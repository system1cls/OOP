package org.example;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestGraph {


    Class clazz;

    <T> TestGraph(Class<T> clazz) {
        this.clazz = clazz;
    }


    public void readFromFile() {
        Graph graph = Graphs.createGraphSimple(clazz);
        graph.readFromFile("src\\test\\resources\\graphSimple.txt");
        assertEquals(graph, Graphs.createGraphSimple(clazz));
    }


    public void creatingAndDeleting() {
        Graph graph = Graphs.createGraphMiddle2(clazz);
        graph.deleteEdge(1, 9);
        graph.deleteEdge(8, 9);
        graph.addEdge(1, 9);
        graph.addEdge(8, 9);
        assertEquals(graph, Graphs.createGraphMiddle2(clazz));
        graph.deleteVert(9);
        graph.addVert();
    }

    public void creatingAndDeletingMEdge() {
        Graph graph = Graphs.createGraphMiddle2(clazz);
        graph.deleteEdge(1, 9);
        graph.deleteEdge(8, 9);
        graph.addEdge(1, 9);
        graph.addEdge(8, 9);
        graph.deleteVert(9);
        graph.addVert();
    }


    public void sort() {
        Graph graph = Graphs.createGraphMiddle2(clazz);
        int[] rightAnswer = {6, 8, 0, 2, 4, 1, 9, 3, 7, 5};
        assertArrayEquals(graph.sort(), rightAnswer);
    }

    public void neigs() {
        Graph graph = Graphs.createGraphHard(clazz);
        int[] rightAnswer1 = {4, 7, 9};
        int[] rightAnswer2 = {0, 4, 5};
        int[] answer1 = graph.getNeighbors(8);
        int[] answer2 = graph.getNeighbors(3);
        Arrays.sort(answer1);
        Arrays.sort(answer2);
        assertArrayEquals(answer1, rightAnswer1);
        assertArrayEquals(answer2, rightAnswer2);
    }


    public void print() {
        Graph graph = Graphs.createGraphMiddle1(clazz);
        graph.print();
    }
}