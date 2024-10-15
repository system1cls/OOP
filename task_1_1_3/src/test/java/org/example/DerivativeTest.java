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
        assertTrue(ex.derivative("x").simplify()
                .equals(new Mul(new Number(2), new Var("x"))));
    }

    @Test
    void derivative3() {
        Expression ex = Expression.make_expression("x*x + 24*x");
        assertTrue(ex.derivative("x").simplify()
                .equals(new Add(new Mul(new Number(2), new Var("x")), new Number(24))));
    }

    @Test
    void derivative4() {
        Expression ex = Expression.make_expression("x*x - 24*x");
        assertTrue(ex.derivative("x").simplify()
                .equals(new Sub(new Mul(new Number(2), new Var("x")), new Number(24))));
    }

    @Test
    void derivative5() {
        Expression ex = Expression.make_expression("x/5");
        assertTrue(ex.derivative("x").simplify()
                .equals(new Number(0)));
    }

    @Test
    void derivative6() {
        Expression ex = Expression.make_expression("x*x*x");
        ex.derivative("x").simplify().print();
    }

    @Test
    void derivative7() {
        Expression ex = Expression.make_expression("1/x");
        ex.derivative("x").simplify().print();
        assertTrue(ex.derivative("x").simplify()
                .equals(new Div(new Number(-1),
                        new Mul(new Var("x"), new Var("x")))));
    }

}