package org.example.test2;

import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class KillingCircle extends Task {
    SnakesSynch synch;
    Pane pane;
    GraphicsContext gc;
    int field[][];
    Circle circle;
    double curRad = 221;
    long delay = 500;

    KillingCircle(GraphicsContext gc, int field[][], Pane pane, SnakesSynch synch) {
        this.synch = synch;
        this.gc = gc;
        this.field = field;
        circle = new Circle();
        circle.setCenterX(150);
        circle.setCenterY(150);
        circle.setRadius(curRad);
        circle.setViewOrder(-1);
        circle.setFill(new Color(0, 0, 0, 0));
        pane.getChildren().add(circle);

    }

    @Override
    protected Object call() throws Exception {

        while(true) {

            synchronized (synch.infos[0]) {
                if (synch.infos[0].turn != 10) {
                    try {
                        synch.infos[0].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            if (curRad == 30) return null;

            long t1 = System.currentTimeMillis();

            curRad -= 1;
            circle.setRadius(curRad);

            synchronized (field) {
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < field[0].length; j++) {
                        double xC = i * 10 + 5;
                        double yC = j * 10 + 5;

                        if (curRad <= Math.sqrt((150 - xC) * (150 - xC) + (150 - yC) * (150 - yC))) {
                            field[i][j] = -1;
                            synchronized (gc) {
                                gc.setFill(Color.ORANGE);
                                gc.fillRect(i*10, j*10, 10, 10);
                            }
                        }
                    }
                }
            }


            long t2 = System.currentTimeMillis();

            try {
                Thread.sleep(delay - (t2 - t1));
            } catch (InterruptedException e) {
                return null;
            }
        }
    }
}
