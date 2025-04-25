package org.example.test2;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class EnemySnake extends Task implements Snake {
    int field[][];
    SnakeNode []snake;
    int cntNodes = 0;
    Dirs dir;
    int head, tail;
    int id;
    SnakesSynch synch;
    GraphicsContext gc;


    EnemySnake(Pair<Integer> []nodes, SnakesSynch synch, int field[][], GraphicsContext gc) {
        this.gc = gc;
        this.field = field;
        this.synch = synch;
        cntNodes = nodes.length;
        snake = new SnakeNode[nodes.length];
        head = 0;
        tail = nodes.length - 1;
        for (int i = 0; i < cntNodes; i++) {
            int prev = i != cntNodes - 1 ? i + 1 : -1;
            int next = i != 0 ? i - 1 : -1;

            snake[i] = new SnakeNode(nodes[i].x, nodes[i].y, next, prev);
        }
    }

    @Override
    protected Object call() throws Exception {
        while (true) {

            System.out.println(id + " start round");

            synchronized (synch.infos[id]) {

                System.out.println(id + " turn = " + synch.infos[id].turn + " before 0");

                if (synch.infos[id].turn != 0) {
                    try {
                        System.out.println(id + " wait turn != 0");
                        synch.infos[id].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            simpleDefineDir();


            synchronized (field) {
                Pair<Integer> predicted = predictEnemyMoving(snake[head].getPair(), dir, 1);
                if (checkStolk(predicted)) {
                    clearSnake();
                    return null;
                }

            }

            if (checkCircle(field)) {
                clearSnake();
                return null;
            }

            synchronized (synch.infos[id]) {
                synch.infos[id].turn = 1;
                synch.infos[id].notify();
            }

            synchronized (synch.infos[id]) {

                System.out.println(id + " turn = " + synch.infos[id].turn + " before 2");

                if (synch.infos[id].turn != 2) {
                    try {
                        System.out.println(id + " wait turn != 2");
                        synch.infos[id].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            Pair <Pair<Integer>> headAndTail = predictMoving();
            Pair <Integer> headP = headAndTail.x;
            Pair <Integer> tailP = headAndTail.y;
            move(headAndTail);

            synchronized (field) {
                field[headP.x][headP.y] = id;
                field[tailP.x][tailP.y] = -2;
            }

            synchronized (gc) {
                gc.setFill(Color.BLACK);
                gc.fillRect(tailP.x * 10, tailP.y * 10, 10, 10);
                gc.setFill(Color.BLUEVIOLET);
                gc.fillRect(headP.x * 10, headP.y * 10, 10, 10);
            }

            synchronized (synch.infos[id]) {
                synch.infos[id].turn = 3;
                synch.infos[id].notify();
            }
        }
    }

    @Override
    public Pair<Pair<Integer>> predictMoving() {
        Pair<Integer> nextXY = snake[head].getPair();
        Pair<Integer> last = snake[tail].getPair();
        switch(dir) {
            case Up:
                nextXY.y -= 1;
                break;
            case Down:
                nextXY.y += 1;
                break;
            case Left:
                nextXY.x -= 1;
                break;
            case Right:
                nextXY.x += 1;
                break;
        }

        return new Pair<>(nextXY, last);
    }

    @Override
    public void move(Pair<Pair<Integer>> p) {
        if (cntNodes == 1) {
            snake[head].setXY(p.x);
        }
        else {
            int prevTail = this.tail;
            tail = snake[tail].getNext();
            snake[tail].setPrev(-1);
            snake[head].setNext(prevTail);
            snake[prevTail].setPrev(head);
            snake[prevTail].setXY(p.x);
            head = prevTail;
        }
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setDir(Dirs dir) {
        this.dir = dir;
    }

    @Override
    public void add(Pair<Pair<Integer>> p) {
        throw new RuntimeException("Adding to Bots");
    }

    @Override
    public Pair<Integer> getCurHead() {
        return snake[head].getPair();
    }

    public void updateMyHead() {
        synchronized (synch.infos[id]) {
            synch.infos[id].head = snake[head].getPair();
        }
    }

    @Override
    public Dirs getDir() {
        return dir;
    }

    @Override
    public void setSnakesSynch(SnakesSynch synch) {
        this.synch = synch;
    }

    @Override
    public boolean checkCircle(int[][] field) {
        boolean res = false;
        synchronized (field) {
            for (int i = 0; i < cntNodes; i++) {
                Pair<Integer> p = snake[i].getPair();
                if (field[p.x][p.y] == -1) {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }


    private void defineDir() {
        int minId = -1;
        double minDistance = 1000000.;
        Pair<Integer> headP = snake[head].getPair();

        for (int i = 0; i < synch.infos.length; i++) {
            if (i == id) continue;
            synchronized (synch.infos[i]) {
                Pair<Integer> anotherHead = synch.infos[i].head;
                double dist = Math.sqrt((headP.x - anotherHead.x) * (headP.x - anotherHead.x) +
                        (headP.y - anotherHead.y) * (headP.y - anotherHead.y));
                if (dist < minDistance) {
                    minDistance = dist;
                    minId = i;
                }
            }
        }
        Pair<Integer> anotherHead;
        Dirs anotherDir;
        synchronized (synch.infos[minId]) {
            anotherHead = synch.infos[minId].head;
            anotherDir = synch.infos[minId].dir;
        }

        Pair<Integer> predicted = predictEnemyMoving(anotherHead, anotherDir, 2);

        if (predicted.y > headP.y) {
            if (predicted.x > headP.x) {
                if (predicted.y - headP.y > predicted.x - headP.x) dir = Dirs.Down;
                else dir = Dirs.Right;
            }
            else {
                if (predicted.y - headP.y > headP.x - predicted.x) dir = Dirs.Down;
                else dir = Dirs.Left;
            }
        }
        else {
            if (predicted.x > headP.x) {
                if (headP.y - predicted.y > predicted.x - headP.x) dir = Dirs.Up;
                else dir = Dirs.Right;
            }
            else {
                if (headP.y - predicted.y > headP.x - predicted.x) dir = Dirs.Up;
                else dir = Dirs.Left;
            }
        }

        boolean shouldCheck = true;

        synchronized (field) {
            for (int i = 0; i < 4 & shouldCheck; i++) {
                predicted = this.predictEnemyMoving(headP, dir, 1);
                if (this.checkStolk(predicted)) {
                    setNextDir();
                } else shouldCheck = false;
            }
        }


        synchronized (synch.infos[id]) {
            synch.infos[id].dir = dir;
            synch.infos[id].turn = 1;
            synch.infos[id].notify();
        }
    }


    private void simpleDefineDir() {
        Random random = new Random();
        int num = random.nextInt() % 4;

        switch (num) {
            case 0:
                dir = Dirs.Up;
                break;
            case 1:
                dir = Dirs.Right;
                break;
            case 2:
                dir = Dirs.Down;
                break;
            case 3:
                dir = Dirs.Left;
                break;
        }

        Pair<Integer> predicted;
        boolean shouldCheck = true;
        Pair<Integer> headP = snake[head].getPair();
        synchronized (field) {
            for (int i = 0; i < 4 & shouldCheck; i++) {
                predicted = this.predictEnemyMoving(headP, dir, 1);
                if (this.checkStolk(predicted)) {
                    setNextDir();
                } else shouldCheck = false;
            }
        }


        synchronized (synch.infos[id]) {
            synch.infos[id].dir = dir;
            synch.infos[id].turn = 1;
            synch.infos[id].notify();
        }
    }

    private Pair<Integer> predictEnemyMoving(Pair<Integer> head, Dirs dir, int cntStep) {
        int newY, newX;

        switch (dir) {
            case Up:
                newY = head.y > cntStep ? head.y - cntStep : 0;
                return new Pair<>(head.x, newY);
            case Down:
                newY = head.y < field[0].length - cntStep ? head.y + cntStep : field[0].length;
                return new Pair<>(head.x, newY);
            case Left:
                newX = head.x < cntStep ? head.x - cntStep : 0;
                return  new Pair<>(newX, head.y);
            case Right:
                newX = head.x < field.length - cntStep ? head.x + cntStep : field.length;
                return new Pair<>(newX, head.y);
        }
        return null;
    }

    private void setNextDir() {
        switch (dir) {
            case Up:
                dir = Dirs.Right;
                return;
            case Right:
                dir = Dirs.Down;
                return;
            case Down:
                dir = Dirs.Left;
                return;
            case Left:
                dir = Dirs.Up;
                return;
        }
    }

    private void clearSnake() {
        synchronized (synch.infos[id]) {
            synch.infos[id].isAlive = false;
        }

        synchronized (gc) {
            gc.setFill(Color.BLACK);
            for (int i = 0; i < cntNodes; i++) {
                Pair<Integer> pair = snake[i].getPair();
                field[pair.x][pair.y] = -2;
                gc.fillRect(pair.x * 10, pair.y * 10, 10, 10);
            }
        }
    }

    private boolean checkStolk(Pair<Integer> pair) {
        return pair.x < 0 || pair.y < 0 || pair.x >= field.length || pair.y >= field[0].length ||
                field[pair.x][pair.y] != -2;
    }
}
