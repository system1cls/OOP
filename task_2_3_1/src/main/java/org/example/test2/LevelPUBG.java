package org.example.test2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Random;

public class LevelPUBG implements Level {
    private int field[][];
    private int width, height;
    private int cntFr;
    private GraphicsContext gc;
    private int startX, startY;
    private int dest;
    private int curpoints = 0;
    private SnakesSynch synch;
    private Pane pane;

    private Pair<Integer> nodesToChange[];
    private int curMax;
    Snake playerSnake;

    int startSnakesX1[] = new int[] {5, 4, 3, 2, 1};
    int startSnakesY1[] = new int[] {15, 15, 15, 15, 15};
    Dirs dirSnake1 = Dirs.Right;
    Pair <Integer> []snake1Nodes;


    int startSnakesX2[] = new int[] {25, 26, 27, 28, 29};
    int startSnakesY2[] = new int[] {15, 15, 15, 15, 15};
    Dirs dirSnake2 = Dirs.Left;
    Pair <Integer> []snake2Nodes;


    Thread snake1T, snake2T, circleT;

    LevelPUBG(int startX, int startY, Snake playerSnake) {
        this.dest = 10;
        this.startX = startX;
        this.startY = startY;
        this.width = 30;
        this.height = 30;
        this.field = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = -2;
            }
        }

        synch = new SnakesSynch(3);
        playerSnake.setSnakesSynch(synch);

        snake1Nodes = new Pair[startSnakesX1.length];
        for (int i = 0; i < startSnakesX1.length; i++) {
            snake1Nodes[i] = new Pair<>(startSnakesX1[i], startSnakesY1[i]);
            field[startSnakesX1[i]][startSnakesY1[i]] = 1;
        }

        snake2Nodes = new Pair[startSnakesX2.length];
        for (int i = 0; i < startSnakesX2.length; i++) {
            snake2Nodes[i] = new Pair<>(startSnakesX2[i], startSnakesY2[i]);
            field[startSnakesX2[i]][startSnakesY2[i]] = 2;
        }

        synch.infos[0] = new SnakesSynch.SnakeInfo(playerSnake.getCurHead(), 0, playerSnake.getDir());
        synch.infos[1] = new SnakesSynch.SnakeInfo(snake1Nodes[0], 1, dirSnake1);
        synch.infos[2] = new SnakesSynch.SnakeInfo(snake2Nodes[0], 2, dirSnake2);

        for (int i = 0;  i < 3; i++) synch.infos[i].turn = 0;

        cntFr = 3;

        curMax = 0;
        nodesToChange = new Pair[256];


        this.playerSnake = playerSnake;
        playerSnake.setId(0);
        Pair<Integer> p = playerSnake.getCurHead();
        System.out.println("playerShead: " + p.x + " " + p.y);
        field[p.x][p.y] = 0;
        nodesToChange[curMax++] = new Pair<>(p.x, p.y);
        for (int i = 0; i < this.cntFr; i++) {
            generateFruit();
        }
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }


    @Override
    public void addContext(GraphicsContext gc) {

        this.gc = gc;

        startShow();

        EnemySnake snake1 = new EnemySnake(snake1Nodes, synch, field, gc);
        snake1.setId(1);

        EnemySnake snake2 = new EnemySnake(snake2Nodes, synch, field, gc);
        snake2.setId(2);

        KillingCircle circle = new KillingCircle(gc, field, pane, synch);

        showWithoutWait();

        snake1T = new Thread(snake1);
        snake2T = new Thread(snake2);
        circleT = new Thread(circle);


        snake1T.start();
        snake2T.start();
        circleT.start();
    }

    @Override
    public int checkColAndMove() {
        boolean checkLoose = playerSnake.checkCircle(field);

        if (checkLoose) {
            return -1;
        }

        Pair<Pair<Integer>> playerPairs = playerSnake.predictMoving();
        Pair<Integer> playerNextP = playerPairs.x;
        Pair<Integer> tail = playerPairs.y;
        if (playerNextP.x < 0 || playerNextP.y < 0 || playerNextP.x >= width || playerNextP.y >= height) {
            return -1;
        }



        int res = 0;
        int pointId = 0;
        synchronized (field) {
            pointId = field[playerNextP.x][playerNextP.y];
        }
            switch (pointId) {
                case -2:

                    try {
                        waitForOtherDirs();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    playerSnake.move(playerPairs);
                    synchronized (field) {
                        field[playerNextP.x][playerNextP.y] = 0;
                        field[tail.x][tail.y] = -2;
                    }
                    nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);
                    nodesToChange[curMax++] = new Pair<>(tail.x, tail.y);



                    break;
                case -3:
                    playerSnake.add(playerPairs);
                    synchronized (field) {
                        field[playerNextP.x][playerNextP.y] = 0;
                    }
                    nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);

                    curpoints++;
                    if (curpoints >= dest) {
                        res = -2;
                        break;
                    }

                    generateFruit();


                    res = 1;
                    break;
                default:
                    res = -1;
                    break;
            }


        if (res < 0) {
            snake1T.interrupt();
            snake2T.interrupt();
            circleT.interrupt();
        }
        return res;

    }

    @Override
    public void show() {

        showWithoutWait();
        curMax = 0;

        for (int i = 1; i < synch.infos.length; i++) {
            synchronized (synch.infos[i]) {
                try {
                    if (synch.infos[i].isAlive && synch.infos[i].turn != 3) {
                        System.out.println(i + " Level wait turn != 3");
                        synch.infos[i].wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                synch.infos[i].turn = 0;
                synch.infos[i].notify();
            }
        }

    }


    private void showWithoutWait() {

        synchronized (gc) {
            for (int i = 0; i < curMax; i++) {
                synchronized (field) {
                    switch (field[nodesToChange[i].x][nodesToChange[i].y]) {
                        case -2:
                            gc.setFill(Color.BLACK);
                            break;
                        case -3:
                            gc.setFill(Color.RED);
                            break;
                        case 0:
                            gc.setFill(Color.WHITE);
                            break;
                        default:
                            gc.setFill(Color.BLUEVIOLET);
                            break;
                    }
                }

                gc.fillRect(startX + nodesToChange[i].x * 10, startY + nodesToChange[i].y * 10, 10, 10);
            }
        }
    }

    private void startShow() {
        gc.setFill(Color.BLACK);
        gc.fillRect(startX, startY, 10 * width, 10 * height);
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(startX + 10, startY + 150, 50, 10);
        gc.fillRect(startX + 250, startY + 150, 50, 10);
    }

    private void generateFruit() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            if (field[x][y] == -2) {
                field[x][y] = -3;
                nodesToChange[curMax++] = new Pair<>(x, y);
                return;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field[i][j] == -2) {
                    field[i][j] = -3;
                    nodesToChange[curMax++] = new Pair<>(i, j);
                    return;
                }
            }
        }
    }

    private boolean checkStolk(Pair<Integer> pair) {
        return pair.x < 0 || pair.y < 0 || pair.x >= field.length || pair.y >= field[0].length ||
                field[pair.x][pair.y] == -1;
    }

    private void waitForOtherDirs() throws InterruptedException {
        for (int i = 1; i < synch.infos.length; i++) {
            synchronized (synch.infos[i]) {
                if (synch.infos[i].isAlive && synch.infos[i].turn != 1) {
                    System.out.println(i + " Level wait turn != 1");
                    synch.infos[i].wait();
                }
                synch.infos[i].turn = 2;
            }
        }


        for (int i = 1; i < synch.infos.length; i++) {
            synchronized (synch.infos[i]) {
                synch.infos[i].notify();
            }
        }
    }
}
