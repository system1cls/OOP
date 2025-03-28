module org.example.test2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.test2 to javafx.fxml;
    exports org.example.test2;
}