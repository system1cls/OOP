package org.example.test2.dirDefiners;

import org.example.test2.auxillary.KillingCircle;
import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;
import org.example.test2.enums.PUBGEnum;
import org.example.test2.snakes.SnakesSynch;

public class EatStrategy implements IDirDefiner {
    private final int[][] field;
    private final int id;
    private final SnakesSynch synch;
    private AgressiveDirDefiner myAgressiveDif;
    private final KillingCircle circle;

    public EatStrategy(int [][]field, int id, SnakesSynch synch, KillingCircle circle) {
        this.field = field;
        this.id = id;
        this.synch = synch;
        this.myAgressiveDif = new AgressiveDirDefiner(field, id, synch);
        this.circle = circle;
    }

    @Override
    public Dirs defineDir(Pair<Integer> head) {
        Dirs dir = Dirs.Clear, safeDir;

        Pair <Integer> answer = null;


        synchronized (circle) {
            if (circle.curRad - Math.sqrt(Math.pow(head.x - circle.X, 2) + Math.pow(head.y - circle.Y, 2)) < 3.0) {
                answer = new Pair<>(Math.toIntExact(Math.round(circle.X / 10)), Math.toIntExact(Math.round(circle.Y / 10)));
            }
        }


        if (answer == null) {
            synchronized (field) {

                int minDist = 10000;

                for (int x = 0; x < field.length; x++) {
                    for (int y = 0; y < field[x].length; y++) {
                        if (field[x][y] == PUBGEnum.Fruit.id && (calcDist(head, new Pair<>(x, y)) < minDist)) {
                            answer = new Pair<>(x, y);
                        }
                    }
                }
            }
        }


        if (answer == null) {
            return invertDir(myAgressiveDif.defineDir(head));
        }


        if (answer.y > head.y) {
            if (answer.x > head.x) {
                if (answer.y - head.y > answer.x - head.x) {
                    dir = Dirs.Down;
                    safeDir = Dirs.Right;
                }
                else {
                    dir = Dirs.Right;
                    safeDir = Dirs.Down;
                }
            }
            else {
                if (answer.y - head.y > head.x - answer.x) {
                    dir = Dirs.Down;
                    safeDir = Dirs.Left;
                }
                else {
                    dir = Dirs.Left;
                    safeDir = Dirs.Down;
                }
            }
        }
        else {
            if (answer.x > head.x) {
                if (head.y - answer.y > answer.x - head.x) {
                    dir = Dirs.Up;
                    safeDir = Dirs.Right;
                }
                else {
                    dir = Dirs.Right;
                    safeDir = Dirs.Up;
                }
            }
            else {
                if (head.y - answer.y > head.x - answer.x) {
                    dir = Dirs.Up;
                    safeDir = Dirs.Left;
                }
                else {
                    dir = Dirs.Left;
                    safeDir = Dirs.Up;
                }
            }
        }


        return setPredictedOrClear(head, dir);
    }

    private Dirs invertDir(Dirs dir) {
        switch(dir) {
            case Up:
                return Dirs.Down;
            case Down:
                return Dirs.Up;
            case Left:
                return Dirs.Right;
            case Right:
                return Dirs.Left;
            default:
                return Dirs.Clear;
        }
    }

    private Dirs setPredictedOrClear(Pair<Integer> headP, Dirs dir) {
        boolean shouldCheck = true;

        Pair<Integer> predicted;

        synchronized (field) {
            for (int i = 0; i < 4 & shouldCheck; i++) {
                predicted = this.predictMoving(headP, dir);

                if (this.checkStolk(predicted)) {
                    dir = setNextDir(dir);
                } else {
                    shouldCheck = false;
                    if (field[predicted.x][predicted.y] != PUBGEnum.Fruit.id) field[predicted.x][predicted.y] = PUBGEnum.WannaGo.id;
                    else field[predicted.x][predicted.y] = PUBGEnum.WannaEat.id;
                }
            }
        }


        if (shouldCheck) return Dirs.Clear;
        else return dir;
    }

    private Dirs setNextDir(Dirs dir) {
        switch (dir) {
            case Up:
                dir = Dirs.Right;
                return dir;
            case Right:
                dir = Dirs.Down;
                return dir;
            case Down:
                dir = Dirs.Left;
                return dir;
            case Left:
                dir = Dirs.Up;
                return dir;
        }
        return dir;
    }

    private boolean checkStolk(Pair<Integer> pair) {
        return pair.x < 0 || pair.y < 0 || pair.x >= field.length || pair.y >= field[0].length ||
                (field[pair.x][pair.y] != PUBGEnum.Space.id && field[pair.x][pair.y] != PUBGEnum.Fruit.id);
    }

    private Pair<Integer> predictMoving(Pair<Integer> head, Dirs dir) {
        int newY, newX;

        switch (dir) {
            case Up:
                newY = head.y - 1;
                return new Pair<>(head.x, newY);
            case Down:
                newY = head.y + 1;
                return new Pair<>(head.x, newY);
            case Left:
                newX = head.x - 1;
                return  new Pair<>(newX, head.y);
            case Right:
                newX = head.x + 1;
                return new Pair<>(newX, head.y);
        }
        return null;
    }

    private double calcDist(Pair<Integer> head, Pair<Integer> dest) {
        return Math.sqrt(Math.pow(head.x - dest.x, 2) + Math.pow(head.y - dest.y, 2));
    }
}
