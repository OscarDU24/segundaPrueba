package org.example.test.Controllers;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.test.Models.Cliente;
import org.example.test.Util.DatosCompartidos;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Ventana 4: Consulta de clientes registrados.
 */
public class consultasController implements Initializable {

    @FXML private TextField txtBuscar;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colTipoCliente;
    @FXML private TableColumn<Cliente, String> colCiudad;
    @FXML private TableColumn<Cliente, LocalDate> colFechaNacimiento;
    @FXML private TableColumn<Cliente, String> colTipoSolicitud;

    private FilteredList<Cliente> clientesFiltrados;
    private String ciudadSeleccionada = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

        // La tabla lee directamente de la lista compartida: cualquier
        // cliente registrado en la Ventana 3 aparece aquí automáticamente.
        clientesFiltrados = new FilteredList<>(DatosCompartidos.clientes, p -> true);

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        tablaClientes.setItems(clientesFiltrados);
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText();
        clientesFiltrados.setPredicate(cliente -> {
            boolean coincideTexto = texto == null || texto.trim().isEmpty()
                    || cliente.getNombreCompleto().toLowerCase().contains(texto.toLowerCase().trim());
            boolean coincideCiudad = ciudadSeleccionada == null
                    || ciudadSeleccionada.equals(cliente.getCiudad());
            return coincideTexto && coincideCiudad;
        });
    }

    /** Dialog (ChoiceDialog) vinculado a una funcionalidad real: filtrar por ciudad. */
    @FXML
    private void filtrarPorCiudad() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Todas",
                "Todas", "Managua", "León", "Masaya", "Granada", "Matagalpa", "Chinandega", "Estelí");
        dialog.setTitle("Filtrar por ciudad");
        dialog.setHeaderText("Seleccione la ciudad de los clientes a mostrar");
        dialog.setContentText("Ciudad:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(ciudad -> {
            ciudadSeleccionada = "Todas".equals(ciudad) ? null : ciudad;
            aplicarFiltros();
        });
    }

    @FXML
    private void onTableItemClicked(MouseEvent event) {
        // MouseEvent: doble clic sobre un registro abre el detalle.
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirDetalle(seleccionado);
            }
        }
    }

    private void abrirDetalle(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/test/detalle.fxml"));
            Parent root = loader.load();

            detalleController controller = loader.getController();
            controller.cargarDatosCliente(cliente);

            Stage stage = new Stage();
            stage.setTitle("Detalle del Cliente");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRegresar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
