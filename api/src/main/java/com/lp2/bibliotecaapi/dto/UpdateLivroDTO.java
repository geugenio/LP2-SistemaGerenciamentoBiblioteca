package com.lp2.bibliotecaapi.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLivroDTO {
    private String nome;
    private String autor;
    private Integer ano;
}
