package org.example.test.Services;

import org.example.test.Models.User;

import java.util.HashSet;
import java.util.Set;

public class UserService {
    public static Set<User> DBUser = new HashSet<>();

    public static Set<User> getDBUser() {
        return DBUser;
    }

    public static void setDBUser(Set<User> DBUser) {
        UserService.DBUser = DBUser;
    }

    public static boolean BuscarUsuario(String txtUsuario) {
        for (User usuario : DBUser) {
            if (usuario.getUsername().equals(txtUsuario)) {
                return true;
            }
        }
        return false;
    }
}
