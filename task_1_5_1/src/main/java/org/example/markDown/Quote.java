package org.example.markDown;

/**
 * Quote class.
 */
public class Quote extends Element {

    /**
     * Constructor by builder.
     *
     * @param builder builder
     */
    Quote(QuoteBuilder builder) {
        this.str = builder.str;
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

        Quote quote = (Quote) obj;

        return quote.str.equals(this.str);
    }


    /**
     * Builder class.
     */
    public static class QuoteBuilder {
        String str;

        /**
         * Set Quote by Element.
         *
         * @param el new quote
         * @return builder
         */
        public QuoteBuilder setQuote(Element el) {
            this.str = "";
            this.str = ">" + el.str;
            this.str = this.str.replaceAll("\n", "\n>");
            return this;
        }

        /**
         * Set Quote by String.
         *
         * @param str new quote
         * @return builder
         */
        public QuoteBuilder setQuote(String str) {
            this.str = "";
            this.str = ">" + str;
            this.str = this.str.replaceAll("\n", "\n>");
            return this;
        }

        /**
         * Build builder.
         *
         * @return built Quote
         */
        public Quote build() {
            return new Quote(this);
        }
    }
}
