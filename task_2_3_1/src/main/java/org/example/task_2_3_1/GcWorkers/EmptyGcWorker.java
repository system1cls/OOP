package org.example.test2.GcWorkers;

import javafx.scene.paint.Color;
import org.example.test2.auxillary.Pair;

public class EmptyGcWorker implements GcWorker {
    @Override
    public void print(Pair<Integer> pair, Color color) {
    }

    @Override
    public void print(Pair<Integer> pair, Color color, int width, int height) {

    }
}
