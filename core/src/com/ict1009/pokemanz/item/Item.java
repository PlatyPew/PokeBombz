package com.ict1009.pokemanz.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Item extends Sprite {
    final private int cost; // Cost of item

    public Item(String textureLocation) {
        super(new Texture(textureLocation));
        this.cost = 0;
    }

    public Item(int cost, String textureLocation) {
        super(new Texture(textureLocation));
        this.cost = cost;
    }

    public int getCost() {
        return this.cost;
    }
}
