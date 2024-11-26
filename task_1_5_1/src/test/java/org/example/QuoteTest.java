package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class QuoteTest {

    @Test
    void print() {
        Quote.QuoteBuilder builder = new Quote.QuoteBuilder();
        builder.setQuote("People who thinks they know everything are a great \n"
                + "annoyance to those of us who do\n"
                + "\t\t\tIsaak Asimov");
        System.out.print(builder.build());
    }

    @Test
    void checkEq() {
        Quote.QuoteBuilder builder = new Quote.QuoteBuilder();
        builder.setQuote("People who thinks they know everything are a great \n"
                + "annoyance to those of us who do\n"
                + "\t\t\tIsaak Asimov");

        Quote.QuoteBuilder builder1 = new Quote.QuoteBuilder();
        builder1.setQuote(new Text.TextBuilder()
                .setText("People who thinks they know everything are a great \n"
                        + "annoyance to those of us who do\n"
                        + "\t\t\tIsaak Asimov").build());

        assertTrue(builder.build().equals(builder1.build()));
    }
}