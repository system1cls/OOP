package org.example.test2.snakes;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.example.test2.GcWorkers.EmptyGcWorker;
import org.example.test2.Logger.ILogger;
import org.example.test2.Logger.SaveLogger;
import org.example.test2.Logger.SimpleLogger;
import org.example.test2.auxillary.KillingCircle;
import org.example.test2.auxillary.Pair;
import org.example.test2.dirDefiners.AgressiveDirDefiner;
import org.example.test2.dirDefiners.EatStrategy;
import org.example.test2.dirDefiners.IDirDefiner;
import org.example.test2.enums.Dirs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Snakes {

    int [][]field = new int[30][30];
    int startSnakesX1[] = new int[] {5, 4, 3, 2, 1};
    int startSnakesY1[] = new int[] {15, 15, 15, 15, 15};
    Dirs dirSnake1 = Dirs.Right;


    @Test
    public void AggressiveStrategy1() {
        makeField(new Pair<>(25, 15), false);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(25, 15), 0, Dirs.Left);

        EnemySnake snake = makeSnake(new AgressiveDirDefiner(field, 1, synch), synch);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Right);

    }

    @Test
    public void AggressiveStrategy2() {
        makeField(new Pair<>(5, 10), false);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        EnemySnake snake = makeSnake(new AgressiveDirDefiner(field, 1, synch), synch);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Up);

        Pair<Pair<Integer>> headAndTail = snake.predictMoving();

        snake.move(headAndTail);

        assertEquals(snake.getCurHead().y, 14);
        assertEquals(snake.getCurHead().x, 5);
        assertEquals(snake.cntNodes, 5);
    }

    @Test
    public void AggressiveStrategy3() {
        makeField(new Pair<>(5, 10), false);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        SaveLogger logger = new SaveLogger();
        EnemySnake snake = makeSnake(new AgressiveDirDefiner(field, 1, synch), synch, logger);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Up);

        for (int i = 0; i < 3; i++) {
            Pair<Pair<Integer>> headAndTail = snake.predictMoving();
            snake.move(headAndTail);
            snake.print();
        }



        assertEquals(logger.list.get(logger.list.size() - 3), "(5, 14)-(5, 15)-(4, 15)-(3, 15)-(2, 15)-");
        assertEquals(logger.list.get(logger.list.size() - 2), "(5, 13)-(5, 14)-(5, 15)-(4, 15)-(3, 15)-");
        assertEquals(logger.list.get(logger.list.size() - 1), "(5, 12)-(5, 13)-(5, 14)-(5, 15)-(4, 15)-");

    }

    @Test
    public void EatStrategy1() {
        makeField(new Pair<>(25, 10), true);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        KillingCircle circle = new KillingCircle(new EmptyGcWorker(), field, new Pane(), synch);

        EnemySnake snake = makeSnake(new EatStrategy(field, 1, synch, circle), synch);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Right);
    }

    @Test
    public void EatStrategy2() {
        makeField(new Pair<>(25, 10), false);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        KillingCircle circle = new KillingCircle(new EmptyGcWorker(), field, new Pane(), synch);
        EnemySnake snake = makeSnake(new EatStrategy(field, 1, synch, circle), synch);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Down);
    }

    @Test
    public void EatStrategy3() {
        makeField(new Pair<>(6, 15), true);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        KillingCircle circle = new KillingCircle(new EmptyGcWorker(), field, new Pane(), synch);
        EnemySnake snake = makeSnake(new EatStrategy(field, 1, synch, circle), synch);

        assertEquals(snake.getCurHead().x, 5);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());

        assertEquals(snake.dir, Dirs.Right);

        Pair<Pair<Integer>> headAndTail = snake.predictMoving();
        assertEquals(field[headAndTail.x.x][headAndTail.x.y], -5);

        snake.add(headAndTail);

        assertEquals(snake.getCurHead().x, 6);
        assertEquals(snake.getCurHead().y, 15);
        assertEquals(snake.cntNodes, 6);
    }

    @Test
    public void testDeath() {
        makeField(new Pair<>(6, 15), true);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        KillingCircle circle = new KillingCircle(new EmptyGcWorker(), field, new Pane(), synch);
        EnemySnake snake = makeSnake(new EatStrategy(field, 1, synch, circle), synch);

        field[1][15] = -1;
        assertTrue(snake.checkCircle(field));
    }

    @Test
    public void testAvoiding() {
        makeField(new Pair<>(6, 15), true);

        SnakesSynch synch = new SnakesSynch(2);

        synch.infos[0] = new SnakesSynch.SnakeInfo(new Pair<>(5, 10), 0, Dirs.Right);

        KillingCircle circle = new KillingCircle(new EmptyGcWorker(), field, synch, 0, 140, 75);
        EnemySnake snake = makeSnake(new EatStrategy(field, 1, synch, circle), synch);

        snake.dir = snake.strategy.defineDir(snake.getCurHead());
        assertEquals(snake.dir, Dirs.Up);
    }

    private void makeField(Pair<Integer> anotherHeadOrFruit, boolean isFruit) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = -2;
            }
        }

        for (int i = 0; i < startSnakesX1.length; i++) {
            field[startSnakesX1[i]][startSnakesY1[i]] = 1;
        }

        if (isFruit) {
            field[anotherHeadOrFruit.x][anotherHeadOrFruit.y] = -3;
        }
        else {
            field[anotherHeadOrFruit.x][anotherHeadOrFruit.y] = 0;
        }
    }

    private EnemySnake makeSnake(IDirDefiner dirDefiner, SnakesSynch synch) {

        return makeSnake(dirDefiner, synch, new SimpleLogger());
    }

    private EnemySnake makeSnake(IDirDefiner dirDefiner, SnakesSynch synch, ILogger logger) {

        Pair<Integer>[]snake1Nodes = new Pair[5];

        for (int i = 0; i < startSnakesX1.length; i++) {
            snake1Nodes[i] = new Pair<>(startSnakesX1[i], startSnakesY1[i]);
        }

        synch.infos[1] = new SnakesSynch.SnakeInfo(snake1Nodes[0], 1, dirSnake1);


        EnemySnake snake = new EnemySnake(snake1Nodes, synch, field, dirDefiner, Color.RED, new EmptyGcWorker(), logger);
        snake.setId(1);
        return snake;
    }
}
