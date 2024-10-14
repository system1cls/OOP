package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class for implementing graph by lists of Adjacency.
 */
public class ListAdj implements Graph {
    private int cntVerts = 0;
    private int maxVerts = 100;
    private Vert[] verts;
    private boolean[] isCreated;

    /**
     * Add new vert.
     * <p>
     * (Important: if there is a deleted vert, this method will NOT return the smallest available number)
     *
     * @return number of new vert
     */
    @Override
    public int addVert() {
        if (cntVerts == 0) {
            verts = new Vert[100];
            isCreated = new boolean[100];
        }

        if (cntVerts == maxVerts) {
            maxVerts *= 2;
            verts = Arrays.copyOf(verts, maxVerts);
            isCreated = Arrays.copyOf(isCreated, maxVerts);
        }


        if (!isCreated[cntVerts]) {
            verts[cntVerts] = new Vert(cntVerts);
        }

        isCreated[cntVerts++] = true;
        return cntVerts - 1;
    }

    /**
     * Add new oriented edge.
     *
     * @param VertNum1 Vert from
     * @param VertNum2 Vert to
     */
    @Override
    public void addEdge(int VertNum1, int VertNum2) {
        if (VertNum2 >= cntVerts) {
            while (VertNum2 > maxVerts) {
                maxVerts *= 2;
                verts = Arrays.copyOf(verts, maxVerts);
                isCreated = Arrays.copyOf(isCreated, maxVerts);
            }

            verts[VertNum2] = new Vert(VertNum2);
        }
        verts[VertNum1].addEdgeOut(VertNum2);
        verts[VertNum2].addEdgeIn(VertNum1);
    }

    /**
     * Delete vert.
     *
     * @param VertNum number of vert to delete
     */
    @Override
    public void deleteVert(int VertNum) {
        Vert vertToDelete = verts[VertNum];
        for (int vertIn : vertToDelete.listOut) {
            verts[vertIn].listIn.remove(verts[vertIn].listIn.indexOf(vertToDelete.number));
        }
        vertToDelete.listOut.clear();

        for (int vertOut : vertToDelete.listIn) {
            verts[vertOut].listOut.remove(verts[vertOut].listOut.indexOf(vertToDelete.number));
        }
        vertToDelete.listIn.clear();
    }


    /**
     * Delete edge from VertNum1 to VertNum2.
     *
     * @param VertNum1 Vert from
     * @param VertNum2 Vert to
     */
    @Override
    public void deleteEdge(int VertNum1, int VertNum2) {
        verts[VertNum1].listOut.remove(verts[VertNum1].listOut.indexOf(VertNum2));
        verts[VertNum2].listIn.remove(verts[VertNum2].listIn.indexOf(VertNum1));
    }

    /**
     * Get adjective verts.
     *
     * @param VertNum number of vert
     * @return array of numbers of verts, that are adjective to VertNum
     */
    @Override
    public int[] getNeighbors(int VertNum) {
        int[] neighs = new int[cntVerts];
        int it = 0;
        boolean[] isAdded = new boolean[cntVerts];
        for (int vert : verts[VertNum].listIn) {
            if (!isAdded[vert]) {
                neighs[it++] = vert;
                isAdded[vert] = true;
            }
        }
        for (int vert : verts[VertNum].listOut) {
            if (!isAdded[vert]) {
                neighs[it++] = vert;
                isAdded[vert] = true;
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
        boolean[] isVisited = new boolean[cntVerts];
        int[] stack = new int[cntVerts];
        int stackIt = 0;
        for (int vert = 0; vert < cntVerts; vert++) {
            if (!isVisited[vert]) {
                stackIt = dfs(isVisited, stack, stackIt, vert);
            }
        }

        for (int vert = 0; vert < cntVerts / 2; vert++) {
            int temp = stack[vert];
            stack[vert] = stack[cntVerts - 1 - vert];
            stack[cntVerts - 1 - vert] = temp;
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
        for (int vert : verts[it].listOut) {
            if (!isVisited[vert]) {
                stackIt = dfs(isVisited, stack, stackIt, vert);
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
        for (int vert = 0; vert < cntVerts; vert++) {
            System.out.print(vert);
            System.out.print(": ");
            for (int vertTo : verts[vert].listOut) {
                System.out.print(vertTo);
                System.out.print(" ");
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
        if (o.getClass() != ListAdj.class) {
            return false;
        }

        ListAdj list2 = (ListAdj) o;

        if (this.cntVerts != list2.cntVerts) {
            return false;
        }

        for (int vert = 0; vert < cntVerts; vert++) {
            Vert v1 = verts[vert];
            Vert v2 = list2.verts[vert];

            for (int vertTo = 0; vertTo < v1.listOut.size(); vertTo++) {
                if (v1.listOut.get(vertTo) != v2.listOut.get(vertTo)) {
                    return false;
                }
            }

            for (int vertOut = 0; vertOut < v1.listIn.size(); vertOut++) {
                if (v1.listIn.get(vertOut) != v2.listIn.get(vertOut)) {
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
        verts = null;
        isCreated = null;
        cntVerts = 0;
        maxVerts = 100;
    }

}
