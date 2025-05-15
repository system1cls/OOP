package org.example.test2.GcWorkers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.test2.auxillary.Pair;

public class myGcWorker implements GcWorker {
    GraphicsContext gc;

    public myGcWorker(GraphicsContext gc) {
        this.gc = gc;
    }


    @Override
    public void print(Pair<Integer> pair, Color color) {
        gc.setFill(color);
        gc.fillRect(pair.x * 10, pair.y * 10, 10, 10);
    }

    @Override
    public void print(Pair<Integer> pair, Color color, int width, int height) {
        gc.setFill(color);
        gc.fillRect(pair.x * 10, pair.y * 10, 10 * width, 10 * height);
    }
}
