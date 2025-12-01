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
    
    public UpdateLivroDTO(String nome, String autor, Integer ano) {
        this.nome = nome;
        this.autor = autor;
        this.ano = ano;
    	}

    public String getNome() { 
    	return nome; 
    	}
    
    public void setNome(String nome) {
    	this.nome = nome; 
    	}
    
    public String getAutor() {
    	return autor; 
    	}
    
    public void setAutor(String autor) {
    	this.autor = autor; 
    	}
    
    public Integer getAno() {
    	return ano; 
    	}
    
    public void setAno(Integer ano) {
    	this.ano = ano; 
    	}
}
