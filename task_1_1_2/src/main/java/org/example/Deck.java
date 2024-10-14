package org.example;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for deck with all cards in start.
 */
public class Deck {

    private ArrayList<Card> cards = new ArrayList<>();


    enum types {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    enum suit{
        SPADE,
        CLUB,
        HEART,
        DIAMOND
    }


    /**
     * Get random card from the deck.
     *
     * @return random card
     */
    public Card get_card() {
        Random random = new Random();
        int id = random.nextInt(cards.size() - 1);
        return cards.remove(id);
    }

    /**
     * Create new deck.
     *
     * @param cntDecks - count of usual decks to be added in our.
     * @return count of cards in deck.
     */
    public int make_new_deck(int cntDecks) {
        for (int i = 0; i < cntDecks; i++) {
            for (types j : types.values()) {
                for (suit q : suit.values()) {
                    cards.add(new Card(j, q));
                }
            }
        }
        return cards.size();
    }

    /**
     * Clear list of cards.
     */
    public void clear_deck() {
        cards.clear();
    }



}
