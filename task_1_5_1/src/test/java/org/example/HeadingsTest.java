package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.markDown.Headings;
import org.example.markDown.Text;
import org.junit.jupiter.api.Test;

class HeadingsTest {

    @Test
    void print() {
        Headings.HeadingsBuilder builder = new Headings.HeadingsBuilder();
        builder.setHeader("header", 2);
        System.out.print(builder.build());
    }

    @Test
    void toStringTest() {
        Headings.HeadingsBuilder builder = new Headings.HeadingsBuilder();
        builder.setHeader("header", 2);
        assertEquals(builder.build().toString(), "## header\n");
    }

    @Test
    void checkEq() {
        Headings.HeadingsBuilder builder = new Headings.HeadingsBuilder();
        builder.setHeader("header", 2);

        Headings.HeadingsBuilder builder1 = new Headings.HeadingsBuilder();
        builder1.setHeader(new Text.TextBuilder()
                .setText("header").build(), 2);

        assertTrue(builder.build().equals(builder1.build()));
    }
}