package com.lp2.bibliotecaapi.controller;


import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.LivroService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService ls;

    @PostMapping("/")
    public ResponseEntity<Livro> registerLivro(@RequestBody Livro dados){
        Livro livro = new Livro(dados);
        ls.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Livro>> allLivros(){
        List<Livro> livros = ls.findAll();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id){
        Optional<Livro> livro = ls.findById(id);
        if(livro.isPresent()){
            return ResponseEntity.ok(livro.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @RequestBody Livro dados){
        try{
            Livro livroAtt = ls.update(id, dados);
            return ResponseEntity.ok(livroAtt);
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try{
            ls.delete(id);
            return ResponseEntity.noContent().build();
        } catch(NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
