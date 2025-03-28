package org.example.test2;

import javafx.scene.canvas.Canvas;

public interface Snake {

    Pair<Pair<Integer>>predictMoving();

    void move(Pair<Pair<Integer>> p);

    void setId(int id);

    void setDir(Dirs dir);

    void add(Pair<Pair<Integer>> p);

    Pair<Integer> getCurHead();
}
