package com.ict1009.pokemanz.room;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;

public class Obstacle extends Sprite {
    private World world;
    private Body body;

    public Obstacle(World world, String textureLocation, float initalX, float initialY) {
        super(new Texture(textureLocation));
        this.world = world;
        // Set grid positions and fixes weird offsets
        setPosition(initalX * GameInfo.PPM - getWidth() / 2f,
                    initialY * GameInfo.PPM - getHeight() / 2f);
        this.body = createBody();
    }

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2, getHeight() / 2);

        body.createFixture(shape, 1f);
        shape.dispose();
        return body;
    }

    public void updateObstacle() {
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }
}
