package org.example;

/**
 * Interface to represent graph.
 */
public interface Graph {

    /**
     * Add new vert.
     * (Important: if there is a deleted vert, this method will NOT return the smallest available number)
     *
     * @return number of new vert
     */
    int addVert();

    /**
     * Add new oriented edge.
     *
     * @param vertNum1 Vert from
     * @param vertNum2 Vert to
     */
    void addEdge(int vertNum1, int vertNum2);

    /**
     * Delete vert.
     *
     * @param vertNum number of vert to delete
     */
    void deleteVert(int vertNum);

    /**
     * Delete edge from VertNum1 to VertNum2.
     *
     * @param vertNum1 Vert from
     * @param vertNum2 Vert to
     */
    void deleteEdge(int vertNum1, int vertNum2);

    /**
     * Get adjective verts.
     *
     * @param vertNum number of vert
     * @return array of numbers of verts, that are adjective to VertNum
     */
    int[] getNeighbors(int vertNum);

    /**
     * Create a NEW graph from description if file.
     *
     * @param fileName file name
     */
    void readFromFile(String fileName);

    /**
     * Get numbers of verts in topological sorted order.
     *
     * @return array of sorted numbers of verts.
     */
    int[] sort();

    /**
     * Method to print graph`s parameters.
     */
    void print();

    /**
     * Delete graph.
     */
    void clear();
}
