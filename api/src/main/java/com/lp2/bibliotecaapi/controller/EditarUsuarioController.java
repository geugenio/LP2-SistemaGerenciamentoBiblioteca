package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.dto.UpdateUsuarioDTO;
import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.service.UsuarioService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import javafx.scene.control.Alert;

@Component
public class EditarUsuarioController {

    private final UsuarioService usuarioService;
    private Usuario usuarioAtual;

    @FXML private TextField txtNome;
    @FXML private TextField txtEmail;
    @FXML private Label lblMensagem;

    public EditarUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioAtual = usuario;
        this.txtNome.setText(usuario.getNome());
        this.txtEmail.setText(usuario.getEmail());
    }

    @FXML
    public void salvar() {
        if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            lblMensagem.setText("Preencha todos os campos!");
            return;
        }

        try {
            UpdateUsuarioDTO dto = new UpdateUsuarioDTO(txtNome.getText(), txtEmail.getText());

            usuarioService.update(usuarioAtual.getId(), dto);

            mostrarAlerta("Sucesso", "Usuário atualizado com sucesso!");            fecharJanela();
            
        } catch (Exception e) {
            lblMensagem.setText("Erro ao atualizar: " + e.getMessage());
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