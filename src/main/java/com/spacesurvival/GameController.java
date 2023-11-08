package com.spacesurvival;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameController {
    Player player;
    int metal = 0;
    int fuel = 0;
    int oxygen = 100;
    private boolean hasReplicator = false;
    private boolean hasGenerator = false;
    private boolean hasMedStation = false;

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
        String intro = """
                You are an astronaut sent to an alien planet in order to establish a new base.
                During your landing attempt you encountered a critical system error.
                An error which caused you to crash.

                You are now stranded on this alien world.
                Your main directive is to survive and, if possible, escape the planet.

                """;
        System.out.print(intro);
        cls(true);

        startMainLoop();
    }

    private void startMainLoop() {

        while(gameRunning) {
            if (hasMedStation && player.getHitPoints() < 100) {
                System.out.println("Your wounds have been healed by the MedStation.");
                player.setHitPoints(100);
            }
            if (hasGenerator && oxygen < 100) {
                System.out.println("Your oxygen tank has been refilled by the oxygen generator.");
                oxygen = 100;
            }
            if (hasReplicator && player.getRangedWeapon().getAmmoCount() < player.getRangedWeapon().getMaxAmmo()) {
                System.out.println("The replicator has refilled the ammunition for " + player.getRangedWeapon().getName() + ".");
                oxygen = 100;
            }


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
                    checkBlueprints();
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
            cls(true);
            initiateCombat(hostilityLevel);
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
                    int blueprint = random.nextInt(1, 5);
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
                        case 4 -> {
                            System.out.println("You found a blueprint for a med station! When manufactured the ship will heal you when coming back from adventure!");
                            player.addMiscItem("MedStation Blueprint", 1);
                        }
                    }
                }
                case 7 -> { // Found a weapon, if already found that one, turn into scrap.

                }
                case 8 -> { // Found a rare weapon.

                }
            }
        } else {
            System.out.println("You are dead! Not big soup-rice.");
            closeGame();
        }
    }
    private void initiateCombat(int hostilityLevel) {
        // Generate enemy
        Enemy enemy = new Enemy(hostilityLevel);
        // Combat loop
        boolean combat = true;
        while (combat) {
            if (player.getHitPoints() <= 0) {
                break;
            }
            System.out.println(player.getName() + " HP[" + player.getHitPoints() + "/100] | " + enemy.getName() + " HP[" + enemy.getHitPoints() + "/" + enemy.getMaxHealth() + ")");
            System.out.println("""
                What do you want to do?
                [1] - Melee attack
                [2] - Ranged attack
                [3] - Heal
                [4] - Run
                Input:\s""");

            int playerChoice = scanner.nextInt();
            short chanceOfBeingHit = 0; // Chance of being hit by the enemy.
            scanner.nextLine();

            switch (playerChoice) {
                case 1 -> { // Melee
                    enemy.takeDamage(Math.max(2, random.nextInt(0, player.getMeleeWeapon().getDamageBonus())));
                    chanceOfBeingHit = 10;
                }
                case 2 -> { // Ranged
                    enemy.takeDamage(Math.max(2, random.nextInt(0, player.getRangedWeapon().getDamageBonus())));
                    chanceOfBeingHit = 5;
                }
                case 3 -> { // Heal
                    if(player.removeMiscItem("Health Pack", 1)) {
                        System.out.println("You heal yourself!");
                    } else {
                        System.out.println("You don't have any health packs!");
                    }
                    chanceOfBeingHit = 2;
                }
                case 4 -> { // Run
                    System.out.println("You attempt to flee!");
                    chanceOfBeingHit = 5;
                    combat = false;
                }
                default -> {

                }
            }

            // Enemy has a chance to hit back if still alive.

            if (random.nextInt(0, 10) <= chanceOfBeingHit || enemy.getHitPoints() > 0) {
                System.out.println(enemy.getName() + " manages to attack you!");
                int damageDealt = random.nextInt(0, enemy.getDamage());
                player.takeDamage(damageDealt);
                System.out.println(enemy.getName() + " deals " + damageDealt + " damage!");
            }

            if (player.getHitPoints() <= 0 || enemy.getHitPoints() <= 0) combat = false;
        }
    }

    private void checkBlueprints() {
        System.out.println("Blueprints: ");
        if (player.getMiscItemQuantity("Escape Ship Blueprint") > 0 && player.getMiscItemQuantity("Ionized Thrusters Blueprint") <= 0) {
            System.out.print("[1] Escape Ship ");
            if (fuel > 50 && metal > 50) {
                System.out.print(" [available]\n");
            } else {
                System.out.print(" [Fuel: " + fuel + "/50, Metal: " + metal + "/50]\n");
            }
        } else if (player.getMiscItemQuantity("Escape Ship Blueprint") > 0 && player.getMiscItemQuantity("Ionized Thrusters Blueprint") > 0) {
            System.out.print("[1] Escape Ship (Ionized thrusters) ");
            if (fuel > 30 && metal > 40) {
                System.out.print(" [available]\n");
            } else {
                System.out.print(" [Fuel: " + fuel + "/30, Metal: " + metal + "/40]\n");
            }
        }
        if (player.getMiscItemQuantity("Oxygen Generator Blueprint") > 0) {
            System.out.print("[2] Oxygen Generator ");
            if (fuel > 10 && metal > 20) {
                System.out.print(" [available]\n");
            } else {
                System.out.print(" [Fuel: " + fuel + "/10, Metal: " + metal + "/20]\n");
            }
        }
        if (player.getMiscItemQuantity("Ammunition Replicator Blueprint") > 0) {
            System.out.print("[3] Ammunition Replicator ");
            if (fuel > 10 && metal > 30) {
                System.out.print(" [available]\n");
            } else {
                System.out.print(" [Fuel: " + fuel + "/10, Metal: " + metal + "/30]\n");
            }
        }
        if (player.getMiscItemQuantity("MedStation Blueprint") > 0) {
            System.out.print("[4] MedStation ");
            if (fuel > 10 && metal > 40) {
                System.out.print(" [available]\n");
            } else {
                System.out.print(" [Fuel: " + fuel + "/10, Metal: " + metal + "/40]\n");
            }
        }
        System.out.print("[0] Nothing yet.");
        System.out.print("What do you want to build? [int]: ");
        int playerChoice = scanner.nextInt();
        scanner.nextLine();

        switch (playerChoice) {
            case 1 -> {
                if (
                    (fuel > 50
                    && metal > 50
                    && player.getMiscItemQuantity("Escape Ship Blueprint") > 0
                    && player.getMiscItemQuantity("Ionized Thrusters Blueprint") <= 0)
                    ||
                    (fuel > 30
                    && metal > 40
                    && player.getMiscItemQuantity("Escape Ship Blueprint") > 0
                    && player.getMiscItemQuantity("Ionized Thrusters Blueprint") > 0))
                    {
                    System.out.println("You manage to build a ship and get out of the planet!\n");
                    closeGame();
                } else {
                    System.out.println("You have insufficient materials to build the ship.");
                }
            }
            case 2 -> { // Oxygen Generator
                if (fuel > 10 && metal > 20 && player.getMiscItemQuantity("Oxygen Generator Blueprint") > 0) {
                    System.out.println("You manage to build an oxygen generator!\nNow upon return to the base your oxygen will be renewed.");
                    player.removeMiscItem("Oxygen Generator Blueprint", 1);
                    hasGenerator = true;
                } else {
                    System.out.println("You have insufficient materials to build the generator.");
                }
            }

            case 3 -> { // Ammunition Replicator
                if (fuel > 10 && metal > 30 && player.getMiscItemQuantity("Ammunition Replicator Blueprint") > 0) {
                    System.out.println("You manage to build an ammunition replicator!\nNow upon return to the base your ammunition for ranged weapon with refill.");
                    player.removeMiscItem("Ammunition Replicator Blueprint", 1);
                    hasReplicator = true;
                } else {
                    System.out.println("You have insufficient materials to build the replicator.");
                }
            }

            case 4 -> { // MedStation
                if (fuel > 10 && metal > 40 && player.getMiscItemQuantity("MedStation Blueprint") > 0) {
                    System.out.println("You manage to build a MedStation!\nNow upon return to the base your health will be regained.");
                    player.removeMiscItem("MedStation Blueprint", 1);
                    hasMedStation = true;
                } else {
                    System.out.println("You have insufficient materials to build the MedStation.");
                }
            }
        }
    }

    public void closeGame() {
        System.out.println("Game will now close.");
        cls(true);
        scanner.close();
        System.exit(0);
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
