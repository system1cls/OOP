package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BlackJackTest {
    @Test
    void game_test_with_1_deck() {
        System.out.print("game_test_with_1_deck\n\n");
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("0 0 0 0 0 0 0".getBytes());
        System.setIn(in);
        BlackJack bj = new BlackJack();
        bj.game();
        System.setIn(sysInBackup);
    }

    @Test
    void game_test_with_10_deck() {
        System.out.print("game_test_with_10_deck\n\n");
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("1 10 0 0 0 0 0 0".getBytes());
        System.setIn(in);
        BlackJack bj = new BlackJack();
        bj.game();
        System.setIn(sysInBackup);
    }

    @Test
    void game_with_one_added_card(){
        System.out.print("game_with_one_added_card\n\n");
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("0 1 0 0 0 0 0 0".getBytes());
        System.setIn(in);
        BlackJack bj = new BlackJack();
        bj.game();
        System.setIn(sysInBackup);
    }

    @Test
    void game_with_two_added_cards(){
        System.out.print("game_with_two_added_cards\n\n");
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("0 1 1 0 0 0 0 0".getBytes());
        System.setIn(in);
        BlackJack bj = new BlackJack();
        bj.game();
        System.setIn(sysInBackup);
    }

}