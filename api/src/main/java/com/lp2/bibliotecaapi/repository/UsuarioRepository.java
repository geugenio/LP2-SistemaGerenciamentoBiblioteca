package com.lp2.bibliotecaapi.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.util.ArmazenamentoJson;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final List<Usuario> usuarios;

    private final ArmazenamentoJson<Usuario> armazenamento;

    public UsuarioRepository(){
        this.armazenamento = new ArmazenamentoJson<>(
                "src/main/resources/data/usuarios.json",
                new TypeReference<List<Usuario>>(){}

        );
        this.usuarios = armazenamento.load();
    }

    public void saveList(){
        armazenamento.save(usuarios);
    }

    public Usuario save(Usuario usuario){
        long novoId;
        if(usuarios.isEmpty()){
            novoId = 1;
        } else{
            novoId = usuarios.get(usuarios.size()-1).getId()+1;
        }

        usuario.setId(novoId);
        usuario.setEmprestimos(0); //começa com 0 livros a devolver
        usuarios.add(usuario);

        saveList();

        return usuario;
    }

    public List<Usuario> findAll(){return usuarios;}

    public Optional<Usuario> findById(long id){
        return usuarios.stream()
                .filter(l -> l.getId()==id)
                .findFirst();
    }

    public boolean delete(long id){
        Optional<Usuario> usuario = findById(id);
        if(usuario.isPresent()){
            if(usuario.get().getEmprestimos() != 0){
                return false;
            }

            usuarios.remove(usuario.get());
            saveList();
            return true;
        }
        return false;
    }
}
