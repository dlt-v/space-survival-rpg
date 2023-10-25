package com.spacesurvival;

public class Weapon {

    private final String name;
    private final int damageBonus;
    private int ammo;
    private final int maxAmmo;

    public Weapon(String name, int damageBonus, int ammo, int maxAmmo) {
        this.name = name;
        this.damageBonus = damageBonus;
        this.ammo = ammo;
        this.maxAmmo = maxAmmo;
    }

    public Weapon(String name, int damageBonus) {
        this.name = name;
        this.damageBonus = damageBonus;
        this.ammo = -1;
        this.maxAmmo = 0;
    }

    public String getName() {
        return name;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getAmmoCount() {
        return ammo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }
}
