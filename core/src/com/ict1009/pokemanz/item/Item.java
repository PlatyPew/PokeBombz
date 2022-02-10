package com.ict1009.pokemanz.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.BoardElement;
import com.ict1009.pokemanz.helper.Destoryable;
import com.ict1009.pokemanz.helper.GameInfo;

public abstract class Item extends Sprite implements Destoryable, BoardElement {
    final private World world;
    final private Body body;
    final private int cost; // Cost of item

    private boolean destroyed = false;
    protected boolean toDestroy = false;

    public Item(World world, String textureLocation, int gridX, int gridY) {
        super(new Texture(textureLocation));
        this.cost = 0;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.world = world;

        this.body = createBody();
    }

    public Item(World world, String textureLocation, int gridX, int gridY, int cost) {
        super(new Texture(textureLocation));
        this.cost = cost;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.world = world;

        this.body = createBody();
    }

    public static Item randomItem(World world, int gridX, int gridY) {
        if ((int)(Math.random() * 100) >= GameInfo.ITEM_SPAWN_CHANCE)
            return null;

        // Chooses a random item
        int choice = (int)(Math.random() * 5);

        switch (choice) {
        case 0:
            return new BombExtra(world, gridX, gridY);
        case 1:
            return new BombKick(world, gridX, gridY);
        case 2:
            return new BombRange(world, gridX, gridY);
        case 3:
            return new BombThrow(world, gridX, gridY);
        case 4:
            return new SpeedUp(world, gridX, gridY);
        default:
            return null;
        }
    }

    public abstract void applyProperty(Player player);

    public int getCost() {
        return this.cost;
    }

    public Body getBody() {
        return body;
    }

    /**
     * Creates a static body with a square shape
     *
     * @return body: Static body
     */
    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(this);
        fixture.setSensor(true);

        shape.dispose();
        return body;
    }

    /**
     * Renders the body of the sprite when body is not destroyed
     *
     * @param batch: The spritebatch of the game
     */
    @Override
    public void render(SpriteBatch batch) {
        if (!destroyed)
            batch.draw(this, this.getX(), this.getY());
    }

    /**
     * Checks if sprite needs to be destroyed
     *
     * @param delta: 1/fps
     */
    @Override
    public void update(float delta) {
        if (toDestroy && !destroyed) {
            this.world.destroyBody(this.body);
            this.destroyed = true;
        }
    }

    @Override
    public boolean getDestroyed() {
        return false;
    }

    @Override
    public void setToDestroy() {}
}
