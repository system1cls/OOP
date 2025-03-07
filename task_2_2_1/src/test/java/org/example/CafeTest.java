package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CafeTest {

    @Test
    void cafeStartRandom1() {
        Cafe cafe = new Cafe();
        cafe.cafeStart(1, 1, 1, 1);
    }

    @Test
    void cafeStartRandom2() {
        Cafe cafe = new Cafe();
        cafe.cafeStart(2, 2, 2, 4);
    }

    @Test
    void cafeStartRandom3() {
        Cafe cafe = new Cafe();
        cafe.cafeStart(2, 2, 2, 0);
    }

    @Test
    void CafeStartFileWithLateOrder() {
        Cafe cafe = new Cafe();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "TestWithLateOrder.txt");
    }

    @Test
    void CafeStartFile2() {
        Cafe cafe = new Cafe();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "Test2.txt");
    }

    @Test
    void cafeStartFileWithManyPizza() {
        Cafe cafe = new Cafe();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "Test3.txt");
    }
}