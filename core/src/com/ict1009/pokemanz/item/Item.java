package com.ict1009.pokemanz.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item {
    final private int cost; // Cost of item
    final private Sprite sprite;

    public Item(String textureName) {
        this.cost = 0;
        this.sprite = new Sprite(new Texture(textureName));
    }

    public Item(int cost, String textureName) {
        this.cost = cost;
        this.sprite = new Sprite(new Texture(textureName));
    }

    public int getCost() {
        return cost;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
