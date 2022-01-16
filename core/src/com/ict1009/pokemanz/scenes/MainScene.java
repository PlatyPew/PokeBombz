package com.ict1009.pokemanz.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Obstacle;

public class MainScene implements Screen {
    private World world;
    private GameMain game;
    private Texture background;
    private Obstacle obstacle;
    private Player player;

    public MainScene(GameMain game) {
        this.game = game;
        this.background = new Texture("room/background.jpg");
        this.world = new World(new Vector2(0, 0), true);
        this.obstacle = new Obstacle(world, "room/rock.png", GameInfo.RATIO_WIDTH / 2,
                                     GameInfo.RATIO_HEIGHT / 2);
        this.player = new Player(world, "player/player1.png", "Platy");
    }

    public void update(float delta) {
        player.detectInput(delta);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    @Override
    public void render(float delta) {
        update(delta);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);
        game.getBatch().draw(obstacle, obstacle.getX(), obstacle.getY());
        game.getBatch().draw(player, player.getX(), player.getY());
        game.getBatch().end();

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
