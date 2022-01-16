package com.ict1009.pokemanz.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Item extends Sprite {
    final private int cost; // Cost of item

    public Item(World world, String textureLocation, float initialX, float initialY) {
        super(new Texture(textureLocation));
        this.cost = 0;
        setPosition(initialX * GameInfo.PPM, initialY * GameInfo.PPM);
    }

    public Item(World world, String textureLocation, float initialX, float initialY, int cost) {
        super(new Texture(textureLocation));
        this.cost = cost;
        setPosition(initialX * GameInfo.PPM, initialY * GameInfo.PPM);
    }

    public int getCost() {
        return this.cost;
    }
}
