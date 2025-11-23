package com.lp2.bibliotecaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Livro {
    private Long id;
    private String nome;
    private int ano;
    private String autor;
    private boolean status; //true-> disponivel, false->emprestado
    private Long responsavelId;

    public Livro(Livro livro){
        this.id = livro.getId();
        this.ano = livro.getAno();
        this.nome = livro.getNome();
        this.autor = livro.getAutor();
        this.status = livro.isStatus();
        this.responsavelId = livro.getResponsavelId();
    }
}
