package org.example;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class to represent Subdivision.
 */
public class Sub extends NonDigitExpr {

    /**
     * Class Sub constructor.
     *
     * @param ex1 first expression
     * @param ex2 second expression
     */
    Sub(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    /**
     * Method to print sum symbol.
     */
    @Override
    protected void printValSym() {
        System.out.print("-");
    }

    /**
     * Method to get derivative.
     *
     * @param str var by which we take derivative.
     * @return derivative Expression
     */
    @Override
    public Expression derivative(String str) {
        return new Sub(ex1.derivative(str), ex2.derivative(str));
    }

    /**
     * Method to copy this Expression.
     *
     * @return copy of this Expression
     */
    @Override
    protected Expression copy() {
        Expression ex1 = this.ex1.copy();
        Expression ex2 = this.ex2.copy();
        return new Sub(ex1, ex2);
    }

    /**
     * Method to recursive calculating value.
     *
     * @param dict dictionary of vars and maps
     * @return Calculated value
     */
    @Override
    protected int rec_eval(HashMap<String, Integer> dict) {
        return (ex1.rec_eval(dict) - ex2.rec_eval(dict));
    }

    /**
     * Method to simplify Expression
     *
     * @return Simplified Expression
     */
    @Override
    public Expression simplify() {
        Expression ex1 = this.ex1.simplify();
        Expression ex2 = this.ex2.simplify();


        if (ex1.getClass() == Number.class && ex2.getClass() == Number.class) {
            Number exN1 = (Number) ex1;
            Number exN2 = (Number) ex2;
            return new Number(exN1.num - exN2.num);
        } else if (ex1.equals(ex2)) {
            return new Number(0);
        } else if (Objects.equals(ex2, new Number(0))) {
            return new Sub(ex1, ex2);
        } else if (Objects.equals(ex1, new Number(0))) {
            if (ex2.getClass() == Number.class) {
                Number exN2 = (Number) ex2;
                exN2.num *= -1;
                return exN2;
            } else {
                return new Mul(new Number(-1), ex2);
            }
        }
        return new Sub(ex1, ex2);

    }
}
