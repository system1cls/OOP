package org.example;

/**
 * Class for pair.
 *
 * @param <K> key
 * @param <V>value
 */
public class Pair <K, V> {
    K key;
    V Value;

    /**
     * Constructor.
     *
     * @param key key
     * @param val value
     */
    Pair(K key, V val) {
        this.key = key;
        this.Value = val;
    }
}
