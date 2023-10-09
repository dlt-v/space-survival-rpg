package com.spacesurvival;

public class Util {
    /**
     * Clears the terminal and resets the cursor to the top.
     */
    public static void cls() {
        System.out.print("\033[H\033[2J");
    }
}
