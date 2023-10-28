package com.spacesurvival;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameController {
    Player player;
    int metal = 0;
    int fuel = 0;
    int oxygen = 100;

    boolean gameRunning = true;

    private Scanner scanner = new Scanner(System.in);
    private Random random = new Random();

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

        startMainLoop();
    }

    private void startMainLoop() {

        while(gameRunning) {
            String mainLoopOptions = "What would you like to do:\n" +
                    "[1] - Check your equipment.\n" +
                    "[2] - Go outside and look for resources.\n" +
                    "[3] - Check your blueprints.\n" +
                    "[4] - Rest and regenerate health.\n" +
                    "[0] - Exit game.\n" +
                    "Enter option number: ";
            System.out.print(mainLoopOptions);
            int playerChoice;
            try {
                playerChoice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e + "\nTry again.");
                continue;
            } finally {
                scanner.nextLine();
            }

            switch(playerChoice) {
                case 1 -> {
                    System.out.println("Checking equipment...");
                    player.checkEquipment();
                }
                case 2 -> {
                    System.out.println("Looking for resources...");
                    lookForResources();
                }
                case 3 -> {
                    System.out.println("Checking blueprints...");
                }
                case 4 -> {
                    System.out.println("Resting...");
                }
                case 0 -> {
                    System.out.println("Stopping game...");
                    gameRunning = false;
                }
                default -> {
                    System.out.println("Invalid choice. Try again.");
                }

            }
            cls(true);
        }


    }

    private void lookForResources() {
        boolean checkedMap;
        do {
            checkedMap = false;
            System.out.println("""
                Where do you want to go?
                [1] - The Crater
                [2] - The Mountain
                [3] - Wastelands
                [4] - Old Colony
                [9] - Check map
                [0] - Exit
                Input:\s""");

            int playerChoice = scanner.nextInt();
            scanner.nextLine();

            switch (playerChoice) {
                case 1 -> {
                    oxygen -= 10;
                    System.out.println("You go to the crater.\nYou use 10% of your oxygen. (O2: " + oxygen + "/100)");
                    // TODO: rollForAction()
                }
                case 2 -> {
                    oxygen -= 15;
                    System.out.println("You climb up the mountain!\nYou use 15% of your oxygen. (O2: " + oxygen + "/100)");
                }
                case 3 -> {
                    oxygen -= 5;
                    System.out.println("You walk towards the wastelands!\nYou use 5% of your oxygen. (O2: " + oxygen + "/100)");
                }
                case 4 -> {
                    oxygen -= 5;
                    System.out.println("You decide to visit the old colony.\nYou use 5% of your oxygen. (O2: " + oxygen + "/100)");
                }
                case 9 -> {
                    System.out.println("""
                            You're checking your map...
                            The Crater - An interesting and well known research place.
                            Devoid of any life but requires a bit of stamina and oxygen to search through.
                            The Mountain - Rich in metal ores though also hard to climb.
                            No data about alien lifeforms.
                            Wastelands - Unforgiving lunar desert.
                            Countless wrecks litter the fields, attracting all sorts of individuals.
                            Old Colony - Abandoned research site.
                            The most contested area in the region though also very rewarding.""");
                    checkedMap = true;
                }
                case 0 -> {
                    System.out.println("You changed your mind, you return to the base.");
                    return;
                }
            }
        } while (checkedMap);

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
