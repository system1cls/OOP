package org.example.test2.GcWorkers;

import javafx.scene.paint.Color;
import org.example.test2.auxillary.Pair;

public interface GcWorker {

    void print(Pair<Integer> pair, Color color);

    void print(Pair<Integer> pair, Color color, int width, int height);

}
