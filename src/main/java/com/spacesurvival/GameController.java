package com.spacesurvival;

import java.io.IOException;
import java.util.Scanner;

public class GameController {
    Player player;
    int metal = 0;
    int fuel = 0;
    int oxygen = 100;

    private Scanner scanner = new Scanner(System.in);

    public GameController() throws InterruptedException {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine(); // Scanner goes to next line.
        player = new Player(name);
        System.out.println("Player " + player.getName() + " created.");
        cls(true);
    }

    public void startGame() {
        System.out.println("Game started. Good luck!");
        cls(true);
        printIntro();
    }

    public void printIntro() {
        StringBuilder intro = new StringBuilder();
        intro.append("You are an astronaut sent to an alien planet in order to establish a new base.\n");
        intro.append("During your landing attempt you encountered a critical system error.\n");
        intro.append("An error which caused you to crash.\n\n");
        intro.append("You are now stranded on this alien world.\n");
        intro.append("Your main directive is to survive and, if possible, escape the planet.\n\n");
        System.out.print(intro);
        cls(true);

    }

    public void closeGame() throws InterruptedException {
        System.out.println("Game will now close.");
        cls(true);
        scanner.close();
    }

    private void cls(boolean askAcknowledged) {
        if (askAcknowledged) {
            System.out.print("Acknowledge [ENTER]: ");
            scanner.nextLine();
        }
        try {
            // Clear the terminal for Windows Command Prompt or PowerShell
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cls() {
        cls(false);
    }
}
