module org.example.test2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires com.dlsc.formsfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens org.example.test2 to javafx.fxml;
    exports org.example.test2;
    exports org.example.test2.GcWorkers;
    opens org.example.test2.GcWorkers to javafx.fxml;
    exports org.example.test2.dirDefiners;
    opens org.example.test2.dirDefiners to javafx.fxml;
    exports org.example.test2.levels;
    opens org.example.test2.levels to javafx.fxml;
    exports org.example.test2.snakes;
    opens org.example.test2.snakes to javafx.fxml;
    exports org.example.test2.auxillary;
    opens org.example.test2.auxillary to javafx.fxml;
    exports org.example.test2.enums;
    opens org.example.test2.enums to javafx.fxml;
}