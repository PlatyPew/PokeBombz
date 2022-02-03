package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.LevelOne;
import com.ict1009.pokemanz.room.Map;

public class MainScene implements Screen, ContactListener {
    private World world;
    private SpriteBatch batch;

    private Map level1;

    private Array<Player> playerArray = new Array<Player>();

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    public MainScene(GameMain game) {
        setupCamera();
        this.batch = game.getBatch();
        this.level1 = new LevelOne();
        this.world = new World(new Vector2(0, 0), true);

        playerArray.add(new Player(world, level1, 1, "upstill.png", 0, 0, "Platy"));
        playerArray.add(new Player(world, level1, 2, "downstill.png", 15, 9, "Helpme"));

        level1.createObstacles(world);

        this.world.setContactListener(this);
    }

    private void setupCamera() {
        this.box2DCamera = new OrthographicCamera();
        this.box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                                    GameInfo.HEIGHT / GameInfo.PPM);
        this.box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    public void update(float delta) {
        for (Player player : playerArray) {
            player.update(delta);
        }
        level1.update(delta);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();

        batch.draw(level1.getTexture(), 0, 0);
        for (Player player : playerArray) {
            player.render(batch);
        }
        level1.render(batch);
        batch.end();

        debugRenderer.render(world, box2DCamera.combined);

        world.step(delta, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() instanceof Player &&
            contact.getFixtureB().getUserData() instanceof Player) {
            Player playerA = (Player)contact.getFixtureA().getUserData();
            Player playerB = (Player)contact.getFixtureB().getUserData();
            Vector2 playerACoords = playerA.getBody().getPosition();
            Vector2 playerBCoords = playerB.getBody().getPosition();

            float xCoords = playerACoords.x - playerBCoords.x;
            float yCoords = playerACoords.y - playerBCoords.y;
            if (xCoords <= -0.5) {
                playerA.disableRight();
                playerB.disableLeft();
            } else if (xCoords >= 0.5) {
                playerA.disableLeft();
                playerB.disableRight();
            } else if (yCoords <= -0.5) {
                playerA.disableUp();
                playerB.disableDown();
            } else if (yCoords >= 0.5) {
                playerA.disableDown();
                playerB.disableUp();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // Update bomb body
        Object body1;
        Object body2;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body1 = contact.getFixtureB().getUserData();
            body2 = contact.getFixtureA().getUserData();
        } else {
            body1 = contact.getFixtureA().getUserData();
            body2 = contact.getFixtureB().getUserData();
        }
        if (body1 instanceof Bomb && body2 instanceof Player) {
            Player player = (Player)body2;
            player.bombTangible((Bomb)body1);
        }

        // Set move to true
        if (contact.getFixtureA().getUserData() instanceof Player &&
            contact.getFixtureB().getUserData() instanceof Player) {
            Player playerA = (Player)contact.getFixtureA().getUserData();
            Player playerB = (Player)contact.getFixtureB().getUserData();

            playerA.enableAll();
            playerB.enableAll();
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
