package org.example;

public class DontExitstException extends Exception {
    public DontExitstException(String str) {
        super(str);
    }

    static void deleteVertExc(int number, int max) throws DontExitstException {
        if (number >= max) {
            throw new DontExitstException("Vert doesn`t exist");
        }
    }
}