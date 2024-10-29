package org.example;


import java.util.Iterator;

/**
 * Class for implementing hash table.
 *
 * @param <K> Key
 * @param <V> Value
 */
public class HashTable<K, V> implements Iterable<Pair<K, V>> {
    int size;
    SubArray<K, V>[] array;


    /**
     * Common Constructor.
     */
    HashTable() {
        size = 10000;
        makeList();
    }

    /**
     * Constructor with exact count of nodes.
     *
     * @param size count of nodes.
     */
    HashTable(int size) {
        if (size <= 0) {
            this.size = 10000;
        }
        else  {
            this.size = size;
        }
        makeList();
    }

    /**
     * Add new pair.
     *
     * @param k key
     * @param v value
     */
    public void add(K k, V v) {
        int hash = this.getHash(k);
        if (this.array[hash] == null) {
            this.array[hash] = new SubArray<>();
        }
        this.array[hash].addValue(k, v);
    }

    /**
     * Delete pair by key.
     *
     * @param k key
     */
    public void delete(K k) {
        int hash = this.getHash(k);
        if (array[hash] == null) {
            return;
        }
        this.array[hash].deleteVal(k);
    }

    /**
     * Get value by key.
     *
     * @param k key
     * @return value
     */
    public V getVal(K k) {
        int hash = this.getHash(k);
        if (array[hash] == null) {
            return null;
        }
        return array[hash].getVal(k);
    }

    /**
     * Check if pair exist.
     *
     * @param k key
     * @return is exist
     */
    public boolean checkVal(K k) {
        return this.getVal(k) != null;
    }

    public void update(K k, V v) {
        int hash = this.getHash(k);
        if (array[hash] == null) {
            array[hash] = new SubArray<>();
        }
        this.array[hash].updateValue(k, v);
    }

    /**
     * Compare hash tables.
     *
     * @param obj object to compare
     * @return is equality
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        HashTable<K, V> newTable = (HashTable<K, V>) obj;

        if (newTable.size != this.size) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != null && newTable.array[i] != null) {
                if (!this.array[i].equals(newTable.array[i])) {
                    return false;
                }
            }
            else {
                if (this.array[i] != null || newTable.array[i] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Print pairs.
     */
    public void print() {
        for (Pair<K, V> p : this) {
            System.out.println(p.key + ": " + p.value);
        }
    }


    /**
     * Create array of nodes.
     */
    private void makeList() {
        this.array = new SubArray[size];
    }

    /**
     * Get hash of key.
     *
     * @param key key
     * @return hash
     */
    private int getHash(K key) {
        int hash = key.hashCode() % this.size;
        if (hash < 0) {
            hash += this.size;
        }
        return hash;
    }


    /**
     * Create Iterator.
     *
     * @return iterator of hash table
     */
    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new HashIterator<>(this);
    }
}
