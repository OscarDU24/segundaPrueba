package org.example.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.test.Services.SesionService;

import java.io.IOException;

public class homeController {


    @FXML
    public void abrirConfig() throws IOException{
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/config.fxml")
        );
        Parent root = loader.load();
        Stage nuevaVentana = new Stage();
        Scene scene = new Scene(root);
        nuevaVentana.setTitle("Configuración");
        nuevaVentana.setScene(scene);
        nuevaVentana.show();
    }




    public void cerrarSesion(ActionEvent event) throws IOException {
        SesionService.cerrarSesion();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/login.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}

