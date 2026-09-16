package org.example.test;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class loginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnSesion;
    @FXML private Hyperlink registrarse;

    @FXML
    public void iniciarSesion(){
        for (ListaUsuarios Usuario : u) {
            if (u.getUsername == txtUsuario.getText()){
                if (u.getPassword == txtPassword.getText()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Inicio de sesión exitoso");
                    alert.setHeaderText(null);
                    alert.setContentText("¡Bienvenido, " + u.getUsername() + "!");
                    alert.showAndWait();
                    //Saltar de pagina
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Contraseña incorrecta");
                    alert.setContentText("La contraseña ingresada es incorrecta. Por favor, inténtelo de nuevo.");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Usuario no encontrado");
                alert.setContentText("El usuario ingresado no se encuentra registrado. Por favor, verifique su nombre de usuario.");
                alert.showAndWait();
            }
        }


    }

}
