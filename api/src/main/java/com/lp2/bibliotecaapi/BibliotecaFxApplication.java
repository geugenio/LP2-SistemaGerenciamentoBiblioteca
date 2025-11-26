package com.lp2.bibliotecaapi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class BibliotecaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder()
                .sources(BibliotecaApiApplication.class) 
                .run(getParameters().getRaw().toArray(new String[0]));

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        
        fxmlLoader.setControllerFactory(springContext::getBean);
        
        rootNode = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema de Biblioteca - LP2");
        stage.setScene(new Scene(rootNode));
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
