package com.ict1009.pokemanz.item;

public class Heart extends Item {
    final private int health;

    public Heart(int cost, String textureLocation, int health) {
        super(cost, textureLocation);
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }
}
