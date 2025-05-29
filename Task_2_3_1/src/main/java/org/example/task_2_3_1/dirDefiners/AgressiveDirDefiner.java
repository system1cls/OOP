package org.example.test2.dirDefiners;


import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;
import org.example.test2.enums.PUBGEnum;
import org.example.test2.snakes.SnakesSynch;

public class AgressiveDirDefiner implements IDirDefiner {
    private final int [][]field;
    private final int id;
    private final SnakesSynch synch;


    public AgressiveDirDefiner(int [][]field, int id, SnakesSynch synch) {
        this.field = field;
        this.id = id;
        this.synch = synch;
    }

    @Override
    public Dirs defineDir(Pair<Integer> headP) {
        Dirs dir, safeDir;
        int minId = -1;
        double minDistance = 1000000.;


        for (int i = 0; i < 2; i++) {
            if (i == id) continue;

            synchronized (synch.infos[i]) {
                Pair<Integer> anotherHead = synch.infos[i].head;
                double dist = Math.sqrt((headP.x - anotherHead.x) * (headP.x - anotherHead.x) +
                        (headP.y - anotherHead.y) * (headP.y - anotherHead.y));
                if (synch.infos[i].isAlive && dist < minDistance) {
                    minDistance = dist;
                    minId = i;
                }
            }
        }
        Pair<Integer> anotherHead;
        Dirs anotherDir;

        System.out.println(id + ": dist = " + minDistance + " id = " + minId);


        synchronized (synch.infos[minId]) {
            anotherHead = synch.infos[minId].head;
            anotherDir = synch.infos[minId].dir;
        }

        System.out.println(id + ": EnemyHead = " + anotherHead);
        Pair<Integer> predicted = predictEnemyMoving(anotherHead, anotherDir, 2);



        if (predicted.y > headP.y) {
            if (predicted.x > headP.x) {
                if (predicted.y - headP.y > predicted.x - headP.x) {
                    dir = Dirs.Down;
                    safeDir = Dirs.Right;
                }
                else {
                    dir = Dirs.Right;
                    safeDir = Dirs.Down;
                }
            }
            else {
                if (predicted.y - headP.y > headP.x - predicted.x) {
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
            if (predicted.x > headP.x) {
                if (headP.y - predicted.y > predicted.x - headP.x) {
                    dir = Dirs.Up;
                    safeDir = Dirs.Right;
                }
                else {
                    dir = Dirs.Right;
                    safeDir = Dirs.Up;
                }
            }
            else {
                if (headP.y - predicted.y > headP.x - predicted.x) {
                    dir = Dirs.Up;
                    safeDir = Dirs.Left;
                }
                else {
                    dir = Dirs.Left;
                    safeDir = Dirs.Up;
                }
            }
        }

        System.out.println(id + ": dir = " + dir);

        return setPredictedOrClear(headP, dir, safeDir);
    }

    private Dirs setPredictedOrClear(Pair<Integer> headP, Dirs dir, Dirs safeDir) {
        boolean shouldCheck = true;
        Dirs endDir = dir;
        Pair<Integer> predicted;

        synchronized (field) {

            predicted = this.predictMoving(headP, dir);
            if (!checkStolk(predicted)) {
                shouldCheck = false;
                if (field[predicted.x][predicted.y] == PUBGEnum.Fruit.id) field[predicted.x][predicted.y] = PUBGEnum.WannaEat.id;
                else field[predicted.x][predicted.y] = PUBGEnum.WannaGo.id;
            } else {
              predicted = predictMoving(headP, safeDir);
              if (!checkStolk(predicted)) {
                  shouldCheck = false;
                  endDir = safeDir;
                  if (field[predicted.x][predicted.y] == PUBGEnum.Fruit.id) field[predicted.x][predicted.y] = PUBGEnum.WannaEat.id;
                  else field[predicted.x][predicted.y] = PUBGEnum.WannaGo.id;
              }
            }


            for (int i = 0; i < 4 & shouldCheck; i++) {
                predicted = this.predictMoving(headP, dir);

                if (this.checkStolk(predicted)) {
                    dir = setNextDir(dir);
                } else {
                    shouldCheck = false;
                    endDir = dir;
                    if (field[predicted.x][predicted.y] != PUBGEnum.Fruit.id) field[predicted.x][predicted.y] = PUBGEnum.WannaGo.id;
                    else field[predicted.x][predicted.y] = PUBGEnum.WannaEat.id;
                }
            }
        }

        if (shouldCheck) return Dirs.Clear;
        else return endDir;
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

    private Pair<Integer> predictEnemyMoving(Pair<Integer> head, Dirs dir, int cntStep) {
        int newY, newX;

        switch (dir) {
            case Up:
                newY = head.y >= cntStep ? head.y - cntStep : 0;
                return new Pair<>(head.x, newY);
            case Down:
                newY = head.y < field[0].length - cntStep ? head.y + cntStep : field[0].length;
                return new Pair<>(head.x, newY);
            case Left:
                newX = head.x >= cntStep ? head.x - cntStep : 0;
                return  new Pair<>(newX, head.y);
            case Right:
                newX = head.x < field.length - cntStep ? head.x + cntStep : field.length;
                return new Pair<>(newX, head.y);
        }
        return null;
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
}
