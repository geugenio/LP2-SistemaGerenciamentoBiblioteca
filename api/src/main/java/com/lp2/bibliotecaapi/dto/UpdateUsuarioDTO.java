package com.lp2.bibliotecaapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsuarioDTO {
    private String nome;
    private String email;

    public boolean isEmpty(){
        return nome == null && email == null;
    }
}
