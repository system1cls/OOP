package org.example.test2.levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.enums.TurnResCode;

public interface Level {

    void addContext(GcWorker worker);

    TurnResCode checkColAndMove();

    void show();

    void setPane(Pane pane);
}
