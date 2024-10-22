package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class EvalTest {

    @Test
    void evalTest1() {
        Expression ex = Expression.make_expression("1+2+3+4+5+6+7+8+9");
        assertEquals(45, ex.eval(""));
    }

    @Test
    void evalTest2() {
        Expression ex = Expression.make_expression("1+2*x");
        assertEquals(11, ex.eval("x=5"));
    }

    @Test
    void evalTest3() {
        Expression ex = Expression.make_expression("50/11");
        assertEquals(4, ex.eval(""));
    }

    @Test
    void evalTest4() {
        Expression ex = Expression.make_expression("5*6/3+24-(x*x+y)");
        assertEquals(30, ex.eval("x=2;y=0; z = 6"));
    }

    @Test
    void evalTest5() {
        Expression ex = Expression.make_expression("1-2-3-4-5-6-7-8-9");
        assertEquals(-43, ex.eval("x=2;y=0; z = 6"));
    }

}