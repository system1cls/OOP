package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DerivativeTest {

    @Test
    void derivative1() {
        Expression ex = Expression.make_expression("x");
        assertTrue(ex.derivative("x").simplify().equals(new Number(1)));
    }

    @Test
    void derivative2() {
        Expression ex = Expression.make_expression("x*x");
        assertTrue(ex.derivative("x").simplify().equals(new Mul(new Number(2), new Var("x"))));
    }

    @Test
    void derivative3() {
        Expression ex = Expression.make_expression("x*x + 24*x");
        assertTrue(ex.derivative("x").simplify().
                equals(new Add(new Mul(new Number(2), new Var("x")), new Number(24))));
    }

    @Test
    void derivative4() {
        Expression ex = Expression.make_expression("x*x - 24*x");
        assertTrue(ex.derivative("x").simplify().
                equals(new Sub(new Mul(new Number(2), new Var("x")), new Number(24))));
    }

}