package org.example;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Expression e = Expression.make_expression("(x-2)+2");
        e.simplify().print();
    }
}