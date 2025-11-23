package com.lp2.bibliotecaapi.controller;


import com.lp2.bibliotecaapi.dto.UpdateLivroDTO;
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


    //criar
    @PostMapping("/")
    public ResponseEntity<Livro> registerLivro(@RequestBody Livro dados){
        Livro livro = new Livro(dados);
        ls.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //pegar todos
    @GetMapping("/")
    public ResponseEntity<List<Livro>> allLivros(){
        List<Livro> livros = ls.findAll();
        return ResponseEntity.ok(livros);
    }
    //pegar por id
    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id){
        Optional<Livro> livro = ls.findById(id);
        if(livro.isPresent()){
            return ResponseEntity.ok(livro.get());
        }
        return ResponseEntity.notFound().build();
    }

    //atualizar por id
    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @RequestBody UpdateLivroDTO dto){
        if (dto.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Optional<Livro> att = ls.update(id, dto);
        if(att.isPresent()){
            Livro livro = att.get();
            return ResponseEntity.ok(att.get());
        }

        return ResponseEntity.notFound().build();
    }

    //deletar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Optional<Livro> livro = ls.findById(id);
        if(livro.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        boolean deletado = ls.delete(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
