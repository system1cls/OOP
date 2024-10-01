package org.example;

import java.util.HashMap;
import java.util.Stack;

import static java.lang.Character.isDigit;

/**
 * Class for rewriting string in Reverse Polish notation.
 */
public class Parser {


    /**
     * Method to rewrite string in Reverse Polish notation.
     *
     * @param str string to pars
     * @return parsed string in Reverse Polish notation
     */
    static public String pars(String str) {
        HashMap<Character, Integer> opPrior = new HashMap<>();
        opPrior.put('(', 0);
        opPrior.put('+', 1);
        opPrior.put('-', 1);
        opPrior.put('*', 2);
        opPrior.put('/', 2);

        Stack<Character> stack = new Stack<>();
        String end_str = "";
        Character ch;
        int it = 0;
        while (it < str.length()) {
            if (isDigit(str.charAt(it))) {

                while (it < str.length() && isDigit(str.charAt(it))) {
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), str.charAt(it)).toString();
                    it++;
                }

                end_str = new StringBuffer(end_str)
                        .insert(end_str.length(), ' ').toString();
            } else if (str.charAt(it) == '(') {
                stack.push('(');
                it++;
            } else if (str.charAt(it) == ')') {
                while ((ch = stack.pop()) != '(') {
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), ch).toString();
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), " ").toString();
                }
                it++;
            } else if (opPrior.containsKey(str.charAt(it))) {
                if (stack.isEmpty()) {
                    stack.push(str.charAt(it));
                } else if (opPrior.get(stack.peek()) >= opPrior.get(str.charAt(it))) {
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), stack.pop()).toString();
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), " ").toString();
                    stack.push(str.charAt(it));
                } else {
                    stack.push(str.charAt(it));
                }
                it++;
            } else if (str.charAt(it) == ' ') {
                it++;
            } else {
                while (it < str.length() && ((str.charAt(it) >= 'a' && str.charAt(it) <= 'z')
                        || (str.charAt(it) >= 'A' && str.charAt(it) <= 'Z'))) {
                    end_str = new StringBuffer(end_str)
                            .insert(end_str.length(), str.charAt(it)).toString();
                    it++;
                }
                end_str = new StringBuffer(end_str)
                        .insert(end_str.length(), ' ').toString();
            }
        }

        while (!stack.isEmpty()) {
            end_str = new StringBuffer(end_str)
                    .insert(end_str.length(), stack.pop()).toString();
            end_str = new StringBuffer(end_str)
                    .insert(end_str.length(), " ").toString();
        }

        end_str = new StringBuffer(end_str).delete(end_str.length() - 1, end_str.length()).toString();
        return end_str;
    }
}
