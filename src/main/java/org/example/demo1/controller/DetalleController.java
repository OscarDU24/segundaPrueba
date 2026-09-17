package org.example.demo1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.demo1.model.Cliente;

public class DetalleController {

    @FXML
    private Label lblNombre;

        public void cargarDatosCliente(Cliente cliente) {
            if (cliente != null && lblNombre != null) {
                lblNombre.setText(cliente.getNombreCompleto());
            }
        }
    }
