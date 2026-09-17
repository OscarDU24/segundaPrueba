package org.example.test.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.test.Models.User;
import org.example.test.Services.SesionService;
import org.example.test.Util.DatosCompartidos;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Ventana 2: Ventana principal / menú de navegación del sistema.
 */
public class homeController implements Initializable {

    @FXML private VBox rootPane;
    @FXML private Label lblUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User usuarioActual = SesionService.getUsuarioActual();
        if (usuarioActual != null) {
            lblUser.setText("Sesión activa: " + usuarioActual.getUsername());
        }

        // KeyEvent: la tecla ESC solicita confirmación y cierra la aplicación.
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(this::manejarTeclas);
            }
        });
    }

    private void manejarTeclas(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            salirAplicacion();
        }
    }

    // ---------- Navegación (MenuBar / ToolBar) ----------

    @FXML
    public void abrirRegistroCliente() throws IOException {
        abrirVentana("/org/example/test/registroClientes.fxml", "Registro de Cliente");
    }

    @FXML
    public void abrirConsultas() throws IOException {
        abrirVentana("/org/example/test/consultas.fxml", "Consulta de Clientes");
    }

    @FXML
    public void abrirConfig() throws IOException {
        abrirVentana("/org/example/test/config.fxml", "Configuración");
    }

    private void abrirVentana(String recurso, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(recurso));
        Parent root = loader.load();
        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle(titulo);
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();
    }

    @FXML
    public void cerrarSesion(ActionEvent event) throws IOException {
        // Nota: este método puede dispararse tanto desde un Button (ToolBar)
        // como desde un MenuItem (MenuBar / ContextMenu). MenuItem no es un
        // Node, por lo que la ventana se obtiene a través de rootPane en
        // lugar de event.getSource(), evitando un ClassCastException.
        SesionService.cerrarSesion();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/login.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 600));
        stage.setTitle("Mi Login");
        stage.show();
    }

    @FXML
    public void salirAplicacion() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar salida");
        confirmacion.setHeaderText("¿Desea salir de la aplicación?");
        confirmacion.setContentText("Se cerrarán todas las ventanas abiertas.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    // ---------- Herramientas ----------

    @FXML
    public void elegirCarpetaRespaldo() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar carpeta de respaldo");
        File carpeta = directoryChooser.showDialog(rootPane.getScene().getWindow());

        if (carpeta != null) {
            DatosCompartidos.setCarpetaRespaldo(carpeta.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Carpeta seleccionada");
            alert.setHeaderText("Carpeta de respaldo configurada");
            alert.setContentText("Los respaldos se guardarán en:\n" + carpeta.getAbsolutePath());
            alert.showAndWait();
        }
    }

    // ---------- Ayuda: Dialog (no Alert) ----------

    @FXML
    public void mostrarAcercaDe() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Acerca de");
        dialog.setHeaderText("Sistema de Registro y Consulta de Solicitudes");

        VBox contenido = new VBox(8);
        contenido.getChildren().addAll(
                new Label("Asignatura: Programación de Aplicaciones de Escritorio"),
                new Label("Tecnología: JavaFX + Scene Builder"),
                new Label("Caso práctico: Eventos, navegación y paso de datos"),
                new Label("Usuario actual: " +
                        (SesionService.getUsuarioActual() != null
                                ? SesionService.getUsuarioActual().getUsername()
                                : "-"))
        );
        dialog.getDialogPane().setContent(contenido);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    // ---------- ContextMenu ----------

    @FXML
    public void verInformacionSesion() {
        User usuarioActual = SesionService.getUsuarioActual();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de sesión");
        alert.setHeaderText("Usuario conectado");
        alert.setContentText(usuarioActual != null
                ? "Usuario: " + usuarioActual.getUsername()
                : "No hay una sesión activa.");
        alert.showAndWait();
    }

    @FXML
    public void actualizarBienvenida() {
        User usuarioActual = SesionService.getUsuarioActual();
        if (usuarioActual != null) {
            lblUser.setText("Sesión activa: " + usuarioActual.getUsername());
        }
    }
}
