package com.lp2.bibliotecaapi.service;

import com.lp2.bibliotecaapi.dto.UpdateUsuarioDTO;
import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    public UsuarioRepository ur;

    public List<Usuario> findAll(){
        return ur.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return ur.findById(id);
    }

    public Usuario save(Usuario usuario){
        return ur.save(usuario);
    }

    public boolean delete(Long id){
        return ur.delete(id);
    }

    public Optional<Usuario> update(long id, UpdateUsuarioDTO dto){
        Optional<Usuario> existente = ur.findById(id);
        
        if(existente.isPresent()){
            Usuario usuario = existente.get();
            
            if(dto.getNome() != null){
                usuario.setNome(dto.getNome());
            }
            if(dto.getEmail() != null){
                usuario.setEmail(dto.getEmail());
            }

            ur.save(usuario); 
            
            return Optional.of(usuario);
        }
        return Optional.empty();
    }
}