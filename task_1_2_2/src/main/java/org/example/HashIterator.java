package org.example;

import java.util.Iterator;

/**
 * Class for iterator.
 *
 * @param <K> key
 * @param <V> value
 */
public class HashIterator<K, V> implements Iterator<Pair<K, V>> {
    int itArray;
    int itElem;
    Pair<K, V> cur;
    HashTable<K, V> table;

    /**
     * Constructor.
     *
     * @param table hash table
     */
    HashIterator(HashTable<K, V> table) {
        itArray = -1;
        itElem = 1;
        this.table = table;
        for (int i = 0; i < table.size; i++) {
            if (table.array[i] != null && table.array[i].length != 0) {
                itArray = i;
                break;
            }
        }

        if (itArray > 0) {
            cur = table.array[itArray].subArray[0];
        }
        else {
            cur = null;
        }
    }


    /**
     * Check next pair.
     *
     * @return is next exist.
     */
    @Override
    public boolean hasNext() {
        return (cur != null);
    }

    /**
     * Get next Pair.
     *
     * @return next Pair
     */
    @Override
    public Pair<K, V> next() {
        Pair<K, V> temp = cur;
        cur = getNext();
        return temp;
    }

    /**
     * Get next pair for variable.
     *
     * @return next pair for variable
     */
    private Pair<K, V> getNext() {
        if (itElem == table.array[itArray].length) {
            for (int i = itArray + 1; i < table.size; i++) {
                if (table.array[i] != null && table.array[i].length != 0) {
                    itArray = i;
                    itElem = 1;
                    cur = table.array[itArray].subArray[0];
                    return cur;
                }
            }
            return null;
        }
        else {
            cur = table.array[itArray].subArray[itElem];
            itElem++;
            return cur;
        }

    }
}
