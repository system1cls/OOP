package org.example;

import org.junit.jupiter.api.Test;

class PrintTest {

    @Test
    void print() {
        Expression.make_expression("1+2*(3*4)/5+x-y*z").print();
    }
}