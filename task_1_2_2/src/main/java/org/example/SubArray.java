package org.example;

/**
 * Class for implementing node of hashTable.
 *
 * @param <K> key
 * @param <V> value
 */
public class SubArray<K, V> {
    int length;
    int maxLength;
    Pair<K, V>[] subArray;

    /**
     * Constructor.
     */
    SubArray() {
        length = 0;
        maxLength = 100;
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
        this.checkAbility();
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
    public void updateValue(K k, V v) {
        for (int i = 0; i < length; i++) {
            if (k.equals(subArray[i].key)) {
                subArray[i].value = v;
                return;
            }
        }
        checkAbility();
        subArray[length++] = new Pair<>(k, v);
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
    public void deleteVal(K k) {
        for (int i = 0; i < length; i++) {
            if (subArray[i].key.equals(k)) {
                if (i == length - 1) {
                    subArray[i] = null;
                } else {
                    subArray[i] = subArray[length - 1];
                }
                length--;
                return;
            }
        }
    }

    /**
     * Method to check if adding available.
     */
    private void checkAbility() {
        if (length == maxLength) {
            maxLength = maxLength * 100;
            Pair<K, V>[] newArray = new Pair[maxLength];
            if (length >= 0) {
                System.arraycopy(this.subArray, 0, newArray, 0, length);
            }
            this.subArray = newArray;
        }
    }
}
