package org.example.demo1.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demo1.model.Cliente;
import org.example.demo1.model.DataStore;

import java.io.IOException;
import java.time.LocalDate;

public class ConsultasController {
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colTipoCliente;
    @FXML
    private TableColumn<Cliente, String> colCiudad;
    @FXML
    private TableColumn<Cliente, LocalDate> colFechaNacimiento;
    @FXML
    private TableColumn<Cliente, String> colTipoSolicitud;

    private FilteredList<Cliente> clientesFiltrados;

    @FXML
    private void initialize() {
        // Mapeo directo usando los getters de la clase Cliente
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        colTipoSolicitud.setCellValueFactory(new PropertyValueFactory<>("tipoSolicitud"));

        // Filtro directo en memoria
        clientesFiltrados = new FilteredList<>(DataStore.getClientes(), p -> true);

        // Búsqueda simple por nombre
        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
            clientesFiltrados.setPredicate(cliente -> {
                if (newVal == null || newVal.trim().isEmpty()) return true;
                return cliente.getNombreCompleto().toLowerCase().contains(newVal.toLowerCase().trim());
            });
        });

        tablaClientes.setItems(clientesFiltrados);
    }

    @FXML
    private void onTableItemClicked(MouseEvent event) {
        // Evento de doble clic
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
            Cliente seleccionado = tablaClientes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                abrirDetalle(seleccionado);
            }
        }
    }

    private void abrirDetalle(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/empresa/solicitudes/view/DetalleView.fxml"));
            Parent root = loader.load();

            DetalleController controller = loader.getController();
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
