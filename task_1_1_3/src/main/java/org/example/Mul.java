package org.example;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class to represent Multiplies.
 */
public class Mul extends NonDigitExpr {

    /**
     * Class Mul constructor.
     *
     * @param ex1 first expression
     * @param ex2 second expression
     */
    Mul(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;
    }

    /**
     * Method to print sum symbol.
     */
    @Override
    protected void printValSym() {
        System.out.print("*");
    }

    /**
     * Method to get derivative.
     *
     * @param str var by which we take derivative.
     * @return derivative Expression
     */
    @Override
    public Expression derivative(String str) {
        return new Add(new Mul(ex1.derivative(str), ex2),
                new Mul(ex1, ex2.derivative(str)));
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
        return new Mul(ex1, ex2);
    }


    /**
     * Method to recursive calculating value.
     *
     * @param dict dictionary of vars and maps
     * @return Calculated value
     */
    @Override
    protected int rec_eval(HashMap<String, Integer> dict) {
        return (ex1.rec_eval(dict) * ex2.rec_eval(dict));
    }

    /**
     * Method to compare Mules.
     *
     * @param obj Object to compare
     * @return is objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        NonDigitExpr newObj = (NonDigitExpr) obj;
        return (newObj.ex1.equals(this.ex1) && newObj.ex2.equals(this.ex2))
                || ((newObj.ex1.equals(this.ex2) && newObj.ex2.equals(this.ex1)));
    }

    /**
     * Method to simplify Expression.
     *
     * @return Simplified Expression
     */
    @Override
    public Expression simplify() {
        Expression ex1;
        Expression ex2;

        ex1 = this.ex1.simplify();
        ex2 = this.ex2.simplify();


        if (Objects.equals(ex1, new Number(0)) || Objects.equals(ex2, new Number(0))) {
            return new Number(0);
        } else if (Objects.equals(ex1, new Number(1))) {
            return ex2;
        } else if (Objects.equals(ex2, new Number(1))) {
            return ex1;
        } else if (ex1.getClass() == Number.class && ex2.getClass() == Number.class) {
            Number exN1 = (Number) ex1;
            Number exN2 = (Number) ex2;
            return new Number(exN1.num * exN2.num);
        }

        return new Mul(ex1, ex2);


    }

}
