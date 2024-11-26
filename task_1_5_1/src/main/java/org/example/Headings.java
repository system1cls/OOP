package org.example;

/**
 * Headings class.
 */
public class Headings extends Element {

    /**
     * Simple constructor.
     */
    Headings() {
        this.str = "";
    }

    /**
     * Constructor by builder.
     *
     * @param builder builder.
     */
    Headings(HeadingsBuilder builder) {
        str = builder.str;
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

        Headings head = (Headings) obj;

        return head.str.equals(this.str);
    }

    /**
     * Builder class.
     */
    public static class HeadingsBuilder {
        String str;

        /**
         * Builder constructor.
         */
        HeadingsBuilder() {
            str = "";
        }


        /**
         * Add heading by Element.
         *
         * @param elInHead head value
         * @param typeOfHead type of heading
         * @return builder
         */
        public  HeadingsBuilder setHeader(Element elInHead, int typeOfHead) {
            this.str = "";

            for (int i = 0; i < typeOfHead; i++) {
                str = str + "#";
            }

            str += " ";

            str += elInHead.str;

            return this;
        }

        /**
         * Add heading by String.
         *
         * @param str head value
         * @param typeOfHead type of heading
         * @return builder
         */
        public  HeadingsBuilder setHeader(String str, int typeOfHead) {
            this.str = "";

            for (int i = 0; i < typeOfHead; i++) {
                this.str = this.str + "#";
            }

            this.str += " ";

            this.str += str;

            return this;
        }


        /**
         * Build builder.
         *
         * @return built Heading
         */
        public Headings build() {
            return new Headings(this);
        }


    }
}
