package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.EmprestimoService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RealizarEmprestimoController {

    private final EmprestimoService emprestimoService;

    @FXML 
    private TextField txtIdLivro;
    
    @FXML 
    private TextField txtIdUsuario;
    
    @FXML 
    private Label lblMensagem;

    public RealizarEmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @FXML
    public void confirmar() {
        try {
            Long idLivro = Long.parseLong(txtIdLivro.getText());
            Long idUsuario = Long.parseLong(txtIdUsuario.getText());

            Optional<Livro> resultado = emprestimoService.emprestar(idLivro, idUsuario);

            if (resultado.isPresent()) {
                mostrarAlerta("Sucesso", "Empréstimo realizado com sucesso!");
                fecharJanela();
            } else {
                lblMensagem.setText("Livro ou Usuário não encontrados.");
            }

        } catch (NumberFormatException e) {
            lblMensagem.setText("Os IDs devem ser números válidos!");
        } catch (RuntimeException e) {
            lblMensagem.setText(e.getMessage());
        } catch (Exception e) {
            lblMensagem.setText("Erro inesperado: " + e.getMessage());
        }
    }

    @FXML
    public void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtIdLivro.getScene().getWindow();
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