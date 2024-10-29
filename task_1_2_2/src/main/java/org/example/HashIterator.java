package org.example;

import java.util.Iterator;

public class HashIterator<K, V> implements Iterator<Pair<K, V>> {
    int itArray;
    int itElem;
    Pair<K, V> cur;
    HashTable<K, V> table;

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



    @Override
    public boolean hasNext() {
        return (cur != null);
    }

    @Override
    public Pair<K, V> next() {
        Pair<K, V> temp = cur;
        cur = getNext();
        return temp;
    }

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
