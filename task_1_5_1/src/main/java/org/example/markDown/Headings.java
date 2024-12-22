package org.example.markDown;

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
        public HeadingsBuilder() {
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
            if (typeOfHead <= 0 || typeOfHead > 6) {
                throw new RuntimeException("type if Heading is out of bounds");
            }
            StringBuilder builder = new StringBuilder();


            builder.append("#".repeat(typeOfHead));

            builder.append(" ");

            builder.append(elInHead.str);
            builder.append("\n");
            this.str = builder.toString();

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
            if (typeOfHead <= 0 || typeOfHead > 6) {
                throw new RuntimeException("type if Heading is out of bounds");
            }
            StringBuilder builder = new StringBuilder();


            builder.append("#".repeat(typeOfHead));

            builder.append(" ");

            builder.append(str);
            builder.append("\n");
            this.str = builder.toString();

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
