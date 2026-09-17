package org.example.test.Services;

import javafx.scene.control.Alert;

public class ValidatorService {
    public static boolean PasswordValida(String p) {
        if (p.length() >= 8 &&
                p.matches(".*[A-Z].*") &&
                p.matches(".*[a-z].*") &&
                p.matches(".*[0-9].*") && p.matches(".*[^a-zA-Z0-9].*")) {

            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Contraseña invalida");
        alert.setContentText("Su contraseña debe contener lo siguiente: \n Una mayúscula. \n Una minuscula. \n Un dígito. \n Un caracter especial");
        alert.showAndWait();
        return false;
    }

    public static boolean UserValido(String u) {
        if (u.matches(".*[a-zA-Z].*") && u.length() >= 3 && u.length() < 40) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("UsuarioInvalido");
        alert.setContentText("El nombre de usuario debe estar compuesto de caracteres con una longitud de 3 a 40");
        alert.showAndWait();
        return false;
    }
}
