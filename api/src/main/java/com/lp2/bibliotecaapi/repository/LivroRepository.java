package com.lp2.bibliotecaapi.repository;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.util.ArmazenamentoJson;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LivroRepository{
   private final List<Livro> livros;

   private final ArmazenamentoJson<Livro> armazenamento;

   public LivroRepository(){
       this.armazenamento = new ArmazenamentoJson<>(
               "src/main/resources/data/livros.json",
               new TypeReference<List<Livro>>() {}
       );

       this.livros = armazenamento.load();
   }

   public Livro save(Livro livro){
       long novoId;
       if (livros.isEmpty()) {
           novoId = 1;
       } else {
           novoId = livros.get(livros.size() - 1).getId() + 1;
       }
       livro.setId(novoId);
       livro.setStatus(true); // Começa com o livro disponível
       livros.add(livro);

       armazenamento.save(livros);

       return livro;
   }

   public List<Livro> findAll(){
       return livros;
   }

   public Optional<Livro> findById(long id){
       return livros.stream()
               .filter(l -> l.getId() == id)
               .findFirst();
   }

   public Optional<Livro> update(long id, Livro novosDados){
       Optional<Livro> livroAntigo = findById(id);

       if(livroAntigo.isPresent()){
           Livro livro = livroAntigo.get();

           livro.setNome(novosDados.getNome());
           livro.setAno(novosDados.getAno());
           livro.setAutor(novosDados.getAutor());
           livro.setStatus(novosDados.isStatus());
           livro.setResponsavelId(novosDados.getResponsavelId());

           armazenamento.save(livros);

           return Optional.of(livro);
       }
       return Optional.empty();
   }

    public boolean delete(long id){
        Optional<Livro> livro = findById(id);

        if(livro.isPresent()){
            if(!livro.get().isStatus()){ // se o status for false (emprestado)
                return false;
            }
            livros.remove(livro.get());
            armazenamento.save(livros);
            return true;
        }
        return false;
    }

}
