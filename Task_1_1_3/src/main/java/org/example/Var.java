package org.example;

import java.util.HashMap;

/**
 * Class to represent Variables.
 */
public class Var extends Expression {

    /**
     * Class Var constructor.
     *
     * @param str - Variable
     */
    Var(String str) {
        var = str;
    }


    /**
     * Method to compare Vars.
     *
     * @param obj Object to compare
     * @return is objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Var variable = (Var) obj;
        return variable.var.compareTo((this.var)) == 0;
    }


    /**
     * Method to print Variables.
     */
    @Override
    public void print() {
        System.out.print(var);
    }


    /**
     * Method to get Var from string str.
     *
     * @param str string with Expression
     * @param it  number of start_symbol in str
     * @return parsed var
     */
    public static Expression my_get_expr(String str, MyInt it) {
        Var ex = new Var("");
        while (str.length() > it.val && str.charAt(it.val) != '(' && str.charAt(it.val) != ')'
                && str.charAt(it.val) != '+' && str.charAt(it.val) != '-'
                && str.charAt(it.val) != '*' && str.charAt(it.val) != '/'
                && str.charAt(it.val) != ' ') {
            ex.var = new StringBuffer(ex.var)
                    .insert(ex.var.length(), str.charAt(it.val)).toString();
            it.val++;
        }
        return ex;
    }

    /**
     * Method to get derivative.
     *
     * @param str var by which we take derivative.
     * @return derivative Expression
     */
    @Override
    public Expression derivative(String str) {
        Expression num;
        if (str.compareTo(this.var) == 0) {
            num = new Number(1);
        } else {
            num = new Number(0);
        }
        return num;
    }

    /**
     * Method to copy this Expression.
     *
     * @return copy of this Expression
     */
    protected Expression copy() {
        return new Var(this.var);
    }

    protected boolean checkEvalAbility(HashMap<String, Integer> dict) {
        return dict.containsKey(var);
    }

    /**
     * Method to recursive calculating value.
     *
     * @param dict dictionary of vars and maps
     * @return Calculated value
     */
    @Override
    protected int rec_eval(HashMap<String, Integer> dict) {
        return dict.get(var);
    }

    private String var;
}
