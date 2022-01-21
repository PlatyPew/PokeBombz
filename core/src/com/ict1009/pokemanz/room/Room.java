package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;

public class Room {
    final private Texture texture;

    public Room(String textureLocation) {
        this.texture = new Texture(textureLocation);
    }

    public Texture getTexture() {
        return this.texture;
    }
}
