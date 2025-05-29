package org.example;

import org.junit.jupiter.api.Test;

/**
 * Class for Lists tests.
 */
public class TestList {

    @Test
    public void testCircle() {
        TestGraph test = new TestGraph(ListAdj.class);
        test.sortCircle();
    }

    @Test
    public void testListSort() {
        TestGraph test = new TestGraph(ListAdj.class);
        test.sort();
    }

    @Test
    public void testListCreating() {
        TestGraph test = new TestGraph(ListAdj.class);
        test.creatingAndDeleting();
    }

    @Test
    public void testReading() {
        TestGraph test = new TestGraph(ListAdj.class);
        test.readFromFile();
    }

    @Test
    public void testNeigs() {
        TestGraph test = new TestGraph(ListAdj.class);
        test.neigs();
    }

    @Test
    public void testPrint() {
        TestGraph test = new TestGraph((ListAdj.class));
        test.print();
    }
}
