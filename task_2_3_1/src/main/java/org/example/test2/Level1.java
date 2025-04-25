package org.example.test2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Random;

public class Level1 implements Level {
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

    Level1(int startX, int startY, Snake playerSnake) {
        cntFr = 3;
        playerSnake.setDir(Dirs.Right);

        curMax = 0;
        nodesToChange = new Pair[256];

        this.dest = 20;
        this.startX = startX;
        this.startY = startY;
        this.width = 30;
        this.height = 30;
        this.field = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = 1;
            }
        }

        for (int i = 10; i < 20; i++) {
            for (int j = 10; j < 20; j++) field[i][j] = 0;
        }

        for (int i = 2; i < 4; i++) {
            for (int j = 10; j < 20; j++) {
                field[i][j] = 0;
                field[29 - i][j] = 0;
            }
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
    }

    @Override
    public void addContext(GraphicsContext gc) {
        this.gc = gc;

        this.startShow();

        this.show();
    }

    private void startShow() {
        gc.setFill(Color.BLACK);
        gc.fillRect(startX, startY, 10 * width, 10 * height);
        gc.setFill(Color.CORAL);
        gc.fillRect(startX + 100, startY + 100, 100, 100);
        gc.fillRect(startX + 20, startY + 100, 20, 100);
        gc.fillRect(startX + 260, startY + 100, 20, 100);
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
            case 0:
                return -1;
        }
        return 0;
    }

    @Override
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

    public void setPane(Pane pane) {}
}
