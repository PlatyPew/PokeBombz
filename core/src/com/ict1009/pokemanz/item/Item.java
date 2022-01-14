package com.ict1009.pokemanz.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {
    final private int cost;    // Cost of item
    final private String name; // Name of item
    final private Sprite sprite;
    // TODO: add item type

    public Item(int cost, String name, String textureName) {
        this.cost = cost;
        this.name = name;
        this.sprite = new Sprite(new Texture(textureName));
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
