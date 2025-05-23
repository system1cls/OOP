package org.example.test2.levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.Logger.ILogger;
import org.example.test2.auxillary.Pair;
import org.example.test2.enums.Dirs;
import org.example.test2.enums.TurnResCode;
import org.example.test2.snakes.Snake;

import java.util.Random;

public class Level1 implements Level {
    private int field[][];
    private int width, height;
    private int cntFr;
    private GcWorker gcWorker;
    private int startX, startY;
    private int dest;
    private int curpoints = 0;

    private Pair<Integer> nodesToChange[];
    private int curMax;

    private ILogger logger;

    Snake playerSnake;

    public Level1(int startX, int startY, Snake playerSnake, ILogger logger) {
        this.logger = logger;
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
    public void addContext(GcWorker gc) {
        this.gcWorker = gc;

        this.startShow();

        this.show();
    }

    private void startShow() {
        gcWorker.print(new Pair<>(startX, startY), Color.BLACK, width, height);
        gcWorker.print(new Pair<>(10, 10), Color.CORAL, 10, 10);
        gcWorker.print(new Pair<>(2, 10), Color.CORAL, 2, 10);
        gcWorker.print(new Pair<>(26, 10), Color.CORAL, 2, 10);
    }


    @Override
    public TurnResCode checkColAndMove() {
        Pair<Pair<Integer>> playerPairs = playerSnake.predictMoving();
        Pair<Integer> playerNextP = playerPairs.x;
        Pair<Integer> tail = playerPairs.y;
        if (playerNextP.x < 0 || playerNextP.y < 0 || playerNextP.x >= width || playerNextP.y >= height) {
            return TurnResCode.Lose;
        }

        switch(field[playerNextP.x][playerNextP.y]) {
            case 1:
                playerSnake.move(playerPairs);
                field[playerNextP.x][playerNextP.y] = 3;
                field[tail.x][tail.y] = 1;
                nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);
                nodesToChange[curMax++] = new Pair<>(tail.x, tail.y);
                return TurnResCode.Nothing;
            case 2:
                playerSnake.add(playerPairs);
                field[playerNextP.x][playerNextP.y] = 3;
                nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);

                curpoints++;
                if (curpoints >= dest) return TurnResCode.Win;

                generateFruit();


                return TurnResCode.Point;
            case 3:
            case 0:
                return TurnResCode.Lose;
        }
        return TurnResCode.Nothing;
    }

    @Override
    public void show() {
        for (int i = 0; i < curMax; i++) {
            Color color = Color.BLACK;

            switch(field[nodesToChange[i].x][nodesToChange[i].y]) {
                case 1:
                    color = Color.BLACK;
                    break;
                case 2:
                    color = Color.RED;
                    break;
                case 3:
                    color = Color.WHITE;
                    break;
            }

            gcWorker.print(nodesToChange[i], color);
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
