package org.example;


import java.util.Iterator;

public class HashTable<K, V> implements Iterable<Pair<K, V>> {
    int size;
    SubArray<K, V>[] array;


    HashTable() {
        size = 10000;
        makeList();
    }

    HashTable(int size) {
        if (size <= 0) {
            this.size = 10000;
        }
        else  {
            this.size = size;
        }
        makeList();
    }

    public void add(K k, V v) {
        int hash = this.getHash(k);
        if (this.array[hash] == null) {
            this.array[hash] = new SubArray<>();
        }
        this.array[hash].addValue(k, v);
    }

    public void delete(K k) {
        int hash = this.getHash(k);
        if (array[hash] == null) {
            return;
        }
        this.array[hash].deleteVal(k);
    }

    public V getVal(K k) {
        int hash = this.getHash(k);
        if (array[hash] == null) {
            return null;
        }
        return array[hash].getVal(k);
    }

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

    public void print() {

        for (Pair<K, V> p : this) {
            System.out.println(p.key + ": " + p.value);
        }
    }


    private void makeList() {
        this.array = new SubArray[size];
    }

    private int getHash(K key) {
        int hash = key.hashCode() % this.size;
        if (hash < 0) {
            hash += this.size;
        }
        return hash;
    }


    @Override
    public Iterator<Pair<K, V>> iterator() {
        return new HashIterator<>(this);
    }
}
