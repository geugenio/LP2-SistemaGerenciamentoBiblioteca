package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.dto.UpdateUsuarioDTO;
import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService us;

    //criar usuario
    @PostMapping("/")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario dados){
        Usuario usuario = new Usuario(dados);
        us.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> allUsuarios(){
        List<Usuario> usuarios = us.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id){
        Optional<Usuario> usuario = us.findById(id);
        if(usuario.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody UpdateUsuarioDTO dto){
        if(dto.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Usuario> att = us.update(id, dto);
        if(att.isPresent()){
            Usuario usuario = att.get();
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = us.findById(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        boolean deletado = us.delete(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
