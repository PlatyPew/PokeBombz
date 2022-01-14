package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Obstacle {
    final private Sprite sprite;
    final private int[] position = new int[2];
    final private BodyDef bodyDef = new BodyDef();

    public Obstacle(int[] position, String textureLocation) {
        this.position[0] = position[0];
        this.position[1] = position[1];
        this.sprite = new Sprite(new Texture(textureLocation));
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int[] getPosition() {
        return position;
    }
}
