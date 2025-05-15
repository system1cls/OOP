package org.example.test2.levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.Logger.ILogger;
import org.example.test2.auxillary.Pair;
import org.example.test2.snakes.Snake;
import org.example.test2.enums.TurnResCode;

import java.util.Random;

public class CustomLevel implements Level {
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

    private void startShow() {
        gcWorker.print(new Pair<>(startX, startY), Color.BLACK, width, height);
    }

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

    @Override
    public void addContext(GcWorker worker) {

        this.gcWorker = worker;

        this.startShow();

        this.show();
    }

    public CustomLevel(int width, int height, int cntFr, int startX, int startY,
                       Snake playerSnake, int dest, ILogger logger) {
        this.logger = logger;
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

    protected void generateFruit() {
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
