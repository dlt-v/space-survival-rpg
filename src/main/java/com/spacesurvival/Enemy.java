package com.spacesurvival;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    private static ArrayList<String> enemyNames = new ArrayList<String>(){
        {
            add("Robot");
            add("Bounty Hunter");
            add("Drone");
            add("Alien");
            add("Space Worm");
        }
    };
    private static Random random = new Random();

    private int damage;
    private int maxHealth;
    public Enemy(int hostilityLevel) {
        super(enemyNames.get(random.nextInt(0, enemyNames.size())));
        // Set hit points according to hostilityLevel
        this.setHitPoints(random.nextInt(10, 20) + hostilityLevel);
        this.maxHealth = hitPoints;
        damage = random.nextInt(5, 10) + hostilityLevel;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
