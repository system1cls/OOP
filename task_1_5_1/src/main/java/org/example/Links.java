package org.example;

/**
 * Link class.
 */
public class Links extends Element {

    /**
     * Constructor by builder.
     *
     * @param builder builder
     */
    Links(LinksBuilder builder) {
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

        Links link = (Links) obj;

        return link.str.equals(this.str);
    }

    /**
     * Builder class.
     */
    public static class LinksBuilder {
        String str;

        /**
         * Builder constructor.
         */
        LinksBuilder() {
            str = "";
        }


        /**
         * Set link by String.
         *
         * @param link link
         * @return builder
         */
        public LinksBuilder setLink(String link) {
            str = "";
            str = "<" + link + ">";
            return this;
        }

        /**
         * Set link by Element.
         *
         * @param link link
         * @return builder
         */
        public LinksBuilder setLink(Element link) {
            str = "";
            str = "<" + link.str + ">";
            return this;
        }


        /**
         * Build builder.
         *
         * @return built Link
         */
        public Links build() {
            return new Links(this);
        }
    }
}
