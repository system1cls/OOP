package org.example.test2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public interface Level {

    void addContext(GraphicsContext gc);

    int checkColAndMove();

    void show();

    void setPane(Pane pane);
}
