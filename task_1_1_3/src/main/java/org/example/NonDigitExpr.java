package org.example;

import java.util.HashMap;

/**
 * Class to represent Sums, Subdivisions, Multiplies, Divisions.
 */
public class NonDigitExpr extends Expression {


    /**
     * Method to print NonDigitExpressions.
     */
    @Override
    public void print() {
        System.out.print("(");
        ex1.print();
        printValSym();
        ex2.print();
        System.out.print(")");
    }

    /**
     * Method to compare NonDigitExpression.
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
        return (newObj.ex1.equals(this.ex1) && newObj.ex2.equals(this.ex2));
    }


    /**
     * Method to check if Expression can be evaled.
     *
     * @param dict - dictionary var - value
     * @return can Expression be evaled.
     */
    @Override
    protected boolean checkEvalAbility(HashMap<String, Integer> dict) {
        return ex1.checkEvalAbility(dict) && ex2.checkEvalAbility(dict);
    }

    /**
     * Method to print sum symbol.
     */
    protected void printValSym() {
        System.out.print("?");
    }


    protected Expression ex1;
    protected Expression ex2;
}
