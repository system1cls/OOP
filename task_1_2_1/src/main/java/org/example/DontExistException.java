package org.example;

public class DontExistException extends Exception {
    public DontExistException(String str) {
        super(str);
    }

    static void deleteVertExc(int number, int max) throws DontExistException {
        if (number >= max) {
            throw new DontExistException("Vert doesn`t exist");
        }
    }
}