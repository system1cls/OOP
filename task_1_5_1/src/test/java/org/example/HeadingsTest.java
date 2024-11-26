package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class HeadingsTest {

    @Test
    void print() {
        Headings.HeadingsBuilder builder = new Headings.HeadingsBuilder();
        builder.setHeader("header", 2);
        System.out.print(builder.build());
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