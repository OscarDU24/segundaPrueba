package org.example.test.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.example.test.Models.User;
import org.example.test.Services.SesionService;
import org.example.test.Services.ValidatorService;

import static org.example.test.Services.UserService.DBUser;

public class cambiarContraController {
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmNewPassword;

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void CambiarContraseña(ActionEvent event) {
        User usuarioActual = SesionService.getUsuarioActual();

        if (txtPassword.getText().equals(usuarioActual.getPassword())
                && ValidatorService.PasswordValida(txtNewPassword.getText())
                && txtNewPassword.getText().equals(txtConfirmNewPassword.getText())) {

            usuarioActual.setPassword(txtNewPassword.getText());

            System.out.println(usuarioActual.getPassword());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operación exitosa");
            alert.setHeaderText("Contraseña actualizada");
            alert.setContentText("Su contraseña ha sido cambiada con éxito");
            alert.showAndWait();

            cerrarVentana(event);


        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Hubo un error inesperado");
            alert.setContentText("Intentelo mas tarde.");
            alert.showAndWait();

        }
    }
}
