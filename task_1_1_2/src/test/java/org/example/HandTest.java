package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class HandTest {

    @Test
    void handTest() {
        Deck deck = new Deck();
        Hand hand = new Hand();

        deck.make_new_deck(1);
        assertEquals(hand.add_card(deck.get_card()), 1);
        assertEquals(hand.add_card(deck.get_card()), 2);

        assertTrue(hand.get_score() <= 21);
        hand.print_card(hand.get_last());

        System.out.print("\n\n");

        hand.print_open_cards();
        hand.print_closed_cards();
    }

}