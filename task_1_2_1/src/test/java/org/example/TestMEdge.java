package org.example;

import org.junit.jupiter.api.Test;

public class TestMEdge {

    @Test
    public void testListSort() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.sort();
    }

    @Test
    public void testListCreating() {
        TestGraph test = new TestGraph(EdgeMatrix.class);
        test.creatingAndDeletingMEdge();
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
