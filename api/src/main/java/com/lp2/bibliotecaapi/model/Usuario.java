package com.lp2.bibliotecaapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private int emprestimos;

    
    
    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public int getEmprestimos() {
		return emprestimos;
	}



	public void setEmprestimos(int emprestimos) {
		this.emprestimos = emprestimos;
	}



	public Usuario(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.emprestimos = usuario.getEmprestimos();
    }
}
