package org.example.test2;

public class SnakeNode {
    private int x, y;
    private int next, prev;

    SnakeNode(int x, int y, int next, int prev) {
        this.x = x;
        this.y = y;
        this.next = next;
        this.prev = prev;
    }

    public void setXY(Pair<Integer> p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public void setPrev(int prev) {
        this.prev = prev;
    }

    public Pair<Integer> getPair() {
        return new Pair<>(this.x, this.y);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(getPair().toString());
        builder.append("prev = ").append(prev).append("\nnext = ").append(next).append("\n");
        return builder.toString();
    }

    public int getNext() {
        return next;
    }

    public int getPrev() {
        return prev;
    }
}
