package org.example;

import java.util.HashMap;


/**
 * Class for finding.
 */
public class Finding {

    /**
     * Static method for finding.
     *
     * @param fileName file for input
     * @param string mask to find
     * @return array of int of places where mask was found
     */
    static int[] find(String fileName, String string) {
        HashMap<Character, Integer> dictFirst = new HashMap<>();
        HashMap<Integer, Integer> dictSecond = new HashMap<>();
        Pair<Character, Integer> pair;
        int []answer = new int[100];
        int[] temp;
        int ansSize = 0;
        int maxSize = 100;
        char []charArr = string.toCharArray();
        CirckedBuffer buffer = new CirckedBuffer(string.length(), fileName);


        for (int i = 0; i < string.length(); i++) {
            dictFirst.put(charArr[i], string.length() - i);
        }

        boolean suitable;
        for (int i = string.length() - 1; i >= 0; i--) {
            suitable = true;
            for (int j = i - 1; j >= 0; j--) {
                if (charArr[j] == charArr[i]) {
                    for (int k = 0; k < (string.length() - i); k++) {
                        if (charArr[j + k] != charArr[i + k]) {
                            suitable = false;
                            break;
                        }
                    }

                    if (suitable) {
                        dictSecond.put(i, j);
                        break;
                    } else {
                        suitable = true;
                    }
                }
            }

            if (!dictSecond.containsKey(i)) {
                dictSecond.put(i, -1);
            }
        }

        int charToSkip = 0;
        int curIt = 0;

        while(buffer.getNext(charToSkip)) {
            pair = buffer.checkString(charArr);
            if (pair.value == -1) {

                if (ansSize == maxSize) {
                    maxSize += 100;
                    temp = new int[maxSize];
                    System.arraycopy(answer, 0, temp, 0, maxSize - 100);
                    answer = temp;
                }

                answer[ansSize++] = curIt;
                charToSkip = 1;
            } else {
                if (dictFirst.containsKey(pair.key)) {
                    charToSkip = dictFirst.get(pair.key) + pair.value - string.length();

                    if (charToSkip <= 0) {
                        charToSkip = 0;
                        for (int i = pair.value + 1; i < string.length(); i++) {
                            if (dictSecond.get(i) != -1) {
                                charToSkip = i - dictSecond.get(i);
                            }
                        }

                        if (charToSkip == 0) {
                            charToSkip = string.length();
                        }
                    }
                } else {
                    charToSkip = pair.value + 1;
                }
            }

            curIt += charToSkip;
        }

        temp = new int[ansSize];
        System.arraycopy(answer, 0, temp, 0, ansSize);
        answer = temp;
        return answer;
    }
}
