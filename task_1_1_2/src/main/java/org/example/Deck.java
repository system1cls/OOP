package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    public Card get_card() {
        Random random = new Random();
        int id = random.nextInt(cards.size() - 1);
        return cards.remove(id);
    }

    public int make_new_deck(int cnt_decks) {
        for (int i = 0; i < cnt_decks; i++) {
            for (int j = 0; j <= 12; j++) {
                for (int q = 0; q <= 3; q++) {
                    cards.add(new Card(j, q));
                }
            }
        }
        return cards.size();
    }

    public void clear_deck() {
        cards.clear();
    }

    private ArrayList<Card> cards = new ArrayList<>();

}
