package org.example.test2.snakes;

import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.Logger.ILogger;
import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;
import org.example.test2.dirDefiners.IDirDefiner;
import org.example.test2.enums.PUBGEnum;


public class EnemySnake extends Task implements Snake {
    final int field[][];
    SnakeNode[]snake;
    int cntNodes = 0;
    GcWorker gcWorker;

    Color myColor;
    Dirs dir;
    int head, tail;
    int id;
    SnakesSynch synch;
    public final IDirDefiner strategy;

    private ILogger logger;

    public EnemySnake(Pair<Integer>[]nodes, SnakesSynch synch, int field[][],
                      IDirDefiner strategy, Color color, GcWorker worker, ILogger logger) {
        this.logger = logger;
        this.gcWorker = worker;
        this.field = field;
        this.synch = synch;
        cntNodes = nodes.length;
        snake = new SnakeNode[256];
        this.myColor = color;

        head = 0;
        tail = nodes.length - 1;
        for (int i = 0; i < cntNodes; i++) {
            int prev = i != cntNodes - 1 ? i + 1 : -1;
            int next = i != 0 ? i - 1 : -1;

            snake[i] = new SnakeNode(nodes[i].x, nodes[i].y, next, prev);
        }
        this.strategy = strategy;
    }

    @Override
    protected Object call() throws Exception {

        while (true) {

            logger.print("synch.infos[" + id +"]");
            synchronized (synch.infos[id]) {

                if (synch.infos[id].turn != 0) {
                    try {
                        logger.print(id + " wait turn != 0 (turn = " + synch.infos[id].turn + ")");
                        synch.infos[id].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            dir = strategy.defineDir(snake[head].getPair());

            if (dir == Dirs.Clear) clearSnake();



            synchronized (synch.infos[id]) {
                synch.infos[id].dir = dir;
            }

            synchronized (field) {

                if (checkCircle(field)) {
                    clearSnake();
                    return null;
                }
            }



            synchronized (synch.infos[id]) {
                synch.infos[id].turn = 1;
                synch.infos[id].notify();
            }

            print();

            synchronized (synch.infos[id]) {

                if (synch.infos[id].turn != 2) {
                    try {
                        logger.print(id + " wait turn != 2 (turn = " + synch.infos[id].turn + ")");
                        synch.infos[id].wait();
                    } catch (InterruptedException e) {
                        return null;
                    }
                }
            }

            Pair <Pair<Integer>> headAndTail = predictMoving();
            Pair <Integer> headP = headAndTail.x;
            Pair <Integer> tailP = headAndTail.y;


            boolean willEat = false;
            synchronized (field) {
                if (field[headP.x][headP.y] == PUBGEnum.WannaEat.id) {
                    willEat = true;
                }
            }


            if (!willEat) {

                move(headAndTail);


                synchronized (field) {
                    field[headP.x][headP.y] = id;
                    field[tailP.x][tailP.y] = PUBGEnum.Space.id;

                    gcWorker.print(tailP, Color.BLACK);
                    gcWorker.print(headP, myColor);
                }
            }
            else {
                add(headAndTail);

                synchronized (field) {
                    field[headP.x][headP.y] = id;
                    gcWorker.print(headP, myColor);
                }
            }



            synchronized (synch.infos[id]) {
                synch.infos[id].turn = 3;
                synch.infos[id].head = this.getCurHead();
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
        snake[cntNodes] = new SnakeNode(p.x.x, p.x.y, -1, head);
        snake[head].setNext(cntNodes);
        head = cntNodes;
        cntNodes++;

        synchronized (synch.fruits) {
            synch.fruits.cnt -= 1;
        }
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
                if (field[p.x][p.y] == PUBGEnum.Death.id) {
                    res = true;
                    break;
                }
            }
        }
        return res;
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

    private void clearSnake() {

        logger.print("\n\n\n" + id + " clear\n\n");



        synchronized (field) {

            for (int i = 0; i < cntNodes; i++) {
                Pair<Integer> pair = snake[i].getPair();
                field[pair.x][pair.y] = PUBGEnum.Space.id;
                gcWorker.print(pair, Color.BLACK);
            }
        }

        synchronized (synch.infos[id]) {
            synch.infos[id].isAlive = false;
            synch.infos[id].notify();
        }
    }


    public void print() {
        StringBuilder builder = new StringBuilder();
        for (int i = head; i >= 0; i = snake[i].getPrev()) {
            Pair<Integer> p = snake[i].getPair();
            builder.append("(").append(p.x).append(", ").append(p.y).append(")-");
        }

        System.out.println(id + ": " + builder.toString());
    }
}
