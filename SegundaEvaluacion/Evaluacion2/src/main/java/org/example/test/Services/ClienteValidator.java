package org.example.test.Services;

import org.example.test.Models.Cliente;

import java.time.LocalDate;
import java.util.List;

public class ClienteValidator {
    public static String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El campo Nombres es obligatorio.";
        }
        if (!nombre.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "El nombre solo puede contener letras y espacios.";
        }
        return null;
    }

    public static String validarApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            return "El campo Apellidos es obligatorio.";
        }
        if (!apellido.trim().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            return "El apellido solo puede contener letras y espacios.";
        }
        return null;
    }

    public static String validarTipoCliente(String tipoCliente) {
        if (tipoCliente == null || tipoCliente.trim().isEmpty()) {
            return "Debe seleccionar un tipo de cliente.";
        }
        return null;
    }

    public static String validarCiudad(String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty()) {
            return "Debe seleccionar una ciudad.";
        }
        return null;
    }

    public static String validarFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return "Debe seleccionar una fecha de nacimiento.";
        }
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            return "La fecha de nacimiento no puede ser futura.";
        }
        return null;
    }

    public static String validarServicios(String servicios) {
        if (servicios == null || servicios.trim().isEmpty()) {
            return "Debe seleccionar al menos un servicio de interés.";
        }
        return null;
    }

    public static String validarTipoSolicitud(String tipoSolicitud) {
        if (tipoSolicitud == null || tipoSolicitud.trim().isEmpty()) {
            return "Debe seleccionar un tipo de solicitud.";
        }
        return null;
    }

    public static boolean clienteDuplicado(
            List<Cliente> clientes,
            String nombre,
            String apellido,
            LocalDate fechaNacimiento,
            Cliente clienteActual) {
        if (clientes == null) {
            return false;
        }
        for (Cliente cliente : clientes) {
            if (cliente == clienteActual) {
                continue;
            }
            if (cliente.getNombre() != null
                    && cliente.getApellido() != null
                    && cliente.getFechaNacimiento() != null
                    && cliente.getNombre().equalsIgnoreCase(nombre.trim())
                    && cliente.getApellido().equalsIgnoreCase(apellido.trim())
                    && cliente.getFechaNacimiento().equals(fechaNacimiento)) {
                return true;
            }
        }
        return false;
    }
}
