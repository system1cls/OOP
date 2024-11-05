package org.example;

import static org.example.DontExitstException.deleteVertExc;

import java.io.*;
import java.util.Arrays;

/**
 * Class for implementing graph by Matrix of Adjacency.
 */
public class AdjMatrix implements Graph {

    private int curSize = 0;
    private int maxSize = 100;
    private int[][] matrix;

    /**
     * Add new vert.
     *
     * (Important: if there is a deleted vert,
     * this method will NOT return the smallest available number)
     *
     * @return number of new vert
     */
    @Override
    public int addVert() {
        if (curSize == 0) {
            matrix = new int[100][100];
        }

        if (curSize == maxSize) {
            maxSize *= 2;
            matrix = Arrays.copyOf(matrix, maxSize);
            for (int i = 0; i < maxSize; i++) {
                matrix[i] = Arrays.copyOf(matrix[i], maxSize);
            }
        }

        curSize++;
        return curSize - 1;
    }

    /**
     * Add new oriented edge.
     *
     * @param vertNum1 Vert from
     * @param vertNum2 Vert to
     */
    @Override
    public void addEdge(int vertNum1, int vertNum2) {
        while (vertNum1 > maxSize || vertNum2 > maxSize) {
            maxSize *= 2;
            matrix = Arrays.copyOf(matrix, maxSize);
            for (int i = 0; i < maxSize; i++) {
                maxSize *= 2;
                matrix[i] = Arrays.copyOf(matrix[i], maxSize);
            }
        }
        matrix[vertNum1][vertNum2] = 1;
    }

    /**
     * Delete vert.
     *
     * @param vertNum number of vert to delete
     */
    @Override
    public void deleteVert(int vertNum) {

        try {
            deleteVertExc(vertNum, curSize);
            for (int vertOut = 0; vertOut < curSize; vertOut++) {
                for (int vertTo = 0; vertTo < curSize; vertTo++) {
                    if (vertOut == vertNum || vertTo == vertNum) {
                        matrix[vertOut][vertTo] = 0;
                    }
                }
            }
        } catch (DontExitstException ex) {
            System.out.print("Invalid number: " + ex.getMessage());
        }
    }

    /**
     * Delete edge from VertNum1 to VertNum2.
     *
     * @param vertNum1 Vert from
     * @param vertNum2 Vert to
     */
    @Override
    public void deleteEdge(int vertNum1, int vertNum2) {
        try {
            deleteVertExc(vertNum1, curSize);
        } catch (DontExitstException ex) {
            System.out.print("No Vert " + vertNum1 + ": " + ex.getMessage());
        }

        try {
            deleteVertExc(vertNum2, curSize);
        } catch (DontExitstException ex) {
            System.out.print("No Vert " + vertNum2 + ": " + ex.getMessage());
        }

        matrix[vertNum1][vertNum2] = 0;
    }

    /**
     * Get adjective verts.
     *
     * @param vertNum number of vert
     * @return array of numbers of verts, that are adjective to vertNum
     */
    @Override
    public int[] getNeighbors(int vertNum) {
        try {
            deleteVertExc(vertNum, curSize);
        }
        catch (DontExitstException ex) {
            System.out.print("Invalid input: " + ex.getMessage());
        }

        int[] neighs = new int[curSize];
        int it = 0;
        for (int vertNeigh = 0; vertNeigh < curSize; vertNeigh++) {
            if (matrix[vertNum][vertNeigh] == 1) {
                neighs[it++] = vertNeigh;
            }
            if (matrix[vertNeigh][vertNum] == 1) {
                neighs[it++] = vertNeigh;
            }
        }
        neighs = Arrays.copyOf(neighs, it);
        return neighs;
    }

    /**
     * Create a NEW graph from description if file.
     *
     * @param fileName file name
     */
    @Override
    public void readFromFile(String fileName) {
        this.clear();
        int number;
        try (FileReader fd = new FileReader(fileName)) {

            try (BufferedReader br = new BufferedReader(fd)) {
                String line = br.readLine();


                while (line != null) {
                    int vertNumber = this.addVert();
                    number = 0;
                    for (char ch : line.toCharArray()) {
                        if (Character.isDigit(ch)) {
                            number *= 10;
                            number += ch - '0';
                        } else {

                            this.addEdge(vertNumber, number);
                            number = 0;

                        }
                    }
                    if (number > 0) {
                        this.addEdge(vertNumber, number);
                    }
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get numbers of verts in topological sorted order.
     *
     * @return array of sorted numbers of verts.
     */
    @Override
    public int[] sort() {
        boolean[] isVisited = new boolean[curSize];
        int[] stack = new int[curSize];
        int stackIt = 0;
        for (int vertToVisit = 0; vertToVisit < curSize; vertToVisit++) {
            if (!isVisited[vertToVisit]) {
                stackIt = dfs(isVisited, stack, stackIt, vertToVisit);
            }
        }

        for (int i = 0; i < curSize / 2; i++) {
            int temp = stack[i];
            stack[i] = stack[curSize - 1 - i];
            stack[curSize - 1 - i] = temp;
        }

        return stack;
    }

    /**
     * DFS.
     *
     * @param isVisited array, representing was vert visited.
     * @param stack array of sorted verts
     * @param stackIt stack size/iterator for the next vert
     * @param it number of vert to check.
     * @return new stack size
     */
    private int dfs(boolean[] isVisited, int[] stack, int stackIt, int it) {
        isVisited[it] = true;
        for (int vertNeigh = 0; vertNeigh < curSize; vertNeigh++) {
            if (matrix[it][vertNeigh] == 1 && !isVisited[vertNeigh]) {
                stackIt = dfs(isVisited, stack, stackIt, vertNeigh);
            }
        }
        stack[stackIt++] = it;
        return stackIt;
    }

    /**
     * Method to print graph`s parameters.
     */
    @Override
    public void print() {

        System.out.print("   ");
        for (int vert = 0; vert < curSize; vert++) {
            System.out.print("\"");
            System.out.print(vert);
            System.out.print("\" ");
        }
        System.out.print("\n");
        for (int vert1 = 0; vert1 < curSize; vert1++) {
            System.out.print("\"");
            System.out.print(vert1);
            System.out.print("\" ");
            for (int vert2 = 0; vert2 < curSize; vert2++) {
                System.out.print(" ");
                System.out.print(matrix[vert1][vert2]);
                System.out.print("  ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Comparison.
     *
     * @param o object to compare
     * @return are objects similar
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != AdjMatrix.class) {
            return false;
        }

        AdjMatrix secMatrix = (AdjMatrix) o;
        if (secMatrix.curSize != this.curSize) {
            return false;
        }

        for (int vertOut = 0; vertOut < curSize; vertOut++) {
            for (int vertIn = 0; vertIn < curSize; vertIn++) {
                if (secMatrix.matrix[vertOut][vertIn] != matrix[vertOut][vertIn]) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Delete graph.
     */
    public void clear() {
        matrix = null;
        curSize = 0;
        maxSize = 100;
    }
}
