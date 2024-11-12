package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class for representing Circled Buffer.
 */
public class CirckedBuffer {
    char []str;
    int size;
    int it;
    FileInputStream stream;


    /**
     * Constructor.
     *
     * @param size max cnt of elements
     * @param fileName file from where get info
     */
    CirckedBuffer(int size, String fileName) {
        this.size = size;
        str = new char[size];
        it = size - 1;
        File file = new File(fileName);
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.print("Not found" + fileName);
            throw new RuntimeException(ex);
        }

        for (int i = 0; i < size; i++) {
            try {
                str[i] = (char)stream.read();
            } catch (IOException e) {
                System.out.print("read error");
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * @param cntNext count of chars to get
     * @return is file ended
     */
    public boolean getNext(int cntNext) {
        for (int i = 0; i < cntNext; i++) {
            if (!setNext()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set new char.
     *
     * @return is file ended
     */
    private boolean setNext() {
        int ch;
        int newIt = getNextIt(it);
        try {
            ch = stream.read();
            if (ch == -1) {
                return false;
            } else {
                str[newIt] = (char)ch;
                this.it = newIt;
            }

        } catch (IOException e) {
            System.out.print("read error");
            throw new RuntimeException(e);
        }

        return true;
    }


    /**
     * Method to increment iterator.
     *
     * @param it cur iterator
     * @return new value of iterator
     */
    private int getNextIt(int it) {
        if (it + 1 == size) {
            return 0;
        }
        else {
            return it + 1;
        }
    }

    /**
     * Method to decrement iterator.
     *
     * @param it cur iterator
     * @return new value of iterator
     */
    private int getPrevIt(int it) {
        if (it - 1 == -1) {
            return size - 1;
        } else {
            return it - 1;
        }
    }

    /**
     * Method for comparing string and chars in buffer.
     *
     * @param charArr string to compare
     * @return number of char that was wrong or -1, if strings are the same
     */
    public Pair<Character, Integer> checkString(char[] charArr) {
        int iter = it;
        for (int i = size - 1; i >= 0; i--) {
            if (charArr[i] != this.str[iter]) {
                return new Pair<>(this.str[iter], i);
            }
            iter = getPrevIt(iter);
        }

        return new Pair<>(this.str[0], -1);
    }
}
