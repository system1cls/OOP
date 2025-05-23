package org.example.test2.auxillary;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.enums.PUBGEnum;
import org.example.test2.snakes.SnakesSynch;

public class KillingCircle extends Task {
    SnakesSynch synch;
    GcWorker gcWorker;
    int field[][];
    Circle circle;
    public double curRad = 220;
    public double X = 150;
    public double Y = 150;

    public KillingCircle(GcWorker worker, int field[][], Pane pane, SnakesSynch synch) {
        this.synch = synch;
        gcWorker = worker;
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

    public KillingCircle(GcWorker worker, int field[][], SnakesSynch synch, double X, double Y, double Rad) {
        this.synch = synch;
        gcWorker = worker;
        this.field = field;
        circle = new Circle();
        circle.setTranslateX(X - 150);
        circle.setTranslateY(Y - 150);
        circle.setRadius(curRad);
        circle.setViewOrder(-1);
        circle.setStroke(Paint.valueOf("blue"));
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(3);
        this.X = X;
        this.Y = Y;
        this.curRad = Rad;
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

            if (curRad <= 60) {
                synchronized (synch.infos[0]) {
                    synch.infos[0].turn = -1;
                    synch.infos[0].notify();
                }
                return null;
            }


            synchronized (this) {
                curRad -= 2;
            }
            circle.setRadius(curRad);


            synchronized (field) {
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < field[0].length; j++) {
                        double xC = i * 10 + 5;
                        double yC = j * 10 + 5;

                        if (field[i][j] != PUBGEnum.Death.id && curRad <= Math.sqrt((X - xC) * (X - xC) + (Y - yC) * (Y - yC))) {
                            if (field[i][j] == PUBGEnum.Fruit.id) {
                                synchronized (synch.fruits) {
                                    synch.fruits.cnt -= 1;
                                }
                            }

                            field[i][j] = PUBGEnum.Death.id;

                            gcWorker.print(new Pair<>(i, j), Color.ORANGE);
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
