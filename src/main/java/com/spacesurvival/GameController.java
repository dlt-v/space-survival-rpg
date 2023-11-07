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
                    explore(1);
                }
                case 2 -> {
                    oxygen -= 15;
                    System.out.println("You climb up the mountain!\nYou use 15% of your oxygen. (O2: " + oxygen + "/100)");
                    explore(3);
                }
                case 3 -> {
                    oxygen -= 5;
                    System.out.println("You walk towards the wastelands!\nYou use 5% of your oxygen. (O2: " + oxygen + "/100)");
                    explore(5);
                }
                case 4 -> {
                    oxygen -= 5;
                    System.out.println("You decide to visit the old colony.\nYou use 5% of your oxygen. (O2: " + oxygen + "/100)");
                    explore(8);
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
                    cls(true);
                }
                case 0 -> {
                    System.out.println("You changed your mind, you return to the base.");
                    return;
                }
            }
        } while (checkedMap);

    }

    private void explore(int hostilityLevel) {
        // Prizes should scale with hostility level as well.
        // Higher level (1 - 8) means higher chance of encountering an enemy.
        int chanceOfEncounter = 10; // Base chance is 10%.
        chanceOfEncounter += (hostilityLevel - 1) * 9;

        int encounterCheck = random.nextInt(100) + 1;

        if (encounterCheck <= chanceOfEncounter) {
            System.out.println("You have encountered an enemy!");
            initiateCombat();
        } else {
            System.out.println("You managed to not engage in combat this time.");
        }

        // If player is still alive they can find something cool.
        // Some loot can only be found in the higher level zones.

        if (player.getHitPoints() > 0) {
            int lootCheck = random.nextInt(hostilityLevel) + 1;
            switch (lootCheck) {
                case 1 -> { // Found oxygen.
                    System.out.println("You found a full oxygen tank, you refilled your oxygen.");
                    oxygen = 100;
                }
                case 2 -> { // Found fuel.
                    fuel += 2 + hostilityLevel;
                    System.out.println("You found a dusty fuel canister. You now have " + fuel + " liters of fuel.");
                }
                case 3 -> { // Found metal.
                    metal += 5 + hostilityLevel;
                    System.out.println("You found some scrap. You now have " + metal + " units of metal.");
                }
                case 4 -> { // Found a health pack - useful in combat.
                    player.addMiscItem("Health Pack", 1);
                    System.out.println("You found a still useful health pack! You now have " + player.getMiscItemQuantity("health pack") + " health pack(s).");
                }
                case 5 -> { // Found more metal.
                    metal += 10 + hostilityLevel;
                    System.out.println("You found a some good looking parts. You now have " + metal + " units of metal.");
                }
                case 6 -> { // Found blueprint, if already found it, turn into scrap.
                    int blueprint = random.nextInt(1, 4);
                    switch (blueprint) {
                        case 1 -> {
                            System.out.println("You found a blueprint for oxygen generator!");
                            player.addMiscItem("Oxygen Generator Blueprint", 1);
                        }
                        case 2 -> {
                            System.out.println("You found a blueprint for an ammunition replicator!");
                            player.addMiscItem("Ammunition Replicator Blueprint", 1);
                        }
                        case 3 -> {
                            System.out.println("You found a blueprint for ionized thrusters! When manufactured the ship will require less fuel to be used!");
                            player.addMiscItem("Ionized Thrusters Blueprint", 1);
                        }
                    }
                }
                case 7 -> { // Found a weapon, if already found that one, turn into scrap.

                }
                case 8 -> { // Found a rare weapon.

                }
            }
        }
    }
    private void initiateCombat() {
        // Generate enemy

        // Combat loop
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
