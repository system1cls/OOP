package org.example.task_2_3_1;

public class Pair<T> {
    public T x, y;

    Pair(T newX, T newY){
        this.x = newX;
        this.y = newY;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("x = ").append(x).append("\ny = ").append(y).append("\n");
        return builder.toString();
    }
}
