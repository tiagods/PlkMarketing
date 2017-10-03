
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Runner {

	public static void main(String[] args) {
		Object[] opcao = {"Usar Filtro Atual","Informar registros","Cancelar"};
		int n = JOptionPane.showOptionDialog(null, "Deseja usar os registros filtrados ou informar manualmente\nos registros que devem ser atualizados?", 
				"Escolha uma opção", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[2]);
		if(n == JOptionPane.NO_OPTION) {
			JTextArea ta = new JTextArea(20, 10);
			Object[] acao = {"Concluir","Cancelar"};
			switch (JOptionPane.showOptionDialog(null, new JScrollPane(ta),"Informe o texto dentro da caixa",JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION, null, acao, acao[1])) {
			
			case JOptionPane.YES_OPTION:
			        //System.out.println(ta.getText());
			        String[] value = ta.getText().split(" |\n|,|;");
			        for(String s : value)
			        	System.out.print(s+",");
			        break;
			}
		}
		else if(n== JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
		else {
			System.out.println("continuar");
		}

	}

}
