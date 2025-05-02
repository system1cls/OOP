module org.example.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.task_2_3_1 to javafx.fxml;
    exports org.example.task_2_3_1;
}