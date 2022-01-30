package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Map;
import java.util.ArrayList;

public class Player extends Sprite implements ContactListener {
    final private World world;
    final private Body body;
    final private Map map;
    final private String name;

    private int maxBombs = 3;
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();

    public Player(World world, Map map, String textureLocation, int initialX, int initialY,
                  String name) {
        super(new Texture(textureLocation));
        this.name = name;
        this.world = world;
        this.map = map;
        setPosition((initialX + 1) * GameInfo.PPM, (initialY + 1) * GameInfo.PPM);
        this.body = createBody();
    }

    public Body getBody() {
        return this.body;
    }

    public String getName() {
        return this.name;
    }

    public int getMaxBombs() {
        return maxBombs;
    }

    public void setMaxBombs(int maxBombs) {
        this.maxBombs = maxBombs;
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

        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth() / 2) / GameInfo.PPM - 0.02f);

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
     * Handles bomb placement when spacebar is pressed
     */
    public void handleBomb() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // Snaps the bomb to the grid
            float currX = getX() / GameInfo.PPM;
            float currY = getY() / GameInfo.PPM;
            int bombX = (int)Math.floor(currX);
            int bombY = (int)Math.floor(currY);

            // Snaps the bomb if player passes the 0.5 threshold
            if (currX - bombX < 0.5) {
                bombX -= 1;
            }
            if (currY - bombY < 0.5) {
                bombY -= 1;
            }

            // Place bombs only if player has remaining bombs and bomb does not exist in the spot
            if (bombs.size() < maxBombs && map.getBombMap()[bombX][bombY] == null) {
                Bomb bomb = new Bomb(world, "bomb/bomb1.png", bombX, bombY);
                bombs.add(bomb);
                map.setBombMap(bombX, bombY, bomb); // Places bomb is grid
            }
        }
    }

    /**
     * Renders the body of the player
     *
     * @param batch: The spritebatch of the game
     */
    public void render(SpriteBatch batch) {
        for (Bomb bomb : bombs) {
            bomb.render(batch);
        }

        batch.draw(this, this.getX(), this.getY());
    }

    /**
     * Updates the sprite texture according to where the body is
     *
     * @param delta: 1/fps
     */
    public void update(float delta) {
        handleMovement();
        handleBomb();
        setPosition((body.getPosition().x) * GameInfo.PPM, (body.getPosition().y) * GameInfo.PPM);

        // TODO: Super ugly code please someone help me refractor I too lazy
        ArrayList<Integer> toRemove = new ArrayList<Integer>();
        ArrayList<int[]> toRemoveCoords = new ArrayList<int[]>();

        // Remove bombs from arraylist and bombMap once bomb has exploded
        for (Bomb bomb : bombs) {
            bomb.update(delta);

            if (bomb.getDestroyed()) {
                toRemove.add(bombs.indexOf(bomb));
                int[] coords = {bomb.getInitialX(), bomb.getInitialY()};
                toRemoveCoords.add(coords);
            }
        }

        for (int index : toRemove) {
            bombs.remove(index);
        }
        for (int[] coords : toRemoveCoords) {
            map.setBombMap(coords[0], coords[1], null);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        // TODO Auto-generated method stub
    }

    /**
     * Checks when 2 objects stop collide
     * Currently used for collecting items
     *
     * @param contact: Contact
     */
    @Override
    public void endContact(Contact contact) {
        Object body;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body = contact.getFixtureB().getUserData();
        } else {
            body = contact.getFixtureA().getUserData();
        }

        Bomb detectedBomb;
        if (body instanceof Bomb) {
            detectedBomb = (Bomb)body;
            for (Bomb bomb : bombs) {
                if (bomb == detectedBomb && !bomb.getUpdated()) {
                    bomb.updateBody(false);
                    break;
                }
            }
        }
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
