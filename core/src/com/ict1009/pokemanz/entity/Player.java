package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Map;
import java.util.ArrayList;

public class Player extends Sprite implements ContactListener {
    final private World world;
    final private Body body;
    final private Map map;
    final private String name;
    final private int playerNumber;

    final private TextureAtlas playerAtlasSide;
    final private TextureAtlas playerAtlasDown;
    final private TextureAtlas playerAtlasUp;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private float elapsedTime;

    private boolean isWalking = false;
    private Texture texture;

    private int maxBombs = 3;
    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();

    public Player(World world, Map map, int playerNumber, String textureLocation, int gridX,
                  int gridY, String name) {
        super(new Texture(String.format("player/%d/%s", playerNumber, textureLocation)));
        this.name = name;
        this.world = world;
        this.map = map;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.body = createBody();

        this.playerNumber = playerNumber;
        this.texture = new Texture(String.format("player/%d/%s", playerNumber, textureLocation));
        this.playerAtlasSide =
            new TextureAtlas(String.format("player/%d/left.atlas", playerNumber));
        this.playerAtlasDown =
            new TextureAtlas(String.format("player/%d/down.atlas", playerNumber));
        this.playerAtlasUp = new TextureAtlas(String.format("player/%d/up.atlas", playerNumber));
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
        isWalking = false;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && currY < GameInfo.HEIGHT - GameInfo.PPM * 2) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasUp.getRegions());
            texture = new Texture(String.format("player/%d/upstill.png", playerNumber));
            velY = GameInfo.PLAYER_VELOCITY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A) && currX > GameInfo.PPM) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasSide.getRegions());
            texture = new Texture(String.format("player/%d/leftstill.png", playerNumber));
            velX = -GameInfo.PLAYER_VELOCITY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && currY > GameInfo.PPM) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasDown.getRegions());
            texture = new Texture(String.format("player/%d/downstill.png", playerNumber));
            velY = -GameInfo.PLAYER_VELOCITY;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) &&
                   currX < GameInfo.WIDTH - (GameInfo.WIDTH - GameInfo.PPM * 16)) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasSide.getRegions());
            texture = new Texture(String.format("player/%d/rightstill.png", playerNumber));
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

        if (isWalking) {
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlasSide.getRegions();

            for (TextureRegion frame : frames) {
                if (body.getLinearVelocity().x < 0 && frame.isFlipX()) {
                    frame.flip(true, false);
                } else if (body.getLinearVelocity().x > 0 && !frame.isFlipX()) {
                    frame.flip(true, false);
                }
            }

            batch.draw((TextureRegion)animation.getKeyFrame(elapsedTime, true), this.getX(),
                       this.getY());
        } else {
            batch.draw(texture, this.getX(), this.getY());
        }
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
                int[] coords = {bomb.getGridX(), bomb.getGridY()};
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
