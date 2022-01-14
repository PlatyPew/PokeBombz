package com.ict1009.pokemanz.entity;

public class Enemy {
    final private String name;
    final private int[] position = new int[2];
    final private String texture;

    private int health;

    // TODO: add movement, attack

    public Enemy(String name, int[] position, int health, String texture) {
        this.name = name;
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.health = health;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public int[] getPosition() {
        return position;
    }

    public String getTexture() {
        return texture;
    }

    public int getHealth() {
        return health;
    }

    public void updateHealth(int health) {
        this.health += health;
    }
}
