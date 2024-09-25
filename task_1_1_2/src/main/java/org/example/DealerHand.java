package org.example;

public class DealerHand extends Hand {
    /**
     * Print cards, if you do not know their value.
     */
    public void print_closed_cards() {
        Card first = cards.get(0);
        System.out.print("[" + types[first.type] + " " + suits[first.suit] + " (");
        if (first.value <= 8) {
            System.out.print(first.type + 2);
        } else if (first.value <= 11) {
            System.out.print(10);
        } else {
            System.out.print("1/11");
        }
        System.out.print(")");

        for (int i = 1; i < cards.size(); i++) {
            System.out.print(" <closed card>");
        }
        System.out.println("]");
    }
}
