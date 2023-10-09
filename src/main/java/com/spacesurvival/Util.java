package com.spacesurvival;

import java.io.IOException;

public class Util {
    /**
     * Clears the terminal and resets the cursor to the top.
     */
    public static void cls() {
        try {
            // Clear the terminal for Windows Command Prompt or PowerShell
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
