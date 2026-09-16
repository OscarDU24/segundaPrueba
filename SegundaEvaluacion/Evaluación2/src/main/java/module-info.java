module org.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.example.test to javafx.fxml;
    opens org.example.test.Controllers to javafx.fxml;

    exports org.example.test;
}