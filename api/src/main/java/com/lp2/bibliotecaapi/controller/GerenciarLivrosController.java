package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Livro;
import com.lp2.bibliotecaapi.service.LivroService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class GerenciarLivrosController {

    private final LivroService livroService;
    private final ApplicationContext context;

    @FXML 
    private TableView<Livro> tabelaLivros;
    
    @FXML 
    private TableColumn<Livro, Long> colId;
    
    @FXML 
    private TableColumn<Livro, String> colTitulo;
    
    @FXML 
    private TableColumn<Livro, String> colAutor;
    
    @FXML 
    private TableColumn<Livro, Integer> colAno;
    
    @FXML 
    private TableColumn<Livro, String> colStatus;

    public GerenciarLivrosController(LivroService livroService, ApplicationContext context) {
        this.livroService = livroService;
        this.context = context;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusTexto"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        ObservableList<Livro> data = FXCollections.observableArrayList(livroService.findAll());
        tabelaLivros.setItems(data);
        tabelaLivros.refresh();
    }

    @FXML
    public void irParaCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro_livro.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Livro");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            atualizarTabela();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaEdicao(ActionEvent event) {
        Livro selecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        
        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editar_livro.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();

                EditarLivroController controller = loader.getController();
                controller.setLivro(selecionado);

                Stage stage = new Stage();
                stage.setTitle("Editar Livro");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                stage.showAndWait();

                atualizarTabela();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione um livro na tabela para editar.");
        }
    }

    @FXML
    public void excluirLivro(ActionEvent event) {
        Livro selecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        
        if (selecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Livro");
            alert.setHeaderText("Tem certeza que deseja excluir: " + selecionado.getNome() + "?");
            
            Optional<ButtonType> resultado = alert.showAndWait();
            
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                boolean sucesso = livroService.delete(selecionado.getId());
                
                if (sucesso) {
                    atualizarTabela();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Livro excluído.");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não é possível excluir um livro que está emprestado.");
                }
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, selecione um livro na tabela para excluir.");
        }
    }
    
    @FXML
    public void irParaEmprestimo(ActionEvent event) {
        abrirModal("/fxml/realizar_emprestimo.fxml", "Realizar Empréstimo");
        atualizarTabela(); 
    }

    @FXML
    public void irParaDevolucao(ActionEvent event) {
        abrirModal("/fxml/realizar_devolucao.fxml", "Realizar Devolução");
        atualizarTabela();
    }

    private void abrirModal(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void voltarMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        loader.setControllerFactory(context::getBean);
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
