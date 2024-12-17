package org.example;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Main class.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {

        Element el = new Text.TextBuilder()
                .setText("abc")
                .build();

        try (FileOutputStream outStream = new FileOutputStream("testSer")) {
            ObjectOutputStream obj = new ObjectOutputStream(outStream);
            obj.writeObject(el);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}