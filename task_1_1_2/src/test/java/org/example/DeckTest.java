package org.example;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class DeckTest {

    @Test
    void make_new_deck() {
        Deck deck = new Deck();
        for (int i = 1; i <= 10; i++) {
            assertEquals(deck.make_new_deck(i), i * 52);
            deck.clear_deck();
        }
    }


    @Test
    void get_card() {
        Deck deck = new Deck();
        Card card;
        deck.make_new_deck(1);
        for (int i = 0; i < 50; i++) {
            card = deck.get_card();
            assertTrue(card.type >= 0 && card.type <= 12 &&
                    card.suit >= 0 && card.suit <= 3);
        }
        deck.clear_deck();
    }
}