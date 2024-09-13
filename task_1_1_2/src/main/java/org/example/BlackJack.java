package org.example;

import java.util.Scanner;

/**
 * Class for game.
 */
public class BlackJack {

    /**
     * Start game.
     */
    public void game() {
        rounds = 1;
        deck = new Deck();
        player = new Hand();
        dealer = new Hand();
        playerRes = 0;
        dealerRes = 0;
        boolean readyToContinue = true;
        int choice;
        boolean isNewRound;
        Card card;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!\n");
        isWrittenSmthAfterCards = true;

        while (readyToContinue) {

            System.out.println("Round " + rounds + '\n');
            rounds++;
            System.out.println("Enter \"1\" if you want to enter the number of decks in the game. "
                    + " Enter \"0\", if you do not want, then there will be one deck in the game");
            choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("Enter the number of decks from 1 to 10");
                choice = scanner.nextInt();
                if (choice > 10) {
                    choice = 10;
                }
                if (choice <= 0) {
                    choice = 1;
                }
                deck.make_new_deck(choice);
            } else {
                deck.make_new_deck(1);
            }

            player.add_card(deck.get_card());
            dealer.add_card(deck.get_card());
            player.add_card(deck.get_card());
            dealer.add_card(deck.get_card());

            System.out.println("The dealer has dealt the cards!");

            isNewRound = check_result(player.get_score(), dealer.get_score(), false, true);

            if (!isNewRound) {
                choice = 1;
                System.out.println("Your turn!");
                System.out.println("__________");

                while (choice == 1 && !isNewRound) {
                    System.out.print("Enter \"1\" to take the card, and \"0\" to stop.\n");
                    isWrittenSmthAfterCards = true;
                    choice = scanner.nextInt();

                    if (choice == 1) {
                        player.add_card(deck.get_card());
                        card = player.get_last();
                        System.out.print("You opened: ");
                        isWrittenSmthAfterCards = true;
                        player.print_card(card);
                        System.out.print("\n\n");

                        isNewRound = check_result(player.get_score(), dealer.get_score(),
                                false, true);
                    }

                }
            }


            if (!isNewRound) {
                System.out.println("Dealer`s turn!");
                System.out.println("______________");

                System.out.print("The dealer opens a closed card\n");
                isWrittenSmthAfterCards = true;
                isNewRound = check_result(player.get_score(), dealer.get_score(),
                        false, false);

                while (dealer.get_score() < 17 && !isNewRound) {
                    dealer.add_card(deck.get_card());
                    card = dealer.get_last();
                    System.out.print("Dealer opened: ");
                    isWrittenSmthAfterCards = true;
                    dealer.print_card(card);
                    System.out.print("\n\n");

                    isNewRound = check_result(player.get_score(), dealer.get_score(),
                            false, false);
                }
            }


            if (!isNewRound) {
                check_result(player.get_score(), dealer.get_score(), true, false);
            }

            System.out.println("Enter \"1\" if you want to start new round. " +
                    " Enter \"0\", if you do not want it");
            isWrittenSmthAfterCards = true;
            choice = scanner.nextInt();
            if (choice == 0) readyToContinue = false;

            deck.clear_deck();
            player.clear_hand();
            dealer.clear_hand();
        }
    }

    /**
     * Check should round be ended and print results.
     *
     * @param playerScore    - total value of player`s deck
     * @param dealerScore    - total value of dealer`s deck
     * @param isEnd          - is round ended
     * @param isDealersClose - is dealer`s cards closed
     * @return is round ended
     */
    private boolean check_result(int playerScore, int dealerScore,
                                 boolean isEnd, boolean isDealersClose) {
        if (playerScore >= 21 || dealerScore >= 21) {
            isEnd = true;
        }
        if (isEnd) {
            if (isWrittenSmthAfterCards) {
                print_players_card();
                print_dealers_cards(false);
            }
            if (playerScore == dealerScore) {
                System.out.println("Draw! " + playerRes + ":" + dealerRes);
            } else if ((playerScore > dealerScore && playerScore <= 21) || dealerScore > 21) {
                playerRes++;
                if (playerScore == 21) {
                    System.out.print("You got 21! You win! " + playerRes + ":" + dealerRes);
                } else {
                    System.out.print("You win! " + playerRes + ":" + dealerRes);
                }
            } else {
                dealerRes++;
                if (dealerScore == 21) {
                    System.out.print("Dealer got 21! Dealer wins! "
                            + playerRes + ":" + dealerRes);
                } else {
                    System.out.print("Dealer wins! " + playerRes + ":" + dealerRes);
                }
            }

            if (playerRes > dealerRes) {
                System.out.println(" in your favor");
            } else if (playerRes < dealerRes) {
                System.out.println(" in dealer`s favor");
            } else {
                System.out.print("\n");
            }
            System.out.print("\n\n\n");
            return true;
        } else {
            print_players_card();
            print_dealers_cards(isDealersClose);
            if (!isDealersClose) {
                isWrittenSmthAfterCards = false;
            }
            return false;
        }
    }

    private int playerRes = 0;
    private int dealerRes = 0;

    /**
     * Print player`s card.
     */
    private void print_players_card() {
        System.out.print("\tYour cards: ");
        player.print_open_cards();
    }

    /**
     * Print dealer`s card
     *
     * @param isClosed - are dealer`s cards closed
     */
    private void print_dealers_cards(Boolean isClosed) {
        System.out.print("\tDealer`s cards: ");
        if (isClosed) {
            dealer.print_closed_cards();
        } else {
            dealer.print_open_cards();
        }
    }


    private boolean isWrittenSmthAfterCards = false;
    private int rounds;
    private Deck deck;
    private Hand player;
    private Hand dealer;
}
