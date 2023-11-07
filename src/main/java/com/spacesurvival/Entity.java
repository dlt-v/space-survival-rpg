package com.spacesurvival;

public abstract class Entity {
    private String name = null;
    protected int hitPoints = 1;

    public Entity (String name) {
        this.name = name;
    }

    void regainHealth(int value) {
        hitPoints = Math.min(hitPoints + value, 100);
    }
    void takeDamage(int damage) {
        hitPoints = Math.max(hitPoints - damage, 0);
    }

    String getName() {
        return name;
    }

    int getHitPoints() {
        return hitPoints;
    }

    void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
