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

       saveList();

       return livro;
   }
    public void saveList(){
        armazenamento.save(livros);
    }

   public List<Livro> findAll(){
       return livros;
   }

   public Optional<Livro> findById(Long id){
       return livros.stream()
               .filter(l -> l.getId().equals(id))
               .findFirst();
   }

    public boolean delete(Long id){

        Optional<Livro> livro = findById(id);

        if(livro.isPresent()){
            if(!livro.get().isStatus()){ // se o status for false (emprestado)
                return false;
            }
            livros.remove(livro.get());
            saveList();
            return true;
        }
        return false;
    }

}
