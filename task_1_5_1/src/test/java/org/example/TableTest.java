package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TableTest {

    @Test
    void print() {
        Table.TableBuilder builder = new Table.TableBuilder()
                .setAlignments(Table.Align.LEFT_ALIGN, Table.Align.RIGHT_ALIGN)
                .setMaxRows(8)
                .addRow("Index", "Random");

        for (int i = 1; i <= 5; i++) {
            final var value = (int) (Math.random() * 10);
            if (value > 5) {
                builder.addRow(
                        new Text.TextBuilder().setText(Integer.toString(i)).build(),
                        new Text.TextBuilder().setBold(String.valueOf(value)).build()
                );
            } else {
                builder.addRow(
                        new Text.TextBuilder().setText(Integer.toString(i)).build(),
                        new Text.TextBuilder().setText(
                                Integer.toString((int) (Math.random() * 10))).build());
            }
        }

        System.out.print(builder.build());
    }

    @Test
    void checkEq() {
        Table.TableBuilder builder = new Table.TableBuilder();
        builder.setAlignments(Table.Align.RIGHT_ALIGN,
                        Table.Align.CENTER_ALIGN, Table.Align.LEFT_ALIGN)
                .setMaxRows(5)
                .addRow("Index", "Index x2", "Index * Index");
        for (int i = 0; i < 3; i++) {
            builder.addRow(
                    Integer.toString(i),
                    Integer.toString(i * 2),
                    Integer.toString(i * i)
            );
        }

        Table.TableBuilder builder1 = new Table.TableBuilder();
        builder1.setAlignments(Table.Align.RIGHT_ALIGN, Table.Align.CENTER_ALIGN, Table.Align.LEFT_ALIGN)
                .setMaxRows(5)
                .addRow("Index", "Index x2", "Index * Index");
        for (int i = 0; i < 3; i++) {
            builder1.addRow(
                    new Text.TextBuilder().setText(Integer.toString(i)).build(),
                    new Text.TextBuilder().setText(Integer.toString(i * 2)).build(),
                    new Text.TextBuilder().setText(Integer.toString(i * i)).build()
            );
        }

        assertTrue(builder1.build().equals(builder.build()));
    }

    @Test
    void catchOver() {
        Table.TableBuilder builder = new Table.TableBuilder();
        builder.setAlignments(Table.Align.RIGHT_ALIGN, Table.Align.CENTER_ALIGN, Table.Align.LEFT_ALIGN)
                .setMaxRows(5)
                .addRow("Index", "Index x2", "Index * Index");
        for (int i = 0; i < 4; i++) {
            builder.addRow(
                    Integer.toString(i),
                    Integer.toString(i * 2),
                    Integer.toString(i * i)
            );
        }
        assertThrows(
                RuntimeException.class,
                () ->
                {
                    builder.addRow(
                        Integer.toString(5),
                        Integer.toString(5 * 2),
                        Integer.toString(5 * 5)
                );
            });


        Table.TableBuilder builder1 = new Table.TableBuilder();
        builder1.setAlignments(Table.Align.RIGHT_ALIGN,
                        Table.Align.CENTER_ALIGN, Table.Align.LEFT_ALIGN)
                .setMaxRows(5);
        assertThrows(RuntimeException.class, () -> {
            builder1.addRow("Index", "Index x2");
        });

    }

}