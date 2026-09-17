package org.example.demo1.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {
    private static final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    public static ObservableList<Cliente> getClientes() {
        return clientes;
    }
}
