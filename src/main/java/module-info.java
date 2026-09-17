module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;

    // Permite que JavaFX FXML acceda a tus controladores
    opens org.example.demo1.controller to javafx.fxml;

    // Si tus vistas FXML leen directamente propiedades del modelo (Cliente)
    opens org.example.demo1.model to javafx.base;

    exports org.example.demo1;
}