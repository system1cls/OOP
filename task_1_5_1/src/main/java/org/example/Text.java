package org.example;

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

        /**
         * Builder constructor.
         */
        TextBuilder() {
            str = "";
        }


        /**
         * Set paragraph.
         *
         * @return builder
         */
        public TextBuilder setParagraph() {
            this.str = this.str + "\n\n";
            return this;
        }

        /**
         * Add string.
         *
         * @param toAdd str to add
         * @return builder
         */
        public TextBuilder setText(String toAdd) {
            this.str = this.str + toAdd;
            return this;
        }

        /**
         * Set Line break.
         *
         * @return builder
         */
        public TextBuilder setLineBreak() {
            this.str = this.str + "<br>";
            return this;
        }

        /**
         * Set text in cursive.
         *
         * @param strCur string in cursive form
         * @return builder
         */
        public TextBuilder setCursive(String strCur) {
            this.str = this.str + "*" + strCur + "*";
            return this;
        }

        /**
         * Set text in bold.
         *
         * @param strBold string in bold form
         * @return builder
         */
        public TextBuilder setBold(String strBold) {
            this.str = this.str + "**" + strBold + "**";
            return this;
        }

        /**
         * Set text in strike.
         *
         * @param strStrike string in strike form
         * @return builder
         */
        public TextBuilder setStrike(String strStrike) {
            this.str = this.str + "~~" + strStrike + "~~";
            return this;
        }

        /**
         * Set text in code.
         *
         * @param toCode string in code form
         * @return builder
         */
        public TextBuilder setCode(String toCode) {
            this.str = this.str + '`' + toCode + '`';
            return this;
        }

        /**
         * Add string by Element.
         *
         * @param elToAdd str to add
         * @return builder
         */
        public TextBuilder setText(Element elToAdd) {
            this.str = this.str + elToAdd;
            return this;
        }

        /**
         * Set text in cursive by Element.
         *
         * @param strCur string in cursive form
         * @return builder
         */
        public TextBuilder setCursive(Element strCur) {
            this.str = this.str + "*" + strCur + "*";
            return this;
        }

        /**
         * Set text in bold by Element.
         *
         * @param strBold string in bold form
         * @return builder
         */
        public TextBuilder setBold(Element strBold) {
            this.str = this.str + "**" + strBold + "**";
            return this;
        }

        /**
         * Set text in strike by Element.
         *
         * @param strStrike string in strike form
         * @return builder
         */
        public TextBuilder setStrike(Element strStrike) {
            this.str = this.str + "~~" + strStrike + "~~";
            return this;
        }

        /**
         * Set text in code by Element.
         *
         * @param toCode string in code form
         * @return builder
         */
        public TextBuilder setCode(Element toCode) {
            this.str = this.str + '`' + toCode + '`';
            return this;
        }

        /**
         * Build builder.
         *
         * @return built Text
         */
        public Text build() {
            return new Text(this);
        }
    }
}
