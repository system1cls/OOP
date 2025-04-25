package org.example.test2;

public class SnakesSynch {
    public static class SnakeInfo {
        volatile Pair<Integer> head;
        volatile int id;
        volatile int turn;
        volatile Dirs dir;
        volatile boolean isAlive = true;

        SnakeInfo(Pair<Integer> head, int id, Dirs dir) {
            this.id = id;
            this.head = head;
            this.turn = 0;
            this.dir = dir;
        }


    }

    SnakeInfo []infos;

    SnakesSynch(int cnt) {
        infos = new SnakeInfo[cnt];
    }
}
