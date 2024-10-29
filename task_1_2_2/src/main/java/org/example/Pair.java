package org.example;

/**
 * Class for implementing Pair.
 *
 * @param <K> key
 * @param <V> value
 */
public class Pair<K, V> {
    K key;
    V value;

    /**
     * Constructor.
     *
     * @param key key
     * @param val value
     */
    Pair(K key, V val) {
        this.key = key;
        this.value = val;
    }


    /**
     * Method for comparing.
     *
     * @param obj object to compare
     * @return is equality
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Pair<K, V> newPair = (Pair<K, V>) obj;

        return (this.key.equals(newPair.key)) && (this.value.equals(newPair.value));
    }
}
