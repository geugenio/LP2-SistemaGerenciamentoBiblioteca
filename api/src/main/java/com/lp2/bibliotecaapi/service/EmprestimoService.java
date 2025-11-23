package com.lp2.bibliotecaapi.service;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmprestimoService {
    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    public Optional<Livro> emprestar(Long idLivro, Long idUsuario){
        Optional<Livro> livroExistente = livroService.findById(idLivro);
        if(livroExistente.isEmpty()){
            return Optional.empty();
        }
        Livro livro = livroExistente.get();

        if(!livro.isStatus()){
            throw new RuntimeException("Livro já emprestado! Não está disponivel.");
        }
        Optional<Usuario> usuarioExistente = usuarioService.findById(idUsuario);
        if(usuarioExistente.isEmpty()){
            return Optional.empty();
        }
        Usuario usuario = usuarioExistente.get();

        //logica pra emprstar
        livro.setStatus(false);
        livro.setResponsavelId(idUsuario);
        livroService.save(livro);

        usuario.setEmprestimos(usuario.getEmprestimos() + 1);
        usuarioService.save(usuario);

        return Optional.of(livro);
    }

    public Optional<Livro> devolver(Long idLivro, Long idUsuario){
        Optional<Livro> livroExistente = livroService.findById(idLivro);
        if(livroExistente.isEmpty()){
            return Optional.empty();
        }
        Livro livro = livroExistente.get();

        if(livro.isStatus()){ //true indica que ainda tá disponiviel
            throw new RuntimeException("O livro ainda não foi emprestado para ser devolvido.");
        }
        Optional<Usuario> usuarioExistente = usuarioService.findById(idUsuario);
        if(usuarioExistente.isEmpty()){
            return Optional.empty();
        }
        Usuario usuario = usuarioExistente.get();

        if(!idUsuario.equals(livro.getResponsavelId())){
            throw new RuntimeException("Este usuário não é o responsável pelo empréstimo do livro em questão.");
        }

        //logica pra devolver
        livro.setStatus(true);
        livro.setResponsavelId(null);
        livroService.save(livro);

        usuario.setEmprestimos(usuario.getEmprestimos() - 1);
        usuarioService.save(usuario);

        return Optional.of(livro);
    }

}
