package org.example;

import java.lang.reflect.InvocationTargetException;

/**
 * Class for creating exact graphs.
 */
public class Graphs {

    /**
     * Create simple graph.
     *
     * @param clazz class of implementation graph
     * @param <T> class for creating
     * @return graph
     */
    public static <T> Graph createGraphSimple(Class<T> clazz) {
        try {
            Object plainGraph = Class.forName(clazz.getName())
                    .getConstructor().newInstance();
            Graph graph = (Graph) plainGraph;
            for (int i = 0; i < 6; i++) {
                graph.addVert();
            }
            graph.addEdge(2, 3);
            graph.addEdge(3, 1);
            graph.addEdge(4, 0);
            graph.addEdge(4, 1);
            graph.addEdge(5, 0);
            graph.addEdge(5, 2);
            return graph;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Create middle1 graph.
     *
     * @param clazz class of implementation graph
     * @param <T> class for creating
     * @return graph
     */
    public static <T> Graph createGraphMiddle1(Class<T> clazz) {
        try {
            Object plainGraph = Class.forName(clazz.getName()).getConstructor().newInstance();
            Graph graph = (Graph) plainGraph;
            for (int i = 0; i < 6; i++) {
                graph.addVert();
            }
            graph.addEdge(0, 1);
            graph.addEdge(1, 2);
            graph.addEdge(2, 0);
            graph.addEdge(0, 3);
            graph.addEdge(2, 3);
            graph.addEdge(3, 4);
            graph.addEdge(4, 2);
            graph.addEdge(4, 5);
            graph.addEdge(5, 2);
            return graph;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Create middle2 graph.
     *
     * @param clazz class of implementation graph
     * @param <T> class for creating
     * @return graph
     */
    public static <T> Graph createGraphMiddle2(Class<T> clazz) {
        try {
            Object plainGraph = Class.forName(clazz.getName()).getConstructor().newInstance();
            Graph graph = (Graph) plainGraph;
            for (int i = 0; i < 10; i++) {
                graph.addVert();
            }
            graph.addEdge(0, 1);
            graph.addEdge(0, 2);
            graph.addEdge(1, 3);
            graph.addEdge(1, 9);
            graph.addEdge(2, 4);
            graph.addEdge(3, 5);
            graph.addEdge(3, 7);
            graph.addEdge(4, 7);
            graph.addEdge(5, 3);
            graph.addEdge(6, 4);
            graph.addEdge(6, 8);
            graph.addEdge(8, 3);
            graph.addEdge(8, 9);
            return graph;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Create hard graph.
     *
     * @param clazz class of implementation graph
     * @param <T> class for creating
     * @return graph
     */
    public static <T> Graph createGraphHard(Class<T> clazz) {
        try {
            Object plainGraph = Class.forName(clazz.getName()).getConstructor().newInstance();
            Graph graph = (Graph) plainGraph;
            for (int i = 0; i < 10; i++) {
                graph.addVert();
            }
            graph.addEdge(0, 1);
            graph.addEdge(0, 2);
            graph.addEdge(0, 3);
            graph.addEdge(1, 4);
            graph.addEdge(2, 4);
            graph.addEdge(3, 4);
            graph.addEdge(3, 5);
            graph.addEdge(4, 5);
            graph.addEdge(4, 6);
            graph.addEdge(4, 8);
            graph.addEdge(5, 6);
            graph.addEdge(5, 7);
            graph.addEdge(6, 9);
            graph.addEdge(7, 8);
            graph.addEdge(7, 9);
            graph.addEdge(8, 9);
            return graph;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }


}
