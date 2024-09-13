package org.example;

import java.util.Scanner;

public class BlackJack {

    public void game() {
        rounds = 1;
        deck = new Deck();
        player = new Hand();
        dealer = new Hand();
        player_res = 0;
        dealer_res = 0;
        boolean ready_to_continue = true;
        int choice;
        boolean is_new_round;
        Card card;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!\n");
        is_written_smth_after_cards = true;

        while (ready_to_continue) {

            System.out.println("Round " + rounds + '\n');
            rounds++;
            System.out.println("Enter \"1\" if you want to enter the number of decks in the game. " +
                    " Enter \"0\", if you do not want, then there will be one deck in the game");
            choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("Enter the number of decks from 1 to 10");
                choice = scanner.nextInt();
                if (choice > 10) choice = 10;
                if (choice <= 0) choice = 1;
                deck.make_new_deck(choice);
            } else {
                deck.make_new_deck(1);
            }

            player.add_card(deck.get_card());
            dealer.add_card(deck.get_card());
            player.add_card(deck.get_card());
            dealer.add_card(deck.get_card());

            System.out.println("The dealer has dealt the cards!");

            is_new_round = check_result(player.get_score(), dealer.get_score(), false, true);

            if (!is_new_round) {
                choice = 1;
                System.out.println("Your turn!");
                System.out.println("__________");

                while (choice == 1 && !is_new_round) {
                    System.out.print("Enter \"1\" to take the card, and \"0\" to stop.\n");
                    is_written_smth_after_cards = true;
                    choice = scanner.nextInt();

                    if (choice == 1) {
                        player.add_card(deck.get_card());
                        card = player.get_last();
                        System.out.print("You opened: ");
                        is_written_smth_after_cards = true;
                        player.print_card(card);
                        System.out.print("\n\n");

                        is_new_round = check_result(player.get_score(), dealer.get_score(),
                                false, true);
                    }

                }
            }


            if (!is_new_round) {
                System.out.println("Dealer`s turn!");
                System.out.println("______________");

                System.out.print("The dealer opens a closed card\n");
                is_written_smth_after_cards = true;
                is_new_round = check_result(player.get_score(), dealer.get_score(),
                        false, false);

                while (dealer.get_score() < 17 && !is_new_round) {
                    dealer.add_card(deck.get_card());
                    card = dealer.get_last();
                    System.out.print("Dealer opened: ");
                    is_written_smth_after_cards = true;
                    dealer.print_card(card);
                    System.out.print("\n\n");

                    is_new_round = check_result(player.get_score(), dealer.get_score(),
                            false, false);
                }
            }


            if (!is_new_round) {
                check_result(player.get_score(), dealer.get_score(), true, false);
            }

            System.out.println("Enter \"1\" if you want to start new round. " +
                    " Enter \"0\", if you do not want it");
            is_written_smth_after_cards = true;
            choice = scanner.nextInt();
            if (choice == 0) ready_to_continue = false;

            deck.clear_deck();
            player.clear_hand();
            dealer.clear_hand();
        }
    }

    private boolean check_result(int player_score, int dealer_score, boolean is_end, boolean is_dealers_close) {
        if (player_score >= 21 || dealer_score >= 21) is_end = true;
        if (is_end) {
            if (is_written_smth_after_cards) {
                print_players_card();
                print_dealers_cards(false);
            }
            if (player_score == dealer_score) {
                System.out.println("Draw! " + player_res + ":" + dealer_res);
            } else if ((player_score > dealer_score && player_score <= 21) || dealer_score > 21) {
                player_res++;
                if (player_score == 21) {
                    System.out.print("You got 21! You win! " + player_res + ":" + dealer_res);
                } else {
                    System.out.print("You win! " + player_res + ":" + dealer_res);
                }
            } else {
                dealer_res++;
                if (dealer_score == 21) {
                    System.out.print("Dealer got 21! Dealer wins! " + player_res + ":" + dealer_res);
                } else {
                    System.out.print("Dealer wins! " + player_res + ":" + dealer_res);
                }
            }

            if (player_res > dealer_res) System.out.println(" in your favor");
            else if (player_res < dealer_res) System.out.println(" in dealer`s favor");
            else System.out.print("\n");
            System.out.print("\n\n\n");
            return true;
        } else {
            print_players_card();
            print_dealers_cards(is_dealers_close);
            if (!is_dealers_close) is_written_smth_after_cards = false;
            return false;
        }
    }

    private int player_res = 0, dealer_res = 0;

    private void print_players_card() {
        System.out.print("\tYour cards: ");
        player.print_open_cards();
    }

    private void print_dealers_cards(Boolean is_closed) {
        System.out.print("\tDealer`s cards: ");
        if (is_closed) dealer.print_closed_cards();
        else dealer.print_open_cards();
    }


    private boolean is_written_smth_after_cards = false;
    private int rounds;
    private Deck deck;
    private Hand player, dealer;
}
