package org.example.test2.enums;

public enum TurnResCode {
    Lose(-1),
    Win(-2),
    Nothing(0),
    Point(1)
    ;

    public final int id;
    TurnResCode(int id) {
        this.id = id;
    }
}
