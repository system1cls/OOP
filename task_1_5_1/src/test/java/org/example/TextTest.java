package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class TextTest {
    @Test
    void print() {
        Text.TextBuilder builder = new Text.TextBuilder();
        builder.setText("Just").setLineBreak()
                .setBold("simple").setLineBreak()
                .setCode("example").setParagraph()
                .setCursive("of").setLineBreak()
                .setStrike("text");
        System.out.print(builder.build());
    }

    @Test
    void checkEq() {
        Text.TextBuilder builder = new Text.TextBuilder();
        builder.setText("Just").setLineBreak()
                .setBold("simple").setLineBreak()
                .setCode("example").setParagraph()
                .setCursive("of").setLineBreak()
                .setStrike("text");

        Text.TextBuilder builder1 = new Text.TextBuilder();
        builder1.setText(new Text.TextBuilder().setText("Just").build())
                .setLineBreak()
                .setBold(new Text.TextBuilder().setText("simple").build())
                .setLineBreak()
                .setCode(new Text.TextBuilder().setText("example").build())
                .setParagraph()
                .setCursive(new Text.TextBuilder().setText("of").build())
                .setLineBreak()
                .setStrike(new Text.TextBuilder().setText("text").build());


        assertTrue(builder.build().equals(builder1.build()));
    }
}