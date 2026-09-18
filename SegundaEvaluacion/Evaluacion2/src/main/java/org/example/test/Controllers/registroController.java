package org.example.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.test.Models.User;
import org.example.test.Services.UserService;
import org.example.test.Services.ValidatorService;

import java.io.IOException;

import static org.example.test.Services.UserService.DBUser;

public class registroController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;

    @FXML
    public void registrar(ActionEvent event) throws IOException {
        if (txtPassword.getText().equals(txtConfirmarPassword.getText()) &&
                !UserService.BuscarUsuario(txtUsuario.getText()) &&
                ValidatorService.PasswordValida(txtPassword.getText()) &&
                ValidatorService.UserValido(txtUsuario.getText())) {

            User user = new User(txtUsuario.getText(), txtPassword.getText());
            DBUser.add(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Operación exitosa");
            alert.setContentText("Se Registró el usuario " + txtUsuario.getText() + " Correctamente");
            alert.showAndWait();

            // Tras crear el usuario, se envía automáticamente a la
            // pantalla de inicio de sesión para que pueda ingresar.
            abrirLogin(event);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Error en las credenciales");
            alert.setContentText("Las credenciales no son validas");
            alert.showAndWait();
        }
    }

    @FXML
    public void abrirLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/login.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}