package com.spacesurvival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Entity {

    private List<Weapon> weaponList = new ArrayList<>();
    private Map<String, Integer> miscItems;
    private final int maxHitPoints = 100;

    private Weapon meleeWeapon;
    private Weapon rangedWeapon;

    public Player(String name) {
        super(name);
        hitPoints = 100;
        miscItems = new HashMap<>();
        meleeWeapon = new Weapon("Wrench", 15);
        addWeapon(meleeWeapon);
        rangedWeapon = new Weapon("Nail-gun", 10, 10, 20);
        addWeapon(rangedWeapon);
        addMiscItem("Escape Ship Blueprint", 1);
    }

    public void checkEquipment() {
        StringBuilder result = new StringBuilder();
        result.append(getName()).append(" | HP: ").append(getHitPoints()).append("/").append(maxHitPoints).append("\n\n");
        result.append("Weapons:\n");
        for (Weapon weapon : weaponList) {
            // 1. Nail-gun (+5 DMG) [24/30]
            result.append(weaponList.indexOf(weapon) + 1).append(". ").append(weapon.getName()).append(" (+").append(weapon.getDamageBonus()).append(" DMG)");

            if (weapon.getAmmoCount() != -1) {
                result.append(" [").append(weapon.getAmmoCount()).append("/").append(weapon.getMaxAmmo()).append("]\n");
            } else {
                result.append("\n");
            }
        }

        result.append("\nItems:\n");
        for (String item : miscItems.keySet()) {
            result.append(item).append(": ").append(getMiscItemQuantity(item)).append("\n");
        }

        System.out.println(result);
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }

    public void addMiscItem(String item, int quantity) {
        // If the item already exists, increment its quantity.
        miscItems.merge(item, quantity, Integer::sum);
    }

    public boolean removeMiscItem(String item, int quantity) {
        if (miscItems.containsKey(item) && miscItems.get(item) >= quantity) {
            miscItems.put(item, miscItems.get(item) - quantity);
            // Remove the item from the inventory if the quantity becomes 0.
            if (miscItems.get(item) == 0) {
                miscItems.remove(item);
            }
            return true;
        } else {
            System.out.println("Not enough " + item + " in inventory or item does not exist.");
            return false;
        }
    }

    // Method to get the quantity of an item in the inventory.
    public int getMiscItemQuantity(String item) {
        return miscItems.getOrDefault(item, 0);
    }

    public Weapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public Weapon getRangedWeapon() {
        return rangedWeapon;
    }
}
