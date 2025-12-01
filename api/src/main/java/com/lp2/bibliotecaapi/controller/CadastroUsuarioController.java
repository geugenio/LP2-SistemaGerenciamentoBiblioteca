package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import javafx.scene.control.Alert;

@Component
public class CadastroUsuarioController {

    private final UsuarioService usuarioService;

    @FXML 
    private TextField txtNome;
    
    @FXML 
    private TextField txtEmail;
    
    @FXML 
    private Label lblMensagem;

    public CadastroUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @FXML
    public void salvar() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();

        if (nome.isEmpty() || email.isEmpty()) {
            lblMensagem.setText("Preencha todos os campos!");
            lblMensagem.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);

            usuarioService.save(novoUsuario);

            mostrarAlerta("Sucesso", "Usuário " + nome + " salvo com sucesso!");
            fecharJanela();
            
        } catch (Exception e) {
            lblMensagem.setText("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}