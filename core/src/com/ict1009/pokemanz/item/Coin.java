package com.ict1009.pokemanz.item;

public class Coin extends Item {
    final private int value;

    public Coin(String textureName, int value) {
        super(textureName);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
