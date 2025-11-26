package com.lp2.bibliotecaapi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    @FXML
    public Button btnUsuarios;

    @FXML
    public void initialize() {
        btnUsuarios.setOnAction(event -> System.out.println("Teste"));
        
    }
}
