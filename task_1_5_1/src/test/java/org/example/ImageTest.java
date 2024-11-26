package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ImageTest {

    @Test
    void print() {
        Image.ImageBuilder builder = new Image.ImageBuilder();
        builder.setText("image");
        builder.setLink("https://images.unsplash.com/photo-1571992049393-827d13da8fe3?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
        System.out.print(builder.build());
    }

    @Test
    void checkEq() {
        Image.ImageBuilder builder = new Image.ImageBuilder();
        builder.setText("image");
        builder.setLink("https://images.unsplash.com/photo-1571992049393-827d13da8fe3?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        Image.ImageBuilder builder1 = new Image.ImageBuilder();
        builder1.setText(new Text.TextBuilder().setText("image").build());
        builder1.setLink("https://images.unsplash.com/photo-1571992049393-827d13da8fe3?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");

        assertTrue(builder.build().equals(builder1.build()));
    }
}