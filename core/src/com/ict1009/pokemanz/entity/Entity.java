package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Entity extends Sprite {
    final private String name;

    private int health;

    public Entity(String name, String textureLocation) {
        super(new Texture(textureLocation));
        this.name = name;
        this.health = 3;
    }

    public Entity(String name, int health, String textureLocation) {
        super(new Texture(textureLocation));
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public void updateHealth(int health) {
        this.health += health;
    }
}
