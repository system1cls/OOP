package org.example;

/**
 * Class for implementing node of hashTable.
 *
 * @param <K> key
 * @param <V> value
 */
public class SubArray<K, V> {
    int length;
    Pair<K, V>[] subArray;


    /**
     * Constructor.
     */
    SubArray() {
        length = 0;
        this.subArray = new Pair[100];
    }

    /**
     * Add new pair in node.
     *
     * @param k key
     * @param v value
     * @return pointer to new Pair
     */
    public Pair<K, V> addValue(K k, V v) {
        if (this.getVal(k) == null) {
            subArray[length++] = new Pair<>(k, v);
            return subArray[length - 1];
        }
        return null;
    }


    /**
     * Add or update pair in node.
     *
     * @param k key
     * @param v value
     */
    public boolean updateValue(K k, V v) {
        for (int i = 0; i < length; i++) {
            if (k.equals(subArray[i].key)) {
                if (v.equals(subArray[i].value)) {
                    return false;
                }
                subArray[i].value = v;
                return true;
            }
        }
        subArray[length++] = new Pair<>(k, v);
        return true;
    }

    /**
     * Method for node`s comparing.
     *
     * @param obj object to compare
     * @return is equality
     */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        SubArray<K, V> newArr = (SubArray<K, V>) obj;

        if (newArr.length != this.length) {
            return false;
        }

        boolean isFind;
        for (int i = 0; i < this.length; i++) {
            isFind = false;
            for (int j = 0; j < this.length; j++) {
                if (this.subArray[i].equals(newArr.subArray[j])) {
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get Value by key.
     *
     * @param k key
     * @return value
     */
    public V getVal(K k) {
        for (int i = 0; i < length; i++) {
            if (k.equals(subArray[i].key)) {
                return subArray[i].value;
            }
        }
        return null;
    }

    /**
     * Method for deleting pair by key.
     *
     * @param k key
     */
    public boolean deleteVal(K k) {
        for (int i = 0; i < length; i++) {
            if (subArray[i].key.equals(k)) {
                if (i == length - 1) {
                    subArray[i] = null;
                } else {
                    subArray[i] = subArray[length - 1];
                }
                length--;
                return true;
            }
        }
        return false;
    }
}
