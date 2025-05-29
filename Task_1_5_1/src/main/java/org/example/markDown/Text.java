package org.example.markDown;

/**
 * Text class.
 */
public class Text extends Element {

    /**
     * Simple constructor.
     */
    Text() {
        str = "";
    }


    /**
     * Constructor by builder.
     *
     * @param textBuilder builder
     */
    Text(TextBuilder textBuilder) {
        this.str = textBuilder.str;
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

        Text newText = (Text) obj;

        return this.str.equals(newText.str);
    }

    /**
     * Builder class.
     */
    public static class TextBuilder {
        String str;
        private StringBuilder builder;

        /**
         * Builder constructor.
         */
        public TextBuilder() {
            str = "";
            builder = new StringBuilder();
        }


        /**
         * Set paragraph.
         *
         * @return builder
         */
        public TextBuilder setParagraph() {
            builder.append("\n\n");
            return this;
        }

        /**
         * Add string.
         *
         * @param toAdd str to add
         * @return builder
         */
        public TextBuilder setText(String toAdd) {
            builder.append(toAdd);
            return this;
        }

        /**
         * Add string by Element.
         *
         * @param elToAdd str to add
         * @return builder
         */
        public TextBuilder setText(Element elToAdd) {
            builder.append(elToAdd.toString());
            return this;
        }

        /**
         * Set Line break.
         *
         * @return builder
         */
        public TextBuilder setLineBreak() {
            builder.append("<br>");
            return this;
        }

        /**
         * Set text in cursive.
         *
         * @param strCur string in cursive form
         * @return builder
         */
        public TextBuilder setCursive(String strCur) {
            builder.append("*")
                    .append(strCur)
                    .append("*");
            return this;
        }

        /**
         * Set text in cursive by Element.
         *
         * @param strCur string in cursive form
         * @return builder
         */
        public TextBuilder setCursive(Element strCur) {
            builder.append("*")
                    .append(strCur.toString())
                    .append("*");
            return this;
        }

        /**
         * Set text in bold.
         *
         * @param strBold string in bold form
         * @return builder
         */
        public TextBuilder setBold(String strBold) {
            builder.append("**")
                    .append(strBold)
                    .append("**");
            return this;
        }

        /**
         * Set text in bold by Element.
         *
         * @param strBold string in bold form
         * @return builder
         */
        public TextBuilder setBold(Element strBold) {
            builder.append("**")
                    .append(strBold.toString())
                    .append("**");
            return this;
        }


        /**
         * Set text in strike.
         *
         * @param strStrike string in strike form
         * @return builder
         */
        public TextBuilder setStrike(String strStrike) {
            builder.append("~~")
                    .append(strStrike)
                    .append("~~");
            return this;
        }

        /**
         * Set text in strike by Element.
         *
         * @param strStrike string in strike form
         * @return builder
         */
        public TextBuilder setStrike(Element strStrike) {
            builder.append("~~")
                    .append(strStrike.toString())
                    .append("~~");
            return this;
        }

        /**
         * Set text in code.
         *
         * @param toCode string in code form
         * @return builder
         */
        public TextBuilder setCode(String toCode) {
            builder.append('`')
                    .append(toCode)
                    .append('`');
            return this;
        }

        /**
         * Set text in code by Element.
         *
         * @param toCode string in code form
         * @return builder
         */
        public TextBuilder setCode(Element toCode) {
            builder.append('`')
                    .append(toCode.toString())
                    .append('`');
            return this;
        }

        /**
         * Build builder.
         *
         * @return built Text
         */
        public Text build() {
            this.str = builder.toString();
            return new Text(this);
        }
    }
}
