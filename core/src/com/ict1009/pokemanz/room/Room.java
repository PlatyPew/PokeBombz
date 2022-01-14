package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.ict1009.pokemanz.entity.Enemy;

public class Room {
    final private Texture texture;
    // final private Enemy[] enemy;

    public Room(String textureLocation) {
        this.texture = new Texture(textureLocation);
    }

    public Texture getTexture() {
        return texture;
    }
}
