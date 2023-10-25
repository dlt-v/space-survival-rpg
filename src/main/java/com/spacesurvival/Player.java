package com.spacesurvival;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {

    List<Weapon> weaponList = new ArrayList<>();
    private final int maxHitPoints = 100;

    public Player(String name) {
        super(name);
        hitPoints = 100;
        addWeapon(new Weapon("Wrench", 3));
        addWeapon(new Weapon("Nail-gun", 1, 10, 20));
    }

    public void checkEquipment() {
        StringBuilder result = new StringBuilder();
        result.append(getName()).append(" | HP: ").append(getHitPoints()).append("/").append(maxHitPoints).append("\n\n");
        result.append("Weapons:\n");
        for (Weapon weapon : weaponList) {
            // 1. Nail-gun (+5 DMG) [24/30]
            result.append(
                    weaponList.indexOf(weapon) + 1
                            + ". "
                            + weapon.getName()
                            + " (+"
                            + weapon.getDamageBonus()
                            + " DMG)");

            if (weapon.getAmmoCount() != -1) {
                result.append(" [" + weapon.getAmmoCount() + "/" + weapon.getMaxAmmo() + "]\n");
            } else {
                result.append("\n");
            }
        }

        System.out.println(result);
    }

    public void addWeapon(Weapon weapon) {
        weaponList.add(weapon);
    }
}
