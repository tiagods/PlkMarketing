package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.Departamento;

import java.util.List;

public interface UsuarioDepartamentoDAO {
    List<Departamento> getAll();
}
