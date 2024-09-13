package org.example;

import java.util.ArrayList;

public class Hand {

    public int add_card(Card card){
        cards.add(card);
        is_value_ready = false;
        return cards.size();
    }

    public void clear_hand() {
        cards.clear();
        is_value_ready = false;
    }

    public void print_open_cards(){
        if (!is_value_ready) get_cards_value();
        System.out.print("[");
        for (Card card : cards) {
            if (card == cards.getLast()){
                System.out.print(types[card.type] + " " + suits[card.suit] + " (" + card.value + ")");
            }
            else {
                System.out.print(types[card.type] + " " + suits[card.suit] + " (" + card.value + ") ");
            }
        }
        System.out.println("] => "+ score);
    }

    public void print_closed_cards(){
        Card first = cards.getFirst();
        System.out.print("[" + types[first.type] + " " + suits[first.suit] + " (");
        if (first.value <= 8){
            System.out.print(first.type + 2);
        }
        else if (first.value <= 11) {
            System.out.print(10);
        }
        else {
            System.out.print("1/11");
        }
        System.out.print(")");

        for (int i = 1; i < cards.size(); i++) {
            System.out.print(" <closed card>");
        }
        System.out.println("]");
    }

    public int get_score(){
        if (!is_value_ready) get_cards_value();
        return score;
    }

    public Card get_last(){
        if (!is_value_ready) get_cards_value();
        return cards.getLast();
    }

    public void print_card(Card card){
        System.out.print(types[card.type] + " " + suits[card.suit] + " (" + card.value + ")");
    }

    private void get_cards_value(){
        int ace_cnt = 0;
        score = 0;
        for (Card card : cards) {
            if (card.type <= 8) {
                card.value = card.type + 2;
                score += card.value;
            }
            else if (card.type <= 11) {
                card.value = 10;
                score += 10;
            }
            else ace_cnt++;
        }

        if (ace_cnt != 0) {
            for (Card card : cards) {
                if (card.type == 12) {
                    if (10 + ace_cnt + score <= 21){
                        card.value = 11;
                        score += 11;
                    }
                    else{
                        card.value = 1;
                        score += 1;
                    }
                }
            }
        }

        is_value_ready = true;
    }

    private Boolean is_value_ready = false;

    private int score;

    private ArrayList<Card> cards = new ArrayList<>();

    private String[] types = {"Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Jack", "Lady",
            "King", "Ace"};

    private String[] suits = {"Spade", "Club", "Hearts", "Diamond"};
}
