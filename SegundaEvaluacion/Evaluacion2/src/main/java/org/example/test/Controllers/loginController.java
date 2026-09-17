package org.example.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.test.Models.User;
import org.example.test.Services.SesionService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.example.test.Services.UserService.getDBUser;

/**
 * Ventana 1: Inicio de sesión.
 */
public class loginController implements Initializable {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnSesion;
    @FXML private Hyperlink registrarse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // KeyEvent: presionar ENTER dentro del campo de contraseña
        // ejecuta el inicio de sesión sin necesidad de usar el mouse.
        txtPassword.setOnKeyPressed(this::onEnterPressed);
        txtUsuario.setOnKeyPressed(this::onEnterPressed);
    }

    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                procesarLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        // El botón Salir siempre solicita confirmación antes de cerrar.
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar salida");
        confirmacion.setHeaderText("¿Desea salir de la aplicación?");
        confirmacion.setContentText("Se cerrará la ventana de inicio de sesión.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Stage stage = (Stage) btnSesion.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException {
        procesarLogin();
    }

    private void procesarLogin() throws IOException {
        // Validación de campos vacíos.
        if (txtUsuario.getText() == null || txtUsuario.getText().isBlank()
                || txtPassword.getText() == null || txtPassword.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Datos incompletos");
            alert.setHeaderText("Faltan datos por completar");
            alert.setContentText("Debe ingresar tanto el usuario como la contraseña.");
            alert.showAndWait();
            return;
        }

        for (User user : getDBUser()) {

            if (user.getUsername().equals(txtUsuario.getText())) {

                if (user.getPassword().equals(txtPassword.getText())) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inicio de sesión exitoso");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Bienvenido, " + user.getUsername() + "!");
                    alert.showAndWait();
                    SesionService.iniciarSesion(user);
                    abrirHome();

                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Contraseña incorrecta");
                    alert.setContentText(
                            "La contraseña ingresada es incorrecta. Por favor, inténtelo de nuevo."
                    );
                    alert.showAndWait();
                }

                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Usuario no encontrado");
        alert.setContentText(
                "El usuario ingresado no se encuentra registrado. Por favor, verifique su nombre de usuario."
        );
        alert.showAndWait();
    }

    @FXML
    private void abrirRegistro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/registro.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) registrarse.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void abrirHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/home.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) txtUsuario.getScene().getWindow();
        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Sistema de Solicitudes - Menú principal");
        stage.show();
    }
}
