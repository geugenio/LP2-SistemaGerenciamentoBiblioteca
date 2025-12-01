package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.dto.UpdateLivroDTO;
import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.LivroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class EditarLivroController {

    private final LivroService livroService;
    private Livro livroAtual;

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtAno;
    @FXML private Label lblMensagem;

    public EditarLivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    public void setLivro(Livro livro) {
        this.livroAtual = livro;
        this.txtTitulo.setText(livro.getNome());
        this.txtAutor.setText(livro.getAutor());
        this.txtAno.setText(String.valueOf(livro.getAno()));
    }

    @FXML
    public void salvar() {
        if (txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty() || txtAno.getText().isEmpty()) {
            lblMensagem.setText("Preencha todos os campos.");
            return;
        }

        try {
            int ano = Integer.parseInt(txtAno.getText());
            
            UpdateLivroDTO dto = new UpdateLivroDTO(txtTitulo.getText(), txtAutor.getText(), ano);
            
            livroService.update(livroAtual.getId(), dto);

            mostrarAlerta("Sucesso", "Livro atualizado com sucesso!");
            fecharJanela();

        } catch (NumberFormatException e) {
            lblMensagem.setText("O campo de ano deve conter apenas números!");
        } catch (Exception e) {
            lblMensagem.setText("Erro ao atualizar: " + e.getMessage());
        }
    }

    @FXML
    public void cancelar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
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