package org.example.task_2_3_1;

import javafx.scene.canvas.Canvas;

public interface Snake {

    Pair<Pair<Integer>>predictMoving();

    void move(Pair<Pair<Integer>> p);

    void setId(int id);

    void setDir(Dirs dir);

    void add(Pair<Pair<Integer>> p);

    Pair<Integer> getCurHead();

    void updateMyHead();

    Dirs getDir();

    void setSnakesSynch(SnakesSynch synch);

    boolean checkCircle(int field[][]);
}
