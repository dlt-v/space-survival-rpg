package com.spacesurvival;

import java.util.Scanner;

public class GameController {
    Player player;
    int metal = 0;
    int fuel = 0;
    int oxygen = 100;

    public GameController() throws InterruptedException {
        System.out.print("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine(); // Scanner goes to next line.
        player = new Player(name);
        System.out.println("Player " + player.getName() + " created.");
        Thread.sleep(500);
    }

    public void startGame() {
        System.out.print("Game started. Good luck!");
        Util.cls();

    }
}
