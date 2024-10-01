package org.example;

import java.util.HashMap;

import static java.lang.Character.isDigit;

/**
 * Class to represent Numbers.
 */
public class Number extends Expression {

    /**
     * Class Number constructor.
     *
     * @param num - number
     */
    Number(int num) {
        this.num = num;
    }

    /**
     * Method to compare Numbers.
     *
     * @param obj Object to compare
     * @return is objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Number num = (Number) obj;
        return num.num == this.num;
    }


    /**
     * Method to print Numbers.
     */
    @Override
    public void print() {
        System.out.print(this.num);
    }

    /**
     * Method to copy this Expression.
     *
     * @return copy of this Expression
     */
    @Override
    protected Expression copy() {
        return new Number(this.num);
    }


    /**
     * Method to get Number from str.
     *
     * @param str string with Expression
     * @param it  number of start_symbol in str
     * @return Parsed Expression
     */
    static public Expression my_get_expr(String str, myInt it) {

        int bit = 1;
        if (str.charAt(it.val) == '-') {
            bit = -1;
            it.val++;
        }
        int a = 0;
        while (str.length() > it.val && isDigit(str.charAt(it.val))) {
            a *= 10;
            a += (str.charAt(it.val)) - '0';
            it.val++;
        }
        return new Number(a * bit);
    }

    @Override
    protected int rec_eval(HashMap<String, Integer> dict) {
        return num;
    }


    int num;
}
