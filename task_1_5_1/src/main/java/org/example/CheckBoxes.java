package org.example;

/**
 * Checkboxes class.
 */
public class CheckBoxes extends Element {
    List.ListBuilder list;

    /**
     * Constructor by builder.
     *
     * @param builder builder.
     */
    CheckBoxes(CheckBoxesBuilder builder) {
        list = builder.list;
        str = builder.list.build().str;
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

        CheckBoxes box = (CheckBoxes) obj;

        return list.build().equals(box.list.build());
    }

    /**
     * Builder class.
     */
    public static class CheckBoxesBuilder {
        List.ListBuilder list;

        /**
         * Builder constructor.
         */
        CheckBoxesBuilder() {
            list = new List.ListBuilder('-');
        }

        /**
         * addNode to the checkbox by Element.
         *
         * @param set is node set
         * @param el node
         * @return builder
         */
        public CheckBoxesBuilder addNode(boolean set, Element el) {
            Text.TextBuilder text = new Text.TextBuilder();

            if (set) {
                text = text.setText("[x] " + el.str);
            } else {
                text = text.setText("[ ] " + el.str);
            }

            list.addNode(text.build());
            return this;
        }

        /**
         * addNode to the checkbox by String value.
         *
         * @param set is node set
         * @param el node
         * @return builder
         */
        public CheckBoxesBuilder addNode(boolean set, String el) {
            Text.TextBuilder text = new Text.TextBuilder();

            if (set) {
                text = text.setText("[x] " + el);
            } else {
                text = text.setText("[ ] " + el);
            }

            list.addNode(text.build());
            return this;
        }


        /**
         * Build builder.
         *
         * @return built checkbox
         */
        public CheckBoxes build() {
            return new CheckBoxes(this);
        }
    }
}
