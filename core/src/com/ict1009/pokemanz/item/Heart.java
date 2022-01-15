package com.ict1009.pokemanz.item;

public class Heart extends Item {
    final private int health;

    public Heart(int cost, String textureName, int health) {
        super(cost, textureName);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}
