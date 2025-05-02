package org.example.task_2_3_1;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class KillingCircle extends Task {
    SnakesSynch synch;
    GraphicsContext gc;
    int field[][];
    Circle circle;
    double curRad = 220;
    long delay = 500;
    double X = 150;
    double Y = 150;

    KillingCircle(GraphicsContext gc, int field[][], Pane pane, SnakesSynch synch) {
        this.synch = synch;
        this.gc = gc;
        this.field = field;
        circle = new Circle();
        circle.setTranslateX(X - 150);
        circle.setTranslateY(Y - 150);
        circle.setRadius(curRad);
        circle.setViewOrder(-1);
        circle.setStroke(Paint.valueOf("blue"));
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(3);
        pane.getChildren().add(circle);

    }

    @Override
    protected Object call() {

        while(true) {
            synchronized (synch.infos[0]) {
                if (synch.infos[0].turn != 1) {
                    try {
                        synch.infos[0].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            if (curRad == 30) return null;


            curRad -= 1;
            circle.setRadius(curRad);

            synchronized (field) {
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < field[0].length; j++) {
                        double xC = i * 10 + 5;
                        double yC = j * 10 + 5;

                        if (field[i][j] != -1 && curRad <= Math.sqrt((X - xC) * (X - xC) + (Y - yC) * (Y - yC))) {
                            field[i][j] = -1;
                            gc.setFill(Color.ORANGE);
                            gc.fillRect(i*10, j*10, 10, 10);

                        }
                    }
                }
            }




            synchronized (synch.infos[0]) {
                synch.infos[0].turn = 0;
                synch.infos[0].notify();
            }
        }
    }
}
