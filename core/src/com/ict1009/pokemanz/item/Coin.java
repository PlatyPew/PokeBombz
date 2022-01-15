package com.ict1009.pokemanz.item;

public class Coin extends Item {
    final private int value;

    public Coin(String textureLocation, int value) {
        super(textureLocation);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
