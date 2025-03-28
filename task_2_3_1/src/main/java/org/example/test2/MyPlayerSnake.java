package org.example.test2;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MyPlayerSnake implements Snake {
    SnakeNode []snake;
    int cntNodes = 0, maxNode = 256;
    Dirs dir;
    int head, tail;
    int id;

    public void setId(int id) {
        this.id = id;
    }

    MyPlayerSnake(int startX, int startY, Scene scene) {
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
                if (event.equals(KeyCode.UP) && dir != Dirs.Down) {
                    dir = Dirs.Up;
                    System.out.println("Up");
                }
                if (event.equals(KeyCode.DOWN) && dir != Dirs.Up) {
                    dir = Dirs.Down;
                    System.out.println("Down");
                }
                if (event.equals(KeyCode.LEFT) && dir != Dirs.Right) {
                    dir = Dirs.Left;
                    System.out.println("Left");
                }
                if (event.equals(KeyCode.RIGHT) && dir != Dirs.Left) {
                    dir = Dirs.Right;
                    System.out.println("Right");
                }
            }
        });
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

        System.out.println(head);
        System.out.println(tail);

        for (int i = 0; i < cntNodes; i++) {
            System.out.println(snake[i]);
        }
    }

    @Override
    public Pair<Integer> getCurHead() {
        return snake[head].getPair();
    }
}
