package org.example.test.Services;

import org.example.test.Models.User;

public class SesionService {

    private static User sesionActual;

    public static void iniciarSesion(User usuario) {
        sesionActual = usuario;
    }

    public static User getUsuarioActual() {
        return sesionActual;
    }

    public static void cerrarSesion() {
        sesionActual = null;
    }
}
