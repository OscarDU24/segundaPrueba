package org.example.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.test.Services.SesionService;

import java.io.File;
import java.io.IOException;

public class homeController {

    @FXML private VBox mainContainer;

    @FXML
    public void initialize() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemEstadisticas = new MenuItem("Ver estado del sistema");

        itemEstadisticas.setOnAction(e -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Información del Sistema");
            info.setHeaderText("Módulo de Navegación Activo");
            info.setContentText("Sesión iniciada correctamente.");
            info.showAndWait();
        });

        contextMenu.getItems().add(itemEstadisticas);
        mainContainer.setOnContextMenuRequested(e ->
                contextMenu.show(mainContainer, e.getScreenX(), e.getScreenY())
        );
    }


    @FXML
    public void abrirRegistro(ActionEvent event) throws IOException {
        cambiarVentana(event, "/org/example/test/registro.fxml", "Registro de Cliente");
    }

    @FXML
    public void abrirConsulta(ActionEvent event) throws IOException {
        cambiarVentana(event, "/org/example/test/config.fxml", "Consulta de Clientes");
    }

    @FXML
    public void abrirConfig() throws IOException {
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

    @FXML
    public void seleccionarCarpeta(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Carpeta para Respaldos");
        File carpeta = directoryChooser.showDialog(mainContainer.getScene().getWindow());

        if (carpeta != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Carpeta Seleccionada");
            alert.setHeaderText(null);
            alert.setContentText("Ruta asignada: " + carpeta.getAbsolutePath());
            alert.showAndWait();
        }
    }

    @FXML
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

    private void cambiarVentana(ActionEvent event, String fxmlPath, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) mainContainer.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(titulo);
        stage.show();
    }
}