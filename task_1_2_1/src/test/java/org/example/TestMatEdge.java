package org.example;

import org.junit.jupiter.api.Test;

/**
 * Class for matrix of edges tests.
 */
public class TestMatEdge {

    @Test
    public void testCircle() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.sortCircle();
    }

    @Test
    public void testListSort() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.sort();
    }

    @Test
    public void testListCreating() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.creatingAndDeletingMatEdge();
    }

    @Test
    public void testReading() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.readFromFile();
    }

    @Test
    public void testNeigs() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.neigs();
    }

    @Test
    public void testPrint() {
        TestGraph test = new TestGraph((EdgeMatrix.class));
        test.print();
    }
}
