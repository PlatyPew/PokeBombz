package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class Obstacle extends Sprite {
    final private int[] initialPos = new int[2];
    final private BodyDef bodyDef = new BodyDef();

    public Obstacle(int[] initialPos, String textureLocation) {
        super(new Texture(textureLocation));
        this.initialPos[0] = initialPos[0];
        this.initialPos[1] = initialPos[1];
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
    }

    public int getInitialX() {
        return this.initialPos[0];
    }

    public int getInitialY() {
        return this.initialPos[1];
    }
}
