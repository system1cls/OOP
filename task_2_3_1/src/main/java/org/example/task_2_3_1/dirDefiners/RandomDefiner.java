package org.example.test2.dirDefiners;

import org.example.test2.enums.Dirs;
import org.example.test2.auxillary.Pair;

import java.util.Random;

public class RandomDefiner implements IDirDefiner {
    private final int field[][];

    public RandomDefiner(int field[][]) {
        this.field = field;
    }


    @Override
    public Dirs defineDir(Pair<Integer> head) {
        Dirs dir = Dirs.Left;
        Random random = new Random();
        int num = random.nextInt() % 4;
        num = num >= 0 ? num : num + 4;
        switch (num) {
            case 0:
                dir = Dirs.Up;
                break;
            case 1:
                dir = Dirs.Right;
                break;
            case 2:
                dir = Dirs.Down;
                break;
            case 3:
                dir = Dirs.Left;
                break;
        }

        dir = setPredictedOrClear(head, dir);

        return dir;
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
                    if (field[predicted.x][predicted.y] != -3) field[predicted.x][predicted.y] = -4;
                    else field[predicted.x][predicted.y] = -5;
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
                (field[pair.x][pair.y] != -2 && field[pair.x][pair.y] != -3);
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
