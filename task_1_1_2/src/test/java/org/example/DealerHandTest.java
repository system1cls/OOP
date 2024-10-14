package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DealerHandTest {

    @Test
    void handTest() {
        Deck deck = new Deck();
        DealerHand hand = new DealerHand();

        deck.make_new_deck(1);
        assertEquals(hand.add_card(deck.get_card()), 1);
        assertEquals(hand.add_card(deck.get_card()), 2);

        assertTrue(hand.get_score() <= 21);
        hand.print_card(hand.get_last());

        System.out.print("\n\n");

        hand.print_cards(false);
        hand.print_cards(true);
    }

}