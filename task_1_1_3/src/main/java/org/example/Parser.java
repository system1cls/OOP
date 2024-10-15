package org.example;

import static java.lang.Character.isDigit;

import java.util.HashMap;
import java.util.Stack;


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
    public static String pars(String str) {
        HashMap<Character, Integer> opPrior = new HashMap<>();
        opPrior.put('(', 0);
        opPrior.put('+', 1);
        opPrior.put('-', 1);
        opPrior.put('*', 2);
        opPrior.put('/', 2);

        Stack<Character> stack = new Stack<>();
        String endStr = "";
        Character ch;
        int it = 0;
        while (it < str.length()) {
            if (isDigit(str.charAt(it))) {

                while (it < str.length() && isDigit(str.charAt(it))) {
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), str.charAt(it)).toString();
                    it++;
                }

                endStr = new StringBuffer(endStr)
                        .insert(endStr.length(), ' ').toString();
            } else if (str.charAt(it) == '(') {
                stack.push('(');
                it++;
            } else if (str.charAt(it) == ')') {
                while ((ch = stack.pop()) != '(') {
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), ch).toString();
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), " ").toString();
                }
                it++;
            } else if (opPrior.containsKey(str.charAt(it))) {
                if (stack.isEmpty()) {
                    stack.push(str.charAt(it));
                } else if (opPrior.get(stack.peek()) >= opPrior.get(str.charAt(it))) {
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), stack.pop()).toString();
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), " ").toString();
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
                    endStr = new StringBuffer(endStr)
                            .insert(endStr.length(), str.charAt(it)).toString();
                    it++;
                }
                endStr = new StringBuffer(endStr)
                        .insert(endStr.length(), ' ').toString();
            }
        }

        while (!stack.isEmpty()) {
            endStr = new StringBuffer(endStr)
                    .insert(endStr.length(), stack.pop()).toString();
            endStr = new StringBuffer(endStr)
                    .insert(endStr.length(), " ").toString();
        }

        endStr = new StringBuffer(endStr).delete(endStr.length() - 1,
                endStr.length()).toString();
        return endStr;
    }
}
