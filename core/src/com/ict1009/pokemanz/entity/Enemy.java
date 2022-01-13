package com.ict1009.pokemanz.entity;

public class Enemy {
    final private String name;
    final private int[] position = new int[2];
    final private int totalPhases;
    final private String texture;

    private int health;
    private int currentPhase;

    // TODO: add movement, attack

    public Enemy(String name, int[] position, int health, int totalPhases, String texture) {
        this.name = name;
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.health = health;
        this.totalPhases = totalPhases;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public int[] getPosition() {
        return position;
    }

    public int getTotalPhases() {
        return totalPhases;
    }

    public String getTexture() {
        return texture;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }
}
