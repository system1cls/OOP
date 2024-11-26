package org.example;

import java.util.Arrays;


/**
 * Code class.
 */
public class Code extends Element {

    /**
     * Constructor by builder.
     *
     * @param builder builder.
     */
    Code(CodeBuilder builder) {
        str = "```\n";
        for (int i = 0; i < builder.size; i++) {
            str += builder.code[i] + "\n";
        }

        str += "```\n";
    }

    /**
     * Make string.
     *
     * @return string
     */
    @Override
    public String toString() {
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

        Code newCode = (Code) obj;

        return this.str.equals(newCode.str);
    }

    /**
     * Builder class.
     */
    public static class CodeBuilder {
        String []code;
        int size;
        int maxSize;

        /**
         * Builder constructor.
         */
        CodeBuilder() {
            maxSize = 10;
            code = new String[maxSize];
        }

        /**
         * Add string represented by String class.
         *
         * @param str string to add
         * @return builder
         */
        public  CodeBuilder addString(String str) {
            if (size == maxSize) {
                maxSize *= 2;
                code = Arrays.copyOf(code, maxSize);
            }

            code[size++] = str;

            return this;
        }

        /**
         * Add string represented by Element.
         *
         * @param el element to add
         * @return builder
         */
        public  CodeBuilder addString(Element el) {
            if (size == maxSize) {
                maxSize *= 2;
                code = Arrays.copyOf(code, maxSize);
            }

            code[size++] = el.str;

            return this;
        }

        /**
         * Build builder.
         *
         * @return built Code class
         */
        public Code build() {
            return new Code(this);
        }
    }
}
