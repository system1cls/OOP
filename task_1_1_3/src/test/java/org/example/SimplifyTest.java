package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimplifyTest {

    @Test
    void simplify1() {
        Expression ex = Expression.make_expression("54*6/6");
        assertTrue(ex.simplify().equals(new Number(54)));
    }

    @Test
    void simplify2() {
        Expression ex = Expression.make_expression("54/7*7");
        assertTrue(ex.simplify().equals(new Number(54)));
    }

    @Test
    void simplify3() {
        Expression ex = Expression.make_expression("54+7+7");
        assertTrue(ex.simplify().equals(new Number(68)));
    }

    @Test
    void simplify4() {
        Expression ex = Expression.make_expression("54-7-7");
        assertTrue(ex.simplify().equals(new Number(40)));
    }
}