package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.markDown.Links;
import org.example.markDown.Text;
import org.junit.jupiter.api.Test;

class LinksTest {

    @Test
    void print() {
        Links.LinksBuilder builder = new Links.LinksBuilder();
        builder.setLink("https://mapgenie.io/god-of-war-2018/maps/midgard");
        System.out.print(builder.build());
    }

    @Test
    void toStringTest() {
        Links.LinksBuilder builder = new Links.LinksBuilder();
        builder.setLink("https://mapgenie.io/god-of-war-2018/maps/midgard");
        assertEquals(builder.build().toString(),"<https://mapgenie.io/god-of-war-2018/maps/midgard>");
    }

    @Test
    void checkEq() {
        Links.LinksBuilder builder = new Links.LinksBuilder();
        builder.setLink("https://mapgenie.io/god-of-war-2018/maps/midgard");
        Links.LinksBuilder builder1 = new Links.LinksBuilder();
        builder1.setLink(new Text.TextBuilder().setText("https://mapgenie.io/god-of-war-2018/maps/midgard").build());

        assertTrue(builder.build().equals(builder1.build()));
    }

}