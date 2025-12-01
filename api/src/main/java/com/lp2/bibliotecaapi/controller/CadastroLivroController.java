package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.LivroService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class CadastroLivroController {

    private final LivroService livroService;

    @FXML 
    private TextField txtTitulo;
    
    @FXML 
    private TextField txtAutor;
    
    @FXML 
    private TextField txtAno;
    
    @FXML 
    private Label lblMensagem;

    public CadastroLivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @FXML
    public void salvar() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String anoTexto = txtAno.getText();

        if (titulo.isEmpty() || autor.isEmpty() || anoTexto.isEmpty()) {
            lblMensagem.setText("Preencha todos os campos!");
            return;
        }

        try {
            int ano = Integer.parseInt(anoTexto);

            Livro novoLivro = new Livro();
            novoLivro.setNome(titulo);
            novoLivro.setAutor(autor);
            novoLivro.setAno(ano);

            livroService.save(novoLivro);

            mostrarAlerta("Sucesso", "Livro salvo com sucesso!");
            fecharJanela();

        } catch (NumberFormatException e) {
            lblMensagem.setText("O campo de ano deve conter apenas números!");
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
