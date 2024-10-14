package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class for implementing graph by a Matrix of Edges.
 */
public class EdgeMatrix implements Graph {

    private int curSizeVert = 0;
    private int curSizeEdge = 0;
    private int maxSizeVert = 100;
    private int maxSizeEdge = 100;
    private int[][] matrix;

    /**
     * Add new vert.
     * <p>
     * (Important: if there is a deleted vert, this method will NOT return the smallest available number)
     *
     * @return number of new vert
     */
    @Override
    public int addVert() {
        if (curSizeVert == 0) {
            matrix = new int[100][100];
        }

        if (curSizeVert == maxSizeVert) {
            maxSizeVert *= 2;
            matrix = Arrays.copyOf(matrix, maxSizeVert);
        }

        curSizeVert++;
        return curSizeVert - 1;
    }

    /**
     * Add new oriented edge.
     *
     * @param VertNum1 Vert from
     * @param VertNum2 Vert to
     */
    @Override
    public void addEdge(int VertNum1, int VertNum2) {
        while (VertNum1 > maxSizeVert || VertNum2 > maxSizeVert) {
            maxSizeVert *= 2;
            matrix = Arrays.copyOf(matrix, maxSizeVert);

            if (curSizeEdge == maxSizeEdge) {
                for (int i = 0; i < maxSizeVert; i++) {
                    maxSizeEdge *= 2;
                    matrix[i] = Arrays.copyOf(matrix[i], maxSizeEdge);
                }
            }
        }

        matrix[VertNum1][curSizeEdge] = 1;
        matrix[VertNum2][curSizeEdge++] = -1;
    }

    /**
     * Delete vert.
     *
     * @param VertNum number of vert to delete
     */
    @Override
    public void deleteVert(int VertNum) {
        for (int edge = 0; edge < curSizeEdge; edge++) {
            if (matrix[VertNum][edge] != 0) {
                for (int vert = 0; vert < curSizeVert; vert++) {
                    matrix[vert][edge] = 0;
                }
            }
        }
    }

    /**
     * Delete edge from VertNum1 to VertNum2.
     *
     * @param VertNum1 Vert from
     * @param VertNum2 Vert to
     */
    @Override
    public void deleteEdge(int VertNum1, int VertNum2) {
        for (int edge = 0; edge < curSizeEdge; edge++) {
            if (matrix[VertNum1][edge] != 0 && matrix[VertNum2][edge] != 0) {
                matrix[VertNum1][edge] = 0;
                matrix[VertNum2][edge] = 0;
                break;
            }
        }
    }

    /**
     * Get adjective verts.
     *
     * @param VertNum number of vert
     * @return array of numbers of verts, that are adjective to VertNum
     */
    @Override
    public int[] getNeighbors(int VertNum) {
        int[] neighs = new int[curSizeVert];
        int it = 0;
        for (int edge = 0; edge < curSizeEdge; edge++) {
            if (matrix[VertNum][edge] != 0) {
                for (int vert = 0; vert < curSizeVert; vert++) {
                    if (matrix[vert][edge] != 0 && vert != VertNum) {
                        neighs[it++] = vert;
                        break;
                    }
                }
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
        boolean[] isVisited = new boolean[curSizeVert];
        int[] stack = new int[curSizeVert];
        int stackIt = 0;
        for (int i = 0; i < curSizeVert; i++) {
            if (!isVisited[i]) {
                stackIt = dfs(isVisited, stack, stackIt, i);
            }
        }

        for (int i = 0; i < curSizeVert / 2; i++) {
            int temp = stack[i];
            stack[i] = stack[curSizeVert - 1 - i];
            stack[curSizeVert - 1 - i] = temp;
        }

        return stack;
    }

    /**
     * DFS.
     *
     * @param isVisited array, representing was vert visited.
     * @param stack     array of sorted verts
     * @param stackIt   stack size/iterator for the next vert
     * @param it        number of vert to check.
     * @return new stack size
     */
    private int dfs(boolean[] isVisited, int[] stack, int stackIt, int it) {
        isVisited[it] = true;
        for (int edge = 0; edge < curSizeEdge; edge++) {
            if (matrix[it][edge] == 1) {
                for (int vert = 0; vert < curSizeVert; vert++) {
                    if (matrix[vert][edge] == -1) {
                        if (isVisited[vert]) {
                            break;
                        }
                        stackIt = dfs(isVisited, stack, stackIt, vert);
                        break;
                    }
                }

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
        for (int i = 0; i < curSizeEdge; i++) {
            System.out.print("\"");
            System.out.print(i);
            System.out.print("\" ");
        }
        System.out.print("\n");
        for (int i = 0; i < curSizeVert; i++) {
            System.out.print("\"");
            System.out.print(i);
            System.out.print("\" ");
            for (int j = 0; j < curSizeEdge; j++) {
                System.out.print(" ");
                System.out.print(matrix[i][j]);
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
        if (o.getClass() != EdgeMatrix.class) {
            return false;
        }

        EdgeMatrix secMatrix = (EdgeMatrix) o;

        if (secMatrix.curSizeVert != this.curSizeVert) {
            return false;
        }

        if (secMatrix.curSizeEdge != this.curSizeEdge) {
            return false;
        }

        for (int vertOut = 0; vertOut < curSizeVert; vertOut++) {
            for (int edge = 0; edge < curSizeEdge; edge++) {
                if (secMatrix.matrix[vertOut][edge] != matrix[vertOut][edge]) {
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
        curSizeVert = 0;
        curSizeEdge = 0;
        maxSizeEdge = 100;
        maxSizeVert = 100;
    }
}
