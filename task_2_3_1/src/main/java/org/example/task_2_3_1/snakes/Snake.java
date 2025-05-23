package org.example.test2.snakes;

import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;

public interface Snake {

    Pair<Pair<Integer>> predictMoving();

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
