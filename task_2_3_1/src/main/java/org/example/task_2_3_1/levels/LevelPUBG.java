package org.example.test2.levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.test2.GcWorkers.GcWorker;
import org.example.test2.Logger.ILogger;
import org.example.test2.auxillary.KillingCircle;
import org.example.test2.auxillary.Pair;
import org.example.test2.dirDefiners.AgressiveDirDefiner;
import org.example.test2.dirDefiners.EatStrategy;
import org.example.test2.dirDefiners.IDirDefiner;
import org.example.test2.enums.Dirs;
import org.example.test2.enums.PUBGEnum;
import org.example.test2.enums.TurnResCode;
import org.example.test2.snakes.EnemySnake;
import org.example.test2.snakes.Snake;
import org.example.test2.snakes.SnakesSynch;

import java.util.Random;

public class LevelPUBG implements Level {
    private final int[][] field;
    private int width, height;
    private int cntFr;
    private GcWorker gcWorker;
    private int startX, startY;
    private int dest;
    private int curpoints = 0;
    private SnakesSynch synch;
    private Pane pane;
    private ILogger logger;

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

    public LevelPUBG(int startX, int startY, Snake playerSnake, ILogger logger) {
        this.logger = logger;
        this.dest = 10;
        this.startX = startX;
        this.startY = startY;
        this.width = 30;
        this.height = 30;
        this.field = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                field[i][j] = PUBGEnum.Space.id;
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
        logger.print("playerShead: " + p.x + " " + p.y);
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
    public void addContext(GcWorker gc) {

        this.gcWorker = gc;

        startShow();


        KillingCircle circle = new KillingCircle(gc, field, pane, synch);

        IDirDefiner agressive = new AgressiveDirDefiner(field, 1, synch);
        EnemySnake snake1 = new EnemySnake(snake1Nodes, synch, field, agressive, Color.AQUA, gc, logger);
        snake1.setId(1);


        EatStrategy eat = new EatStrategy(field, 2, synch, circle);
        EnemySnake snake2 = new EnemySnake(snake2Nodes, synch, field, eat, Color.KHAKI, gc, logger);
        snake2.setId(2);


        showWithoutWait();

        snake1T = new Thread(snake1);
        snake2T = new Thread(snake2);
        circleT = new Thread(circle);


        snake1T.setDaemon(true);
        snake2T.setDaemon(true);
        circleT.setDaemon(true);
        snake1T.start();
        snake2T.start();
        circleT.start();
    }

    @Override
    public TurnResCode checkColAndMove() {

        synchronized (field) {
            synchronized (synch.fruits) {
                for (int i = synch.fruits.cnt; i < 3; i++) {
                    if (generateFruit())synch.fruits.cnt++;
                }
            }
        }

        if (!synch.infos[1].isAlive && !synch.infos[2].isAlive) {
            snake1T.interrupt();
            snake2T.interrupt();
            circleT.interrupt();
            return TurnResCode.Win;
        }
        boolean checkLoose = playerSnake.checkCircle(field);

        if (checkLoose) {
            snake1T.interrupt();
            snake2T.interrupt();
            circleT.interrupt();
            return TurnResCode.Lose;
        }

        Pair<Pair<Integer>> playerPairs = playerSnake.predictMoving();
        Pair<Integer> playerNextP = playerPairs.x;
        Pair<Integer> tail = playerPairs.y;
        if (playerNextP.x < 0 || playerNextP.y < 0 || playerNextP.x >= width || playerNextP.y >= height) {
            snake1T.interrupt();
            snake2T.interrupt();
            circleT.interrupt();
            return TurnResCode.Lose;
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
                    field[playerNextP.x][playerNextP.y] = PUBGEnum.Player.id;
                    field[tail.x][tail.y] = PUBGEnum.Space.id;
                }
                nodesToChange[curMax++] = new Pair<>(playerNextP.x, playerNextP.y);
                nodesToChange[curMax++] = new Pair<>(tail.x, tail.y);


                break;
            case -3:
                 try {
                     waitForOtherDirs();
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }

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

                 synchronized (field) {
                     generateFruit();
                 }


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

        switch (res) {
            case -2:
                return TurnResCode.Win;
            case -1:
                return TurnResCode.Lose;
            case 0:
                return TurnResCode.Nothing;
            case 1:
                return TurnResCode.Point;
            default:
                return TurnResCode.Nothing;
        }
    }

    @Override
    public void show() {

        showWithoutWait();
        curMax = 0;



        for (int i = 1; i < synch.infos.length; i++) {


            synchronized (synch.infos[i]) {
                try {
                    if (synch.infos[i].isAlive && synch.infos[i].turn != 3) {
                        logger.print("main " + i + " wait turn != 3 (turn = " + synch.infos[i].turn + ")");
                        synch.infos[i].wait();


                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                synch.infos[i].turn = 0;
                synch.infos[i].notify();
            }
        }


        playerSnake.updateMyHead();
    }


    private void showWithoutWait() {
        for (int i = 0; i < curMax; i++) {
            synchronized (field) {
                switch (field[nodesToChange[i].x][nodesToChange[i].y]) {
                    case -2:
                        gcWorker.print(nodesToChange[i], Color.BLACK);
                        break;
                    case -3:
                        gcWorker.print(nodesToChange[i], Color.RED);
                        break;
                    case 0:
                        gcWorker.print(nodesToChange[i], Color.WHITE);
                        break;

                    default:

                        gcWorker.print(nodesToChange[i], Color.BLUEVIOLET);
                        break;
                    }
                }
            }
            return;
    }

    private void startShow() {
        gcWorker.print(new Pair<>(0, 0), Color.BLACK, width, height);
        gcWorker.print(new Pair<>(1, 15), Color.AQUA, 5, 1);
        gcWorker.print(new Pair<>(25, 15), Color.KHAKI, 5, 1);
    }

    private boolean generateFruit() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            if (field[x][y] == PUBGEnum.Space.id) {
                field[x][y] = PUBGEnum.Fruit.id;
                nodesToChange[curMax++] = new Pair<>(x, y);
                return true;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (field[i][j] == PUBGEnum.Space.id) {
                    field[i][j] = PUBGEnum.Fruit.id;
                    nodesToChange[curMax++] = new Pair<>(i, j);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkStolk(Pair<Integer> pair) {
        return pair.x < 0 || pair.y < 0 || pair.x >= field.length || pair.y >= field[0].length ||
                field[pair.x][pair.y] == PUBGEnum.Death.id;
    }

    private void waitForOtherDirs() throws InterruptedException {
        for (int i = 1; i < synch.infos.length; i++) {


            synchronized (synch.infos[i]) {
                if (synch.infos[i].isAlive && synch.infos[i].turn != 1) {
                    logger.print("main " + i + " wait turn != 1 (turn = " + synch.infos[i].turn + ")");
                    synch.infos[i].wait();
                }
            }
        }


        for (int i = 1; i < synch.infos.length; i++) {
            synchronized (synch.infos[i]) {
                synch.infos[i].turn = 2;
                synch.infos[i].notify();
            }
        }
    }
}
