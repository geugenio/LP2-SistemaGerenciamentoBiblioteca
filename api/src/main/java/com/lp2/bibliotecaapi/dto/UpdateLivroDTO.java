package com.lp2.bibliotecaapi.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLivroDTO {
    private String nome;
    private String autor;
    private Integer ano;

    public boolean isEmpty(){
        return nome == null && autor == null && ano == null;
    }

	public Object getNome() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAutor() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAno() {
		// TODO Auto-generated method stub
		return null;
	}
}
