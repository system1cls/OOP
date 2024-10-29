package org.example;

public class Pair<K, V> {
    K key;
    V value;

    Pair(K key, V val) {
        this.key = key;
        this.value = val;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Pair<K, V> newPair = (Pair<K, V>) obj;

        return (this.key.equals(newPair.key)) && (this.value.equals(newPair.value));
    }
}
