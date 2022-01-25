package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Room;

public class MainScene implements Screen {
    private World world;
    private SpriteBatch batch;

    private Room room;

    private Player player;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    public MainScene(GameMain game) {
        setupCamera();
        this.batch = game.getBatch();
        room = new Room("room/background.png");
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(world, "player/player1downstill.png", 6, 6, "Platy");
        this.world.setContactListener(this.player);
        room.makeRoom(world);
    }

    private void setupCamera() {
        this.box2DCamera = new OrthographicCamera();
        this.box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                                    GameInfo.HEIGHT / GameInfo.PPM);
        this.box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    public void update(float delta) {
        player.update(delta);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();

        batch.draw(room.getTexture(), 0, 0);
        player.render(batch);
        room.render(batch);
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
}
