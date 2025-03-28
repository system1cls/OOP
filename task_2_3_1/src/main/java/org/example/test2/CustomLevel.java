package org.example.test2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class CustomLevel implements Level {
    private int field[][];
    private int width, height;
    private int cntFr;
    private GraphicsContext gc;
    private int startX, startY;
    private int dest;
    private int curpoints = 0;

    private Pair<Integer> nodesToChange[];
    private int curMax;

    Snake playerSnake;

    private void startShow() {
        gc.setFill(Color.BLACK);
        gc.fillRect(startX, startY, 10 * width, 10 * height);
    }

    public void show() {
        for (int i = 0; i < curMax; i++) {
            switch(field[nodesToChange[i].x][nodesToChange[i].y]) {
                case 1:
                    gc.setFill(Color.BLACK);
                    break;
                case 2:
                    gc.setFill(Color.RED);
                    break;
                case 3:
                    gc.setFill(Color.WHITE);
                    break;
            }

            gc.fillRect(startX + nodesToChange[i].x*10, startY + nodesToChange[i].y*10, 10, 10);
        }

        curMax = 0;
    }


    CustomLevel(int width, int height, int cntFr, int startX, int startY,
                GraphicsContext gc, Snake playerSnake, int dest) {
        if (cntFr > 3 || cntFr < 0) this.cntFr = 3;
        else this.cntFr = cntFr;


        curMax = 0;
        nodesToChange = new Pair[256];

        this.dest = dest;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
        this.field = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) field[i][j] = 1;
        }
        this.gc = gc;
        this.playerSnake = playerSnake;
        playerSnake.setId(3);
        Pair<Integer> p = playerSnake.getCurHead();
        field[p.x][p.y] = 3;
        nodesToChange[curMax++] = new Pair<>(p.x, p.y);
        for (int i = 0; i < this.cntFr; i++) {
            generateFruit();
        }

        this.startShow();

        this.show();
    }

    @Override
    public int checkColAndMove() {
        Pair<Pair<Integer>> playerPairs = playerSnake.predictMoving();
        Pair<Integer> playerNextP = playerPairs.x;
        Pair<Integer> tail = playerPairs.y;
        if (playerNextP.x < 0 || playerNextP.y < 0 || playerNextP.x >= width || playerNextP.y >= height) {
            return -1;
        }

        switch(field[playerNextP.x][playerNextP.y]) {
            case 1:
                playerSnake.move(playerPairs);
                field[playerNextP.x][playerNextP.y] = 3;
                field[tail.x][tail.y] = 1;
                nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);
                nodesToChange[curMax++] = new Pair<>(tail.x, tail.y);
                return 0;
            case 2:
                playerSnake.add(playerPairs);
                field[playerNextP.x][playerNextP.y] = 3;
                nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);

                curpoints++;
                if (curpoints >= dest) return -2;

                generateFruit();


                return 1;
            case 3:
                return -1;
        }
        return 0;
    }

    private void generateFruit() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            if (field[x][y] == 1) {
                field[x][y] = 2;
                nodesToChange[curMax++] = new Pair<>(x, y);
                return;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field[i][j] == 1) {
                    field[i][j] = 2;
                    nodesToChange[curMax++] = new Pair<>(i, j);
                    return;
                }
            }
        }
    }
}
