package org.example;

/**
 * class for cards, with it cods.
 * (I can not even imagine a person,
 * who did not understand that by name)
 */
public class Card {
    int type;
    int suit;
    int value;

    Card(Deck.types type, Deck.suit suit) {
        this.suit = suit.ordinal();
        this.type = type.ordinal();
    }
}
