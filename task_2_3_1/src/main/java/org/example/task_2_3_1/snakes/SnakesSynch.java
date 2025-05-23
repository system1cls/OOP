package org.example.test2.snakes;

import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;

public class SnakesSynch {
    public static class SnakeInfo {
        public volatile Pair<Integer> head;
        public volatile int id;
        public volatile int turn;
        public volatile Dirs dir;
        public volatile boolean isAlive = true;

        public SnakeInfo(Pair<Integer> head, int id, Dirs dir) {
            this.id = id;
            this.head = head;
            this.turn = 0;
            this.dir = dir;
        }


    }


    public class FruitsInfo {
        public volatile int cnt;

        FruitsInfo() {
            cnt = 3;
        }


    }

    public final SnakeInfo []infos;
    public final FruitsInfo fruits = new FruitsInfo();


    public SnakesSynch(int cnt) {
        infos = new SnakeInfo[cnt];
    }
}
