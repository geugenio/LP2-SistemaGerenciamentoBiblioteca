package com.lp2.bibliotecaapi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MenuController {

    private final ApplicationContext context;

    public MenuController(ApplicationContext context) {
        this.context = context;
    }

    @FXML
    public Button btnUsuarios;

    @FXML
    public Button btnLivros;
    
    @FXML
    public Button btnEmprestimos;

    @FXML
    public void initialize() {
        btnUsuarios.setOnAction(this::irParaGerenciarUsuarios);
        
        btnEmprestimos.setOnAction(event -> System.exit(0));
    }

    private void irParaGerenciarUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gerenciar_usuarios.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}