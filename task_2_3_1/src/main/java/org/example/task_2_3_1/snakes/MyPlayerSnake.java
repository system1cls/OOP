package org.example.test2.snakes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;


import java.util.Arrays;

public class MyPlayerSnake implements Snake {
    SnakeNode[]snake;
    int cntNodes = 0, maxNode = 256;
    Dirs dir;
    int head, tail;
    int id;
    SnakesSynch synch;



    public MyPlayerSnake(int startX, int startY, Scene scene) {
        snake = new SnakeNode[256];
        cntNodes = 1;
        snake[0] = new SnakeNode(startX, startY, -1, -1);
        dir = Dirs.Up;
        head = 0;
        tail = 0;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode event = keyEvent.getCode();
                Pair<Integer> p = null;
                Pair<Integer> headP = snake[head].getPair();
                if (cntNodes != 1) {
                    p = snake[snake[head].getPrev()].getPair();
                }
                if (event.equals(KeyCode.UP) && (cntNodes == 1 || !(p.x == headP.x && p.y == headP.y - 1))) {
                    dir = Dirs.Up;
                }
                if (event.equals(KeyCode.DOWN) && (cntNodes == 1 || !(p.x == headP.x && p.y == headP.y + 1))) {
                    dir = Dirs.Down;
                }
                if (event.equals(KeyCode.LEFT) && (cntNodes == 1 || !(p.x == headP.x - 1 && p.y == headP.y))) {
                    dir = Dirs.Left;
                }
                if (event.equals(KeyCode.RIGHT) && (cntNodes == 1 || !(p.x == headP.x + 1 && p.y == headP.y))) {
                    dir = Dirs.Right;
                }
            }
        });
    }

    public void setSnakesSynch(SnakesSynch synch) {
        this.synch = synch;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDir(Dirs dir) {
        this.dir = dir;
    }

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

    public void add(Pair<Pair<Integer>> p) {
        if (cntNodes == maxNode) {
            maxNode *= 2;
            snake = Arrays.copyOf(snake, maxNode);
        }

        snake[cntNodes] = new SnakeNode(p.x.x, p.x.y, -1, head);
        snake[head].setNext(cntNodes);
        snake[cntNodes].setPrev(head);
        head = cntNodes++;

    }

    @Override
    public Pair<Integer> getCurHead() {
        return snake[head].getPair();
    }


    public void updateMyHead() {
        synchronized (synch.infos[0]) {
            synch.infos[0].head = snake[head].getPair();


            if (synch.infos[0].turn != -1) {
                System.out.println("playerSynch");

                if (synch.infos[0].turn != 0) {
                    System.out.println("playerSynch if wait");
                    try {
                        synch.infos[0].wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException("Game interrupted");
                    }
                }

                synch.infos[0].turn = 1;
                synch.infos[0].notify();
            }
        }
    }

    public Dirs getDir() {
        return this.dir;
    }

    public boolean checkCircle(int field[][]) {
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
}
