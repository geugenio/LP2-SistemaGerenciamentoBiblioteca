package com.lp2.bibliotecaapi.service;

import com.lp2.bibliotecaapi.dto.UpdateLivroDTO;
import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository lr;

    public List<Livro> findAll(){
        return lr.findAll();
    }

    public Optional<Livro> findById(long id){
        return lr.findById(id);
    }
    public Livro save(Livro livro){
        lr.save(livro);
        return livro;
    }

    public boolean delete(long id){
        return lr.delete(id);
    }

    public Optional<Livro> update(long id, UpdateLivroDTO dto){
        Optional<Livro> existente = lr.findById(id);

        if(existente.isPresent()){
            Livro livro = existente.get();

            if (dto.getNome() !=null){
                livro.setNome(dto.getNome());
            }
            if(dto.getAutor() !=null){
                livro.setAutor(dto.getAutor());
            }

            if(dto.getAno() != null){
                livro.setAno(dto.getAno());
            }
            lr.saveList();
            return Optional.of(livro);
        }
        return Optional.empty();
    }

    public Optional<Livro> emprestar(long idLivro, long idUser){
        Optional<Livro> existente = findById(idLivro);

        if(existente.isPresent()){
            Livro livro = existente.get();

            if(!livro.isStatus()){
                throw new RuntimeException("Livro já emprestado");
            }

            livro.setStatus(false); //emprestada
            livro.setResponsavelId(idUser);

            lr.saveList(); //salva alterações
            return Optional.of(livro);
        }
        return Optional.empty();
    }

    public Optional<Livro> devolver(long idLivro){
        Optional<Livro> existente = findById(idLivro);

        if(existente.isPresent()){
            Livro livro = existente.get();

        if(livro.isStatus()){
                throw new RuntimeException("Livro ainda não foi emprestado para ser devolvido.");
            }

            livro.setStatus(true); //disponivel
            livro.setResponsavelId(null);

            lr.saveList(); //salva alterações

            return Optional.of(livro);
        }
        return Optional.empty();
    }
 }
