package com.ict1009.pokemanz.item;

public class Item {
    final private int cost; // Cost of item
    final private String name; // Name of item
    final private String texture; // Texture of item
    // TODO: add item type

    public Item(int cost, String name, String texture) {
        this.cost = cost;
        this.name = name;
        this.texture = texture;
    }

    public int getCost() {
        return cost;
    }
    public String getName() {
        return name;
    }
    public String getTexture() {
        return texture;
    }
}
