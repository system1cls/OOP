package org.example;

import java.util.ArrayList;

/**
 * Class for gamer`s deck.
 */
public class Hand {

    /**
     * Method to add card in deck.
     *
     * @param card - card, you want to add
     * @return new count of cards in gamer`s deck
     */
    public int add_card(Card card) {
        cards.add(card);
        isValueReady = false;
        return cards.size();
    }

    /**
     * CLear the list.
     */
    public void clear_hand() {
        cards.clear();
        isValueReady = false;
    }

    /**
     * Print cards, if you know their value.
     */
    public void print_cards() {
        if (!isValueReady) {
            get_cards_value();
        }
        System.out.print("[");
        for (int i = 0; i < cards.size(); i++) {
            if (i == cards.size() - 1) {
                System.out.print(types[(cards.get(i)).type] + " "
                        + suits[(cards.get(i)).suit] + " (" + (cards.get(i)).value + ")");
            } else {
                System.out.print(types[(cards.get(i)).type] + " "
                        + suits[(cards.get(i)).suit] + " (" + (cards.get(i)).value + ") ");
            }
        }
        System.out.println("] => " + score);
    }



    /**
     * method to get total value of deck.
     *
     * @return total value
     */
    public int get_score() {
        if (!isValueReady) {
            get_cards_value();
        }
        return score;
    }

    /**
     * Get values of all cards and return last of them.
     *
     * @return The last Card
     */
    public Card get_last() {
        if (!isValueReady) {
            get_cards_value();
        }
        return cards.get(cards.size() - 1);
    }

    /**
     * Print card.
     *
     * @param card - card to print
     */
    public void print_card(Card card) {
        System.out.print(types[card.type] + " " + suits[card.suit] + " (" + card.value + ")");
    }

    /**
     * Get values of all cards.
     */
    protected void get_cards_value() {
        int aceCnt = 0;
        score = 0;
        for (Card card : cards) {
            if (card.type <= 8) {
                card.value = card.type + 2;
                score += card.value;
            } else if (card.type <= 11) {
                card.value = 10;
                score += 10;
            } else {
                aceCnt++;
            }
        }

        if (aceCnt != 0) {
            for (Card card : cards) {
                if (card.type == 12) {
                    if (10 + aceCnt + score <= 21) {
                        card.value = 11;
                        score += 11;
                    } else {
                        card.value = 1;
                        score += 1;
                    }
                }
            }
        }

        isValueReady = true;
    }

    protected Boolean isValueReady = false;

    protected int score;

    protected ArrayList<Card> cards = new ArrayList<>();

    protected String[] types = {"Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Jack", "Lady",
            "King", "Ace"};

    protected String[] suits = {"Spade", "Club", "Hearts", "Diamond"};
}
