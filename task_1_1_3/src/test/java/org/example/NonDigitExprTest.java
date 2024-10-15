package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class NonDigitExprTest {

    @Test
    void equalsTest() {
        assertTrue(new Mul(new Number(5), new Var("x"))
                .equals(new Mul(new Number(5), new Var("x"))));
        assertTrue(new Div(new Number(5), new Var("x"))
                .equals(new Div(new Number(5), new Var("x"))));
        assertTrue(new Add(new Number(5), new Var("x"))
                .equals(new Add(new Number(5), new Var("x"))));
        assertTrue(new Sub(new Number(5), new Var("x"))
                .equals(new Sub(new Number(5), new Var("x"))));
    }


}