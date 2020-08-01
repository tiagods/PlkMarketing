package br.com.tiagods.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class RegisterApp {
    @Id
    private Long id;
    private String nome;
}
