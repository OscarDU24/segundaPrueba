package org.example.test.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.test.Models.Cliente;
import org.example.test.Services.ClienteValidator;
import org.example.test.Util.DatosCompartidos;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Ventana 3: Registro de cliente.
 */
public class registroClienteControllers implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private ComboBox<String> cbTipoCliente;
    @FXML private ComboBox<String> cbCiudad;
    @FXML private DatePicker dpFechaNacimiento;

    @FXML private ToggleGroup tgTipoSolicitud;
    @FXML private RadioButton rbNueva;
    @FXML private RadioButton rbModificacion;
    @FXML private RadioButton rbCancelacion;

    @FXML private CheckBox chkInternet;
    @FXML private CheckBox chkTelefonia;
    @FXML private CheckBox chkTelevision;

    @FXML private ImageView imgFoto;
    @FXML private Button btnSeleccionarFoto;
    @FXML private Button btnGuardar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnCancelar;
    @FXML private Button btnVolverMenu;

    private String rutaFoto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbTipoCliente.getItems().addAll(
                "Particular",
                "Empresarial"
        );
        cbCiudad.getItems().addAll(
                "Managua",
                "León",
                "Masaya",
                "Granada",
                "Matagalpa",
                "Chinandega",
                "Estelí"
        );

        // KeyEvent: se restringe el ingreso de números/símbolos en
        // los campos de texto que solo deben contener letras.
        txtNombre.addEventFilter(KeyEvent.KEY_TYPED, this::soloLetras);
        txtApellido.addEventFilter(KeyEvent.KEY_TYPED, this::soloLetras);
    }

    private void soloLetras(KeyEvent event) {
        String caracter = event.getCharacter();
        if (!caracter.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]")) {
            event.consume();
        }
    }

    @FXML
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
            Image imagen = new Image(archivo.toURI().toString());
            imgFoto.setImage(imagen);
        }
    }

    @FXML
    private void guardar() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String tipoCliente = cbTipoCliente.getValue();
        String ciudad = cbCiudad.getValue();
        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();

        RadioButton seleccionado = (RadioButton) tgTipoSolicitud.getSelectedToggle();
        String tipoSolicitud = seleccionado != null ? seleccionado.getText() : null;

        StringBuilder servicios = new StringBuilder();
        if (chkInternet.isSelected()) {
            servicios.append("Internet");
        }
        if (chkTelefonia.isSelected()) {
            if (servicios.length() > 0) servicios.append(", ");
            servicios.append("Telefonía");
        }
        if (chkTelevision.isSelected()) {
            if (servicios.length() > 0) servicios.append(", ");
            servicios.append("Televisión");
        }

        // Validaciones básicas antes de permitir guardar (Alert de advertencia).
        String[] errores = {
                ClienteValidator.validarNombre(nombre),
                ClienteValidator.validarApellido(apellido),
                ClienteValidator.validarTipoCliente(tipoCliente),
                ClienteValidator.validarCiudad(ciudad),
                ClienteValidator.validarFechaNacimiento(fechaNacimiento),
                ClienteValidator.validarTipoSolicitud(tipoSolicitud),
                ClienteValidator.validarServicios(servicios.toString())
        };
        for (String error : errores) {
            if (error != null) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Datos incompletos");
                alerta.setHeaderText("No se puede registrar el cliente");
                alerta.setContentText(error);
                alerta.showAndWait();
                return;
            }
        }

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

        Cliente cliente = new Cliente(
                nombre,
                apellido,
                ciudad,
                tipoCliente,
                fechaNacimiento,
                tipoSolicitud,
                servicios.toString(),
                rutaFoto
        );

        // Se agrega a la lista compartida: esto es lo que permite que el
        // cliente aparezca automáticamente en el TableView de la ventana
        // de Consulta (paso de datos entre ventanas).
        DatosCompartidos.clientes.add(cliente);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Registro exitoso");
        alerta.setHeaderText("Cliente registrado correctamente");
        alerta.setContentText(
                "El cliente " + nombre + " " + apellido
                        + " ha sido registrado correctamente."
        );
        alerta.showAndWait();

        limpiar();
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtApellido.clear();
        cbTipoCliente.getSelectionModel().clearSelection();
        cbCiudad.getSelectionModel().clearSelection();
        dpFechaNacimiento.setValue(null);
        tgTipoSolicitud.selectToggle(null);
        chkInternet.setSelected(false);
        chkTelefonia.setSelected(false);
        chkTelevision.setSelected(false);
        imgFoto.setImage(null);
        rutaFoto = null;
    }

    @FXML
    private void cancelar() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void volverAlMenu() {
        // Esta ventana se abre como una Stage independiente desde el
        // menú principal (home), por lo que "volver al menú" simplemente
        // cierra esta ventana y deja visible la ventana del menú.
        Stage stage = (Stage) btnVolverMenu.getScene().getWindow();
        stage.close();
    }
}