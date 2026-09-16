package org.example.test.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.test.Models.User;
import org.example.test.Services.SesionService;

import java.io.IOException;
import static org.example.test.Services.UserService.getDBUser;

public class loginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnSesion;
    @FXML private Hyperlink registrarse;

    //Set<User> DBUser = new HashSet<>();
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws IOException{
        for (User user : getDBUser()) {

            if (user.getUsername().equals(txtUsuario.getText())) {

                if (user.getPassword().equals(txtPassword.getText())) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inicio de sesión exitoso");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Bienvenido, " + user.getUsername() + "!");
                    alert.showAndWait();
                    SesionService.iniciarSesion(user);
                    // Saltar de página
                    abrirHome(event);

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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void abrirHome(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/test/home.fxml")
        );
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }


}
