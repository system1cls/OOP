package org.example;

import java.util.ArrayList;

/**
 * Class to represent verts in Lists of Adjacency.
 */
public class Vert {
    /**
     * Vert constructor.
     *
     * @param number number of this vert.
     */
    Vert(int number) {
        this.number = number;
        listIn = new ArrayList<>();
        listOut = new ArrayList<>();
    }

    /**
     * Add the output edge.
     *
     * @param vertTo number of vert of input the edge.
     */
    void addEdgeOut(int vertTo) {
        listOut.add(vertTo);
    }

    /**
     * Add the input edge.
     *
     * @param vertOut number of vert of output the edge.
     */
    void addEdgeIn(int vertOut) {
        listIn.add(vertOut);
    }

    int number;
    ArrayList<Integer> listIn;
    ArrayList<Integer> listOut;
}
