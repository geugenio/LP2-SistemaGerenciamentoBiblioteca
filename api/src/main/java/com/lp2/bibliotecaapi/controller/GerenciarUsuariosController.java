package com.lp2.bibliotecaapi.controller;

import com.lp2.bibliotecaapi.model.Usuario;
import com.lp2.bibliotecaapi.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import java.io.IOException;
import java.util.List;

@Component
public class GerenciarUsuariosController {

    private final UsuarioService usuarioService;
    private final ApplicationContext context;

    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private TableColumn<Usuario, Long> colId;

    @FXML
    private TableColumn<Usuario, String> colNome;

    @FXML
    private TableColumn<Usuario, String> colEmail;

    public GerenciarUsuariosController(UsuarioService usuarioService, ApplicationContext context) {
        this.usuarioService = usuarioService;
        this.context = context;
    }

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        List<Usuario> usuarios = usuarioService.findAll();
        ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
        tabelaUsuarios.setItems(data);
    }

    @FXML
    public void irParaCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/cadastro_usuario.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stageCadastro = new Stage();
            stageCadastro.setTitle("Cadastro de Usuário");
            stageCadastro.setScene(new Scene(root));
            

            stageCadastro.initModality(javafx.stage.Modality.APPLICATION_MODAL); 
            
            stageCadastro.showAndWait();
            
            atualizarTabela();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void irParaEdicao(ActionEvent event) {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        
        if (selecionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editar_usuario.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent root = loader.load();

                EditarUsuarioController controller = loader.getController();
                controller.setUsuario(selecionado);

                Stage stage = new Stage();
                stage.setTitle("Editar Usuário");
                stage.setScene(new Scene(root));
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL); 

                stage.showAndWait(); 
                
                atualizarTabela();
                tabelaUsuarios.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, selecione um usuário na tabela para editar.");        }
    }

    @FXML
    public void excluirUsuario(ActionEvent event) {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Excluir Usuário");
            alert.setHeaderText("Tem certeza que deseja excluir o usuário: " + selecionado.getNome() + "?");
            alert.setContentText("Essa ação não poderá ser desfeita.");

            Optional<ButtonType> resultado = alert.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                
                boolean sucesso = usuarioService.delete(selecionado.getId());

                if (sucesso) {
                    atualizarTabela();
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário excluído com sucesso!");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível excluir. Verifique se o usuário tem empréstimos pendentes.");
                }
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Por favor, selecione um usuário na tabela para excluir.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
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
}
