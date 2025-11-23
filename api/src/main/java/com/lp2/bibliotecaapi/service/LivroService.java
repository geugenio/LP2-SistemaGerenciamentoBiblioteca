package com.lp2.bibliotecaapi.service;

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

    public Optional<Livro> findById(Long id){
        return lr.findById(id);
    }
    public Livro save(Livro livro){
        lr.save(livro);
        return livro;
    }

    public boolean delete(Long id){
        return lr.delete(id);
    }

    public Livro update(long id, Livro novosDados) {
       Livro livro = lr.findById(id).orElseThrow(()-> new NoSuchElementException("Livro não encontrado"));

       if(novosDados.getNome() != null){
           livro.setNome(novosDados.getNome());
       }
        if (novosDados.getAutor() != null) {
            livro.setAutor(novosDados.getAutor());
        }
        if (novosDados.getAno() != 0) {
            livro.setAno(novosDados.getAno());
        }

        if(novosDados.isStatus() == livro.isStatus()){

        }

        if(novosDados.getResponsavelId() !=null){
            livro.setResponsavelId(novosDados.getResponsavelId());
        }
        return lr.update(id, livro).orElseThrow(() -> new NoSuchElementException("Erro ao atualizar o livro"));
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

            return lr.update(idLivro, livro);
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

            return lr.update(idLivro, livro);
        }
        return Optional.empty();
    }
 }
