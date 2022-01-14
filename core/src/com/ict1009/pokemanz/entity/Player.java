package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    final private String name;
    final private Sprite sprite;

    private int health = 3;
    private int coin = 0;

    public Player(String name, String textureLocation) {
        this.sprite = new Sprite(new Texture(textureLocation));
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

}
