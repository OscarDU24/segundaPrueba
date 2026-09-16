package org.example.test.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.test.Models.Cliente;
import org.example.test.Util.DatosCompartidos;
import org.example.test.Services.ClienteValidator;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class registroClienteControllers implements Initializable {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private ComboBox<String> cbTipoCliente;
    @FXML
    private ComboBox<String> cbCiudad;
    @FXML
    private DatePicker dpFechaNacimiento;
    @FXML
    private CheckBox chkInternet;
    @FXML
    private CheckBox chkTelefonia;
    @FXML
    private CheckBox chkTelevision;
    @FXML
    private ImageView imgFoto;
    @FXML
    private Button btnSeleccionarFoto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnCancelar;

    private String rutaFoto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Opciones del tipo de cliente
        cbTipoCliente.getItems().addAll(
                "Particular",
                "Empresarial"
        );
        // Opciones de ciudad
        cbCiudad.getItems().addAll(
                "Managua",
                "León",
                "Masaya",
                "Granada",
                "Matagalpa",
                "Chinandega",
                "Estelí"
        );
        btnSeleccionarFoto.setOnAction(event -> seleccionarFoto());
        btnGuardar.setOnAction(event -> guardar());
        btnLimpiar.setOnAction(event -> limpiar());
        btnCancelar.setOnAction(event -> cancelar());
    }

    private void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar fotografía");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Imágenes",
                        "*.png",
                        "*.jpg",
                        "*.jpeg"
                )
        );
        File archivo = fileChooser.showOpenDialog(
                btnSeleccionarFoto.getScene().getWindow()
        );

        if (archivo != null) {
            rutaFoto = archivo.getAbsolutePath();
            Image imagen = new Image(
                    archivo.toURI().toString()
            );
            imgFoto.setImage(imagen);
        }
    }

    private void guardar() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String tipoCliente = cbTipoCliente.getValue();
        String ciudad = cbCiudad.getValue();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        // Obtener los servicios seleccionados
        StringBuilder servicios = new StringBuilder();
        if (chkInternet.isSelected()) {
            servicios.append("Internet");
        }
        if (chkTelefonia.isSelected()) {
            if (servicios.length() > 0) {
                servicios.append(", ");
            }
            servicios.append("Telefonía");
        }
        if (chkTelevision.isSelected()) {
            if (servicios.length() > 0) {
                servicios.append(", ");
            }
            servicios.append("Televisión");
        }

        /*
         * Verificar si el cliente ya existe.
         *
         * Un cliente se considera duplicado cuando
         * coinciden su nombre, apellido y fecha de nacimiento.
         */
        boolean duplicado = ClienteValidator.clienteDuplicado(
                DatosCompartidos.clientes,
                nombre,
                apellido,
                fechaNacimiento,
                null
        );
        if (duplicado) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Cliente duplicado");
            alerta.setHeaderText("No se puede registrar el cliente");
            alerta.setContentText(
                    "Ya existe un cliente con el mismo nombre, "
                            + "apellido y fecha de nacimiento."
            );
            alerta.showAndWait();
            return;
        }
        /*
         * Crear el objeto Cliente.
         *
         * El campo tipoSolicitud se mantiene vacío
         * porque se decidió omitir los RadioButton.
         */
        Cliente cliente = new Cliente(
                nombre,
                apellido,
                ciudad,
                tipoCliente,
                fechaNacimiento,
                "",
                servicios.toString(),
                rutaFoto
        );
        // Agregar el cliente a la lista compartida
        DatosCompartidos.clientes.add(cliente);
        // Mostrar confirmación del registro
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro exitoso");
        alerta.setHeaderText("Cliente registrado correctamente");
        alerta.setContentText(
                "El cliente " + nombre + " " + apellido
                        + " ha sido registrado correctamente."
        );
        alerta.showAndWait();
        // Limpiar formulario después de guardar
        limpiar();
    }

    private void limpiar() {
        txtNombre.clear();
        txtApellido.clear();
        cbTipoCliente.getSelectionModel().clearSelection();
        cbCiudad.getSelectionModel().clearSelection();
        dpFechaNacimiento.setValue(null);
        chkInternet.setSelected(false);
        chkTelefonia.setSelected(false);
        chkTelevision.setSelected(false);
        imgFoto.setImage(null);
        rutaFoto = null;
    }

    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}