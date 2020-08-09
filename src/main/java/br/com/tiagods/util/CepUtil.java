package br.com.tiagods.util;

import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.repository.Cidades;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import org.fxutils.maskedtextfield.MaskedTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CepUtil {

    private boolean habilidarFiltroCidade = false;

    @Autowired
    Cidades cidades;

    public void comboRegiao(JFXComboBox<Cidade> cbCidade, JFXComboBox<Cidade.Estado> cbEstado){
        Optional<Cidade> result = cidades.findByNome("São Paulo");
        cbCidade.getItems().addAll(cidades.findAllByEstado(Cidade.Estado.SP));
        if(result.isPresent()){
            cbCidade.setValue(result.get());
        }
        cbEstado.getItems().addAll(Cidade.Estado.values());
        cbEstado.setValue(Cidade.Estado.SP);
        cbEstado.valueProperty().addListener(new BuscaCep(cbCidade));
        new ComboBoxAutoCompleteUtil<>(cbCidade);
    }

    public void bucarCep(MaskedTextField txCEP, JFXTextField txLogradouro, JFXTextField txNumero, JFXTextField txComplemento,
                            JFXTextField txBairro, JFXComboBox<Cidade> cbCidade, JFXComboBox<Cidade.Estado> cbEstado){
        EnderecoUtil util = EnderecoUtil.getInstance();
        if(txCEP.getPlainText().trim().length()==8) {
            Endereco endereco = util.pegarCEP(txCEP.getPlainText());
            if(endereco!=null){
                habilidarFiltroCidade = false;
                Optional<Cidade> result = cidades.findByNome(endereco.getLocalidade());
                if(result.isPresent()){
                    Cidade cidade = result.get();
                    txLogradouro.setText(endereco.getLogradouro());
                    txNumero.setText("");
                    txComplemento.setText(endereco.getComplemento());
                    txBairro.setText(endereco.getBairro());
                    cbCidade.getItems().clear();

                    List<Cidade> cidadeList = cidades.findAllByEstado(endereco.getUf());
                    cbEstado.setValue(endereco.getUf());
                    cbCidade.getItems().addAll(cidadeList);
                    cbCidade.setValue(cidade);
                    new ComboBoxAutoCompleteUtil<>(cbCidade);
                }
                habilidarFiltroCidade = true;
            }
            else
                JavaFxUtil.alert(Alert.AlertType.WARNING,"CEP Invalido",null,
                        "Verifique se o cep informado é valido ou se existe uma conexão com a internet",null, false);
        }
        else{
            JavaFxUtil.alert(Alert.AlertType.WARNING,"CEP Invalido",null,"Verifique o cep informado",null, false);
        }
    }

    public class BuscaCep implements ChangeListener<Cidade.Estado> {
        private JFXComboBox<Cidade> cbCidade;
        public BuscaCep(JFXComboBox<Cidade> cbCidade){
            this.cbCidade=cbCidade;
        }
        @Override
        public void changed(ObservableValue<? extends Cidade.Estado> observable, Cidade.Estado oldValue, Cidade.Estado newValue) {
            if (newValue != null && habilidarFiltroCidade) {
                cbCidade.getItems().clear();
                List<Cidade> listCidades = cidades.findAllByEstado(newValue);
                cbCidade.getItems().addAll(listCidades);
                cbCidade.getSelectionModel().selectFirst();
            }
        }
    }
}
