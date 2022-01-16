package com.ict1009.pokemanz.attack;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Attack extends Sprite {
    final private int damage;

    public Attack(String textureLocation, int damage) {
        super(new Texture(textureLocation));
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
