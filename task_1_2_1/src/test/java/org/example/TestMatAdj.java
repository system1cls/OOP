package org.example;

import org.junit.jupiter.api.Test;

/**
 * Class for matrix of adjecty tests.
 */
public class TestMatAdj {

    @Test
    public void testSort() {
        TestGraph test = new TestGraph(AdjMatrix.class);
        test.sort();
    }

    @Test
    public void testCreating() {
        TestGraph test = new TestGraph(AdjMatrix.class);
        test.creatingAndDeleting();
    }

    @Test
    public void testReading() {
        TestGraph test = new TestGraph(AdjMatrix.class);
        test.readFromFile();
    }

    @Test
    public void testNeigs() {
        TestGraph test = new TestGraph(AdjMatrix.class);
        test.neigs();
    }

    @Test
    public void testPrint() {
        TestGraph test = new TestGraph((AdjMatrix.class));
        test.print();
    }
}
