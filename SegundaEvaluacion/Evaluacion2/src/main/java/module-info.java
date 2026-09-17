module org.example.test {
    requires javafx.controls;
    requires javafx.fxml;

    // Permite que JavaFX lea tus vistas y controladores
    opens org.example.test to javafx.fxml;
    opens org.example.test.Controllers to javafx.fxml;
    opens org.example.test.Models to javafx.base;

    exports org.example.test;
    exports org.example.test.Controllers;
    exports org.example.test.Models;
}