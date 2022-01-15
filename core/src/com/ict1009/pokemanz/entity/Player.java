package com.ict1009.pokemanz.entity;

public class Player extends Entity {
    private int coin = 0;

    public Player(String name, String textureLocation) {
        super(name, textureLocation);
    }

    public int getCoin() {
        return this.coin;
    }

    public void updateCoin(int coin) {
        this.coin += coin;
    }

}
