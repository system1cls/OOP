package org.example;

import java.util.Arrays;

/**
 * List class.
 */
public class List extends Element {
    int size;
    String[] strings;

    /**
     * Simple constructor.
     */
    public List() {
        str = "";
    }

    /**
     * Constructor by builder.
     *
     * @param builder builder
     */
    public List(ListBuilder builder) {
        strings = Arrays.copyOf(builder.str, builder.size);
        size = builder.size;
        str = this.toString();
    }

    /**
     * Make string.
     *
     * @return string
     */
    @Override
    public String toString() {
        str = "";
        for (int i = 0; i < size; i++) {
            this.str += strings[i] + "\n";
        }
        return this.str;
    }

    /**
     * Compare objects.
     *
     * @param obj object to compare
     * @return is equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        List list = (List) obj;

        if (this.size != list.size) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (!this.strings[i].equals(list.strings[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Builder class.
     */
    public static class ListBuilder {
        String []str;
        int maxSize = 10;
        int size = 0;
        char point;
        boolean isNum = false;

        /**
         * Constructor of builder.
         *
         * @param point char to be point of list
         */
        ListBuilder(char point) {
            str = new String[10];
            this.point = point;
            if (Character.isDigit(point)) {
                isNum = true;
            }
        }

        /**
         * Add Node by Element.
         *
         * @param el node of list
         * @return builder
         */
        public ListBuilder addNode(Element el) {
            if (size == maxSize) {
                maxSize *= 2;
                str = Arrays.copyOf(str, maxSize);
            }
            str[size++] = point + " " + el.str;
            return this;
        }

        /**
         * Add node by String.
         *
         * @param el node of List
         * @return builder
         */
        public ListBuilder addNode(String el) {
            if (size == maxSize) {
                maxSize *= 2;
                str = Arrays.copyOf(str, maxSize);
            }
            str[size++] = point + " " + el;
            return this;
        }


        /**
         * Build builder.
         *
         * @return built List
         */
        public List build() {
            this.str[size] += "\n\n";
            return new List(this);
        }
    }
}
