package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.item.Coin;
import com.ict1009.pokemanz.item.Potion;

public class Player extends Sprite implements ContactListener {
    final private World world;
    final private Body body;
    final private String name;

    private int coin = 0;
    private int health = 0;

    public Player(World world, String textureLocation, float initialX, float initialY,
                  String name) {
        super(new Texture(textureLocation));
        this.name = name;
        this.world = world;
        setPosition(initialX * GameInfo.PPM, initialY * GameInfo.PPM);
        this.body = createBody();
    }

    public Body getBody() {
        return this.body;
    }

    public String getName() {
        return this.name;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Creates a dynamic body with a square shape
     *
     * @return body: Dynamic body
     */
    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM - 0.02f,
                       (getHeight() / 2) / GameInfo.PPM - 0.02f);

        Body body = world.createBody(bodyDef);

        body.createFixture(shape, 1f).setUserData(this);
        shape.dispose();
        return body;
    }

    /**
     * Gets keyboard input and moves the character using WASD
     * Also checks that player does not go over edge
     */
    public void handleMovement() {
        float velX = 0, velY = 0;
        float currX = (getBody().getPosition().x) * GameInfo.PPM;
        float currY = (getBody().getPosition().y) * GameInfo.PPM;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && currY < GameInfo.HEIGHT - GameInfo.PPM * 2) {
            velY = GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && currX > GameInfo.PPM) {
            velX = -GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && currY > GameInfo.PPM) {
            velY = -GameInfo.PLAYER_VELOCITY;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) &&
            currX < GameInfo.WIDTH - (GameInfo.WIDTH - GameInfo.PPM * 16)) {
            velX = GameInfo.PLAYER_VELOCITY;
        }

        getBody().setLinearVelocity(velX, velY);
    }

    /**
     * Gets keyboard input with arrow keys and only runs once per press
     *
     * @param batch: The spritebatch of the game
     */
    public void handleAttack(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
        }
    }

    /**
     * Renders the body of the player
     *
     * @param batch: The spritebatch of the game
     */
    public void render(SpriteBatch batch) {
        batch.draw(this, this.getX(), this.getY());
        handleAttack(batch);
    }

    /**
     * Updates the sprite texture according to where the body is
     *
     * @param delta: 1/fps
     */
    public void update(float delta) {
        handleMovement();
        setPosition((body.getPosition().x) * GameInfo.PPM, (body.getPosition().y) * GameInfo.PPM);
    }

    /**
     * Checks when 2 objects collide
     * Currently used for collecting items
     *
     * @param contact: Contact
     */
    @Override
    public void beginContact(Contact contact) {
        Object body;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body = contact.getFixtureB().getUserData();
        } else {
            body = contact.getFixtureA().getUserData();
        }

        // TODO: Make code less disgusting
        if (body instanceof Coin) {
            Coin coin = (Coin)body;
            coin.applyProperty(this);
        } else if (body instanceof Potion) {
            Potion potion = (Potion)body;
            potion.applyProperty(this);
        }
    }

    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub
    }
}
