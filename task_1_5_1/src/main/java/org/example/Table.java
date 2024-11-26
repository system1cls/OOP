package org.example;

import java.util.Arrays;

/**
 * Table class.
 */
public class Table extends Element {
    String [][]table;
    Align []aligns;
    int cntRows;
    int cntCols;


    /**
     * Constructor by builder.
     *
     * @param builder builder
     */
    Table(TableBuilder builder) {
        cntCols = builder.cntCols;
        cntRows = builder.cntRows;
        aligns = Arrays.copyOf(builder.aligns, cntCols);
        table = Arrays.copyOf(builder.table, cntRows);
        for (int i = 0; i < cntRows; i++) {
            table[i] = Arrays.copyOf(builder.table[i], cntCols);
        }
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
        for (int i = 0; i < cntRows; i++) {
            str += "| ";
            for (int j = 0; j < cntCols; j++) {
                str += table[i][j] + " | ";
            }

            str += "\n";

            if (i == 0) {

                str += "|";
                for (int j = 0; j < cntCols; j++) {
                    switch (aligns[j]) {
                        case LEFT_ALIGN -> str += " :---------- |";
                        case RIGHT_ALIGN -> str += " ----------: |";
                        case CENTER_ALIGN -> str += " :---------: |";
                    }
                }

                str+= "\n";
            }
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

        Table table1 = (Table) obj;

        if (cntCols != table1.cntCols || cntRows != table1.cntRows) {
            return false;
        }

        for (int i = 0; i < cntCols; i++) {
            if (aligns[i] != table1.aligns[i]) {
                return false;
            }
        }

        for (int i = 0; i < cntRows; i++) {
            for (int j = 0; j < cntCols; j++) {
                if (!table[i][j].equals(table1.table[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }


    /**
     * Builder class.
     */
    public static class TableBuilder {
        String [][]table;
        Align []aligns;
        int cntRows;
        int maxRows;
        int cntCols;
        private boolean isMaxSet = false;

        /**
         * Builder constructor.
         */
        TableBuilder() {
            table = null;
        }

        /**
         * Set alignments.
         *
         * @param mass aligns
         * @return builder
         */
        public TableBuilder setAlignments(Align ... mass) {
            if (cntCols == 0) {
                cntCols = mass.length;
            }

            aligns = new Align[cntCols];

            System.arraycopy(mass, 0, aligns, 0, mass.length);

            return this;
        }


        /**
         * Set max cnt of Rows.
         *
         * @param rows cnt rows
         * @return builder
         */
        public TableBuilder setMaxRows(int rows) {
            if (isMaxSet || cntRows > rows) {
                throw new RuntimeException("Rewriting max of rows");
            }

            isMaxSet = true;
            maxRows = rows;
            return this;
        }

        /**
         * Add Row by String.
         *
         * @param strs nodes of row
         * @return builder
         */
        public TableBuilder addRow(String ... strs) {
            if (strs.length != cntCols && cntCols != 0) {
                throw new RuntimeException("Rewriting count of columns");
            }

            cntCols = strs.length;
            if (table == null) {
                if (!isMaxSet) {
                    maxRows = 10;
                }

                table = new String[maxRows][cntCols];
            }

            for (int i = 0; i < cntCols; i++) {
                table[cntRows][i] = strs[i];
            }

            cntRows++;

            return this;
        }

        /**
         * Add row by Elements.
         *
         * @param elements nodes of row
         * @return builder
         */
        public TableBuilder addRow(Element ... elements) {
            if (elements.length != cntCols && cntCols != 0) {
                throw new RuntimeException("Rewriting count of columns");
            }

            cntCols = elements.length;
            if (table == null) {
                if (!isMaxSet) {
                    maxRows = 10;
                }

                table = new String[maxRows][cntCols];
            }

            for (int i = 0; i < cntCols; i++) {
                table[cntRows][i] = elements[i].str;
            }

            cntRows++;

            return this;
        }


        /**
         * Build builder.
         *
         * @return built Table
         */
        public Table build() {
            return new Table(this);
        }
    }

    /**
     * Enum alignments.
     */
    public enum Align {
        LEFT_ALIGN,
        RIGHT_ALIGN,
        CENTER_ALIGN
    }
}

