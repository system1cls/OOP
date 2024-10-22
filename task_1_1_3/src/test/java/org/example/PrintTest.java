package org.example;

import org.junit.jupiter.api.Test;

class PrintTest {

    @Test
    void print1() {
        Expression.make_expression("1+2*(3*4)/5+x-y*z").print();
    }

    @Test
    void print2() {
        Expression.make_expression("x*y*z*q*w*r*t/y/i/h/k-6-8").print();
    }
}