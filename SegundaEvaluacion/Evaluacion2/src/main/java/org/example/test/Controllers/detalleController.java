package org.example.test.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.test.Models.Cliente;

import java.io.File;

/**
 * Ventana de detalle: recibe el Cliente seleccionado en la
 * Ventana 4 (paso de datos entre ventanas) y muestra su información completa.
 */
public class detalleController {

    @FXML private Label lblNombre;
    @FXML private Label lblTipoCliente;
    @FXML private Label lblCiudad;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblTipoSolicitud;
    @FXML private Label lblServicios;
    @FXML private ImageView imgFoto;

    public void cargarDatosCliente(Cliente cliente) {
        if (cliente == null) {
            return;
        }
        lblNombre.setText(cliente.getNombreCompleto());
        lblTipoCliente.setText(cliente.getTipoCliente());
        lblCiudad.setText(cliente.getCiudad());
        lblFechaNacimiento.setText(
                cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento().toString() : "-"
        );
        lblTipoSolicitud.setText(cliente.getTipoSolicitud() != null ? cliente.getTipoSolicitud() : "-");
        lblServicios.setText(
                cliente.getServiciosInteres() != null && !cliente.getServiciosInteres().isEmpty()
                        ? cliente.getServiciosInteres() : "Ninguno"
        );

        if (cliente.getRutaFoto() != null) {
            File archivo = new File(cliente.getRutaFoto());
            if (archivo.exists()) {
                imgFoto.setImage(new Image(archivo.toURI().toString()));
            }
        }
    }

    @FXML
    private void cerrar() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }
}
