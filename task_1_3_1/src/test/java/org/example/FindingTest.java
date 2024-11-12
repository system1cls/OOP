package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FindingTest {
    /*
    @Test
    void Test1() {
        int ans[] = {23, 125, 141};
        assertArrayEquals(Finding.find("input.txt", "love"), ans);
    }
    */
    @Test
    void Test2() {
        int ans[] = {7, 19};
        assertArrayEquals(Finding.find("input2.txt", "aabbaa"), ans);


    }


}