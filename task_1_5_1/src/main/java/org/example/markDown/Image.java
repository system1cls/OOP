package org.example.markDown;

/**
 * Image class.
 */
public class Image extends Element {
    String text;
    String link;

    /**
     * Simple Constructor.
     */
    Image() {
        str = "";
    }

    /**
     * Constructor by builder.
     *
     * @param builder builder
     */
    Image(ImageBuilder builder) {
        if (builder.text == null || builder.link == null) {
            throw new RuntimeException();
        }
        text = builder.text;
        link = builder.link;
        str = this.toString();
    }

    /**
     * Make string.
     *
     * @return string
     */
    @Override
    public String toString() {
        str = "![" + text + "](" + link + ")";
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

        Image newImage = (Image) obj;

        if (!link.equals(newImage.link)) {
            return false;
        }

        return (text.equals(newImage.text));
    }

    /**
     * Builder class.
     */
    public static class ImageBuilder {
        String text;
        String link;


        /**
         * Builder constructor.
         */
        public ImageBuilder() {
            text = "";
            link = "";
        }

        /**
         * Set link.
         *
         * @param link link
         * @return builder
         */
        public ImageBuilder setLink(String link) {
            this.link = link;
            return this;
        }

        /**
         * Set text by String.
         *
         * @param text text
         * @return builder
         */
        public ImageBuilder setText(String text) {
            this.text = text;
            return this;
        }

        /**
         * Set text by Element.
         *
         * @param text text
         * @return builder
         */
        public ImageBuilder setText(Element text) {
            this.text = text.str;
            return this;
        }

        /**
         * Build builder.
         *
         * @return built Image
         */
        public Image build() {
            Image img = null;
            try {
                img =  new Image(this);
            } catch (RuntimeException ex) {
                System.out.print("link || text == null");
            }
            return img;
        }
    }
}
