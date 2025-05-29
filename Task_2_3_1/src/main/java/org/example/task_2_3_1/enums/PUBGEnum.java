package org.example.test2.enums;

public enum PUBGEnum {
    WannaEat(-5),
    WannaGo(-4),
    Fruit(-3),
    Death(-1),
    Space(-2),
    Player(0),
    Snake1(1),
    Snake2(2)
    ;

    public final int id;
    PUBGEnum(int id) {
        this.id = id;
    }
}
