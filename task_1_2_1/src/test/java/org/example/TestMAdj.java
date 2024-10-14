package org.example;

import org.junit.jupiter.api.Test;

public class TestMAdj {

    @Test
    public void testListSort() {
        TestGraph test = new TestGraph(AdjMatrix.class);
        test.sort();
    }

    @Test
    public void testListCreating() {
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
