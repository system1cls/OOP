package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class TestGraph {


    Class clazz;

    <T> TestGraph(Class<T> clazz) {
        this.clazz = clazz;
    }


    public void readFromFile() {
        Graph graph = Graphs.createGraphSimple(clazz);
        graph.readFromFile("graphSimple.txt");
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

    public void creatingAndDeletingMatEdge() {
        Graph graph = Graphs.createGraphMiddle2(clazz);
        graph.deleteEdge(1, 9);
        graph.deleteEdge(8, 9);
        graph.addEdge(1, 9);
        graph.addEdge(8, 9);
        graph.deleteVert(9);
        graph.addVert();
    }


    public void sort() {
        Graph graph = Graphs.createSort(clazz);
        int[] rightAnswer = {0, 1, 3, 4, 2};
        assertArrayEquals(graph.sort(), rightAnswer);
    }

    public void sortCircle() {
        Graph graph = Graphs.createCircled(clazz);
        assertThrows(CircleGraphException.class, () -> {graph.sort();});
    }

    public void neigs() {
        Graph graph = Graphs.createGraphHard(clazz);

        int[] rightAnswer1 = {4, 7, 9};
        int[] answer1 = graph.getNeighbors(8);

        Arrays.sort(answer1);

        int[] rightAnswer2 = {0, 4, 5};
        int[] answer2 = graph.getNeighbors(3);

        Arrays.sort(answer2);

        assertArrayEquals(answer1, rightAnswer1);
        assertArrayEquals(answer2, rightAnswer2);
    }


    public void print() {
        Graph graph = Graphs.createGraphMiddle1(clazz);
        graph.print();
    }
}