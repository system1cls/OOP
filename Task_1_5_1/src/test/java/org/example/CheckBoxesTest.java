package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.markDown.CheckBoxes;
import org.example.markDown.Text;
import org.junit.jupiter.api.Test;

class CheckBoxesTest {

    @Test
    void print1() {
        CheckBoxes.CheckBoxesBuilder builder = new CheckBoxes.CheckBoxesBuilder();
        builder = builder.addNode(true, "Wake up");
        builder.addNode(true, "Visit U");
        builder = builder.addNode(false, "do cleaning");
        System.out.print(builder.build());
    }

    @Test
    void toStringTest() {
        CheckBoxes.CheckBoxesBuilder builder = new CheckBoxes.CheckBoxesBuilder();
        builder = builder.addNode(true, "Wake up");
        builder.addNode(true, "Visit U");
        builder = builder.addNode(false, "do cleaning");
        String answer = "- [x] Wake up\n- [x] Visit U\n- [ ] do cleaning\n";
        assertEquals(builder.build().toString(), answer);
    }

    @Test
    void eqcheck() {
        CheckBoxes.CheckBoxesBuilder builder = new CheckBoxes.CheckBoxesBuilder();
        builder = builder.addNode(true, "Wake up");
        builder.addNode(true, "Visit U");
        builder = builder.addNode(false, "do cleaning");

        CheckBoxes.CheckBoxesBuilder builder2 = new CheckBoxes.CheckBoxesBuilder();
        builder2 = builder2.addNode(true, new Text.TextBuilder().setText("Wake up").build());
        builder2.addNode(true, new Text.TextBuilder().setText("Visit U").build());
        builder2 = builder2.addNode(false, new Text.TextBuilder().setText("do cleaning").build());

        assertTrue(builder2.build().equals(builder.build()));
    }
}