package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/geral")
public class EmprestimoController {
    @Autowired
    private EmprestimoService emprestimoService;

    @PutMapping("/emprestar/{idLivro}/{idUsuario}")
    public ResponseEntity<?> emprestarLivro(@PathVariable Long idLivro, @PathVariable Long idUsuario) {
        try{
            Optional<Livro> emprestimo = emprestimoService.emprestar(idLivro, idUsuario);
            if(emprestimo.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(emprestimo.get());
        } catch(RuntimeException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/devolver/{idLivro}/{idUsuario}")
    public ResponseEntity<?> devolverLivro(@PathVariable Long idLivro, @PathVariable Long idUsuario) {
        try{
            Optional<Livro> devolucao = emprestimoService.devolver(idLivro, idUsuario);
            if(devolucao.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(devolucao.get());
        }catch(RuntimeException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}
