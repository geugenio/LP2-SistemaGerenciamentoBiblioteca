package com.lp2.bibliotecaapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsuarioDTO {
    private String nome;
    private String email;
    
    public UpdateUsuarioDTO() {}
    
    public UpdateUsuarioDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public boolean isEmpty(){
        return nome == null && email == null;
    }

    public String getNome() { 
    	return nome; 
    	}
    
    public void setNome(String nome) { 
    	this.nome = nome; 
    	}
    
    public String getEmail() { 
    	return email; 
    	}
    
    public void setEmail(String email) { 
    	this.email = email; 
    	}
}


