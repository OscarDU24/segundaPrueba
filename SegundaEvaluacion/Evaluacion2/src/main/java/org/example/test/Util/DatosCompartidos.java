package org.example.test.Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.test.Models.Cliente;

/**
 * Estado compartido en memoria. Es el "puente" que permite el paso de
 * datos entre la ventana de Registro de Cliente y la ventana de Consulta:
 * ambas ventanas leen/escriben sobre la misma ObservableList, por lo
 * que un cliente registrado aparece automáticamente en el TableView.
 */
public class DatosCompartidos {

    public static final ObservableList<Cliente> clientes =
            FXCollections.observableArrayList();

    /** Carpeta de respaldo elegida mediante DirectoryChooser (Ventana principal). */
    private static String carpetaRespaldo;

    public static String getCarpetaRespaldo() {
        return carpetaRespaldo;
    }

    public static void setCarpetaRespaldo(String carpetaRespaldo) {
        DatosCompartidos.carpetaRespaldo = carpetaRespaldo;
    }
}
