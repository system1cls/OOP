package org.example;

import java.io.Serializable;

/**
 * Parent class.
 */
public class Element implements Serializable {
    protected String str;

    /**
     * Make string.
     *
     * @return string
     */
    @Override
    public String toString() {
        return this.str;
    }
}
