package org.example;

import java.util.HashMap;
import java.util.Objects;

/**
 * Class to represent Divisions.
 */
public class Div extends NonDigitExpr {

    /**
     * Class Div constructor.
     *
     * @param ex1 first expression
     * @param ex2 second expression
     */
    Div(Expression ex1, Expression ex2) {
        this.ex1 = ex1;
        this.ex2 = ex2;

    }

    /**
     * Method to print sum symbol.
     */
    @Override
    protected void printValSym() {
        System.out.print("/");
    }

    /**
     * Method to get derivative.
     *
     * @param str var by which we take derivative.
     * @return derivative Expression
     */
    @Override
    public Expression derivative(String str) {
        Expression nominator = new Add(new Mul(ex1.derivative(str), ex2.copy()),
                new Mul(new Number(-1), new Mul(ex1.copy(), ex2.derivative(str))));
        Expression denominator = new Mul(this.ex2.copy(), this.ex2.copy());
        return new Div(nominator, denominator);
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
        return new Div(ex1, ex2);
    }

    /**
     * Method to recursive calculating value.
     *
     * @param dict dictionary of vars and maps
     * @return Calculated value
     */
    @Override
    protected int rec_eval(HashMap<String, Integer> dict) {
        return (ex1.rec_eval(dict) / ex2.rec_eval(dict));
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

        if (Objects.equals(ex1, new Number(0))) {
            return new Number(0);
        } else if (Objects.equals(ex2, new Number(1))) {
            return ex1;
        } else if (ex1.getClass() == Number.class && ex2.getClass() == Number.class) {
            Number exN1 = (Number) ex1;
            Number exN2 = (Number) ex2;
            return new Number(exN1.num / exN2.num);
        }

        if (ex1.getClass() == Number.class) {
            if (ex2.getClass() == Mul.class) {
                Mul exA2 = (Mul) ex2;
                if (exA2.ex1.getClass() == Number.class) {
                    Number ex_new = (Number) exA2.ex1;
                    ((Number) ex1).num /= ex_new.num;
                    return new Div(ex1, exA2.ex2).simplify();
                } else if (exA2.ex2.getClass() == Number.class) {
                    Number ex_new = (Number) exA2.ex2;
                    ((Number) ex1).num /= ex_new.num;
                    return new Div(ex1, exA2.ex1).simplify();
                }
            }
            if (ex2.getClass() == Div.class) {
                Div exA2 = (Div) ex2;
                if (exA2.ex1.getClass() == Number.class) {
                    Number ex_new = (Number) exA2.ex1;
                    ((Number) ex1).num /= ex_new.num;
                    return new Mul(ex1, exA2.ex2).simplify();
                } else if (exA2.ex2.getClass() == Number.class) {
                    Number ex_new = (Number) exA2.ex2;
                    ((Number) ex1).num *= ex_new.num;
                    return new Div(ex1, exA2.ex1).simplify();
                }
            }
        } else if (ex2.getClass() == Number.class) {
            if (ex1.getClass() == Mul.class) {
                Mul exA1 = (Mul) ex1;
                if (exA1.ex1.getClass() == Number.class) {
                    Number ex_new = (Number) exA1.ex1;
                    ex_new.num /= ((Number) ex2).num;
                    return exA1.simplify();
                } else if (exA1.ex2.getClass() == Number.class) {
                    Number ex_new = (Number) exA1.ex2;
                    ex_new.num /= ((Number) ex2).num;
                    return exA1.simplify();
                }
            }
            if (ex1.getClass() == Div.class) {
                Div exA1 = (Div) ex1;
                if (exA1.ex1.getClass() == Number.class) {
                    Number ex_new = (Number) exA1.ex1;
                    ex_new.num /= ((Number) ex2).num;
                    return exA1.simplify();
                } else if (exA1.ex2.getClass() == Number.class) {
                    Number ex_new = (Number) exA1.ex2;
                    ex_new.num *= ((Number) ex2).num;
                    return new Div(exA1.ex1, ex_new).simplify();
                }
            }
        }


        return new Mul(ex1, ex2);
    }
}