package org.example;

import java.util.HashMap;
import java.util.Stack;

import static java.lang.Character.isDigit;

/**
 * Ancestor of all Expressions.
 */
public class Expression {
    public void print() {
        System.out.print("");
    }

    /**
     * Method to get derivative.
     *
     * @param str var by which we take derivative.
     * @return derivative Expression
     */
    public Expression derivative(String str) {
        return new Number(0);
    }

    /**
     * Method to parse expression.
     *
     * @param str Expression in string
     * @return Parsed Expression
     */
    static public Expression make_expression(String str) {
        return Expression.get_expr_new(str, new myInt(0));
    }

    /**
     * Method to eval expression.
     *
     * @param params parameters
     * @return evales Expression
     */
    public int eval(String params) {
        HashMap<String, Integer> dict = get_dict(params);
        if (!checkEvalAbility(dict)) {
            System.out.print("No var value");
            System.exit(1);
        }
        return rec_eval(dict);
    }

    /**
     * Method to simplify Expression
     *
     * @return Simplified Expression
     */
    public Expression simplify() {
        return this.copy();
    }

    /**
     * Method to get parsed expression with added argument of iteration.
     *
     * @param str string with expression
     * @param it  number of start symbol
     * @return Expression
     */
    static private Expression get_expr_new(String str, myInt it) {
        str = Parser.pars(str);
        Stack<Expression> stack = new Stack<>();
        while (it.val < str.length()) {
            if (isDigit(str.charAt(it.val))) {
                stack.push(Number.my_get_expr(str, it));
            } else {
                Expression ex1;
                Expression ex2;
                switch (str.charAt(it.val)) {
                    case '+':
                        ex2 = stack.pop();
                        ex1 = stack.pop();
                        stack.push(new Add(ex1, ex2));
                        break;
                    case '-':
                        ex2 = stack.pop();
                        ex1 = stack.pop();
                        stack.push(new Sub(ex1, ex2));
                        break;
                    case '*':
                        ex2 = stack.pop();
                        ex1 = stack.pop();
                        stack.push(new Mul(ex1, ex2));
                        break;
                    case '/':
                        ex2 = stack.pop();
                        ex1 = stack.pop();
                        stack.push(new Div(ex1, ex2));
                        break;
                    case ' ':
                        break;
                    default:
                        stack.push(Var.my_get_expr(str, it));
                        it.val--;
                }
                it.val++;
            }
        }

        return stack.pop();
    }

    /**
     * Method to copy Expression.
     *
     * @return Copied Expression
     */
    protected Expression copy() {
        return new Expression();
    }

    /**
     * Method to get dictionary (Var->value)
     *
     * @param str string with vars and their value
     * @return dictionary(var - > value)
     */
    private HashMap<String, Integer> get_dict(String str) {
        int it = 0;
        int val;
        HashMap<String, Integer> dict = new HashMap<>();
        String name;
        while (it < str.length()) {
            name = "";
            val = 0;

            while (str.charAt(it) == ' ' || str.charAt(it) == '=' || str.charAt(it) == ';') {
                it++;
            }

            while ((str.charAt(it) >= 'a' && str.charAt(it) <= 'z')
                    || (str.charAt(it) >= 'A' && str.charAt(it) <= 'Z')) {
                name = new StringBuffer(name).insert(name.length(),
                        str.charAt(it)).toString();
                it++;
            }

            while (str.charAt(it) == ' ' || str.charAt(it) == '=') {
                it++;
            }

            while (it < str.length() && isDigit(str.charAt(it))) {
                val *= 10;
                val += str.charAt(it) - '0';
                it++;
            }

            dict.put(name, val);

            while (it < str.length() && (str.charAt(it) == ' ' || str.charAt(it) == '=' || str.charAt(it) == ';')) {
                it++;
            }
        }
        return dict;
    }

    /**
     * Method to check if Expression can be evaled.
     *
     * @param dict Dictionary (vat->value)
     * @return can Expression be evaled
     */
    protected boolean checkEvalAbility(HashMap<String, Integer> dict) {
        return true;
    }

    /**
     * Method for recursive eval.
     *
     * @param dict Dictionary (vat->value)
     * @return can Expression be evaled
     */
    protected int rec_eval(HashMap<String, Integer> dict) {
        return 0;
    }

}
