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
import com.ict1009.pokemanz.GameMain;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.bomb.Explode;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.BoardInfo;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.huds.MainHud;
import com.ict1009.pokemanz.item.Item;
import com.ict1009.pokemanz.room.LevelOne;
import com.ict1009.pokemanz.room.LevelTwo;
import com.ict1009.pokemanz.room.LevelThree;
import com.ict1009.pokemanz.room.Map;

import java.util.ArrayList;

public class MainScene implements Screen, ContactListener {
    private World world;
    private SpriteBatch batch;
    private GameMain game;

    private MainHud hud;

    private Map level;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    private boolean gameOver = false;
    private Player winner;

    public MainScene(GameMain game, int numPlayers, int numLevel) {
        setupCamera();
        this.game = game;
        this.batch = game.getBatch();
        switch(numLevel){
            case 1:
                this.level = new LevelOne();
                break;
            case 2:
                this.level = new LevelTwo();
                break;
            case 3:
                this.level = new LevelThree();
                break;
            default:
                this.level = new LevelOne();
                break;
        }

        this.level.setGameMusic();
        this.world = new World(new Vector2(0, 0), true);

        BoardInfo.players.add(new Player(world, level, 1, "upstill.png", 0, 0, "Platy"));
        BoardInfo.players.add(new Player(world, level, 2, "downstill.png", 15, 9, "Helpme"));

        if (numPlayers >= 3) {
            BoardInfo.players.add(new Player(world, level, 3, "upstill.png", 15, 0, "Saveme"));
        }

        if (numPlayers >= 4) {
            BoardInfo.players.add(new Player(world, level, 4, "downstill.png", 0, 9, "Iamdie"));
        }

        this.hud = new MainHud(game, BoardInfo.players.size());

        level.createObstacles(world);

        this.world.setContactListener(this);
    }

    private void setupCamera() {
        this.box2DCamera = new OrthographicCamera();
        this.box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                                    GameInfo.HEIGHT / GameInfo.PPM);
        this.box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        this.debugRenderer = new Box2DDebugRenderer();
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        if (gameOver)
            return winner;
        else
            return null;
    }

    private void checkScore(float delta) {
        int alive = 0;

        for (Player player : BoardInfo.players) {
            player.update(delta);
            if (!player.getDestroyed())
                alive++;
        }

        int largest = 0;
        int largestIndex = 0;
        for (int i = 0; i < BoardInfo.playerScore.length; i++) {
            if (BoardInfo.playerScore[i] > largest) {
                largestIndex = i;
                largest = BoardInfo.playerScore[i];
            }
        }

        winner = BoardInfo.players.get(largestIndex);

        if (alive <= 1)
            gameOver = true;
    }

    public void update(float delta) {
        checkScore(delta);
        level.update(delta);

        GameInfo.timeElapsed += 1;

        hud.updateTime();

        if (getWinner() != null)
            game.setScreen(new EndScene(game, getWinner()));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        update(delta);

        batch.begin();

        batch.draw(level.getTexture(), 0, 0);
        level.render(batch);
        for (Player player : BoardInfo.players) {
            player.render(batch);
        }
        batch.end();

        debugRenderer.render(world, box2DCamera.combined);

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        world.step(delta, 6, 2);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    private void preventPlayerPush(Contact contact) {
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

    private void enablePlayerMovement(Contact contact) {
        // Set move to true
        if (contact.getFixtureA().getUserData() instanceof Player &&
            contact.getFixtureB().getUserData() instanceof Player) {
            Player playerA = (Player)contact.getFixtureA().getUserData();
            Player playerB = (Player)contact.getFixtureB().getUserData();

            playerA.enableAll();
            playerB.enableAll();
        }
    }

    private void detectPlayerItem(Contact contact) {
        Object body1;
        Object body2;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body1 = contact.getFixtureB().getUserData();
            body2 = contact.getFixtureA().getUserData();
        } else {
            body1 = contact.getFixtureA().getUserData();
            body2 = contact.getFixtureB().getUserData();
        }

        if (body1 instanceof Item && body2 instanceof Player) {
            Item item = (Item)body1;
            Player player = (Player)body2;
            item.applyProperty(player);
        }
    }

    private void detectPlayerExplode(Contact contact) {
        Object body1;
        Object body2;

        if (contact.getFixtureA().getUserData() instanceof Player) {
            body1 = contact.getFixtureB().getUserData();
            body2 = contact.getFixtureA().getUserData();
        } else {
            body1 = contact.getFixtureA().getUserData();
            body2 = contact.getFixtureB().getUserData();
        }

        if (body1 instanceof Explode && body2 instanceof Player) {
            Explode explosion = (Explode)body1;
            Player player = (Player)body2;

            if (!BoardInfo.explosionIDs.contains(explosion.getUUID())) {
                if (explosion.getPlayerNumber() != player.getPlayerNumber()) {
                    BoardInfo.playerScore[player.getPlayerNumber() - 1] -= 1;
                    BoardInfo.playerScore[explosion.getPlayerNumber() - 1] += 1;

                    hud.updateScore(player.getPlayerNumber(),
                                    BoardInfo.playerScore[player.getPlayerNumber() - 1]);

                    hud.updateScore(explosion.getPlayerNumber(),
                                    BoardInfo.playerScore[explosion.getPlayerNumber() - 1]);
                } else {
                    BoardInfo.playerScore[player.getPlayerNumber() - 1] -= 1;
                    hud.updateScore(player.getPlayerNumber(),
                                    BoardInfo.playerScore[player.getPlayerNumber() - 1]);
                }

                BoardInfo.explosionIDs.add(explosion.getUUID());

                player.setToDestroy();
            }
        }
    }

    @Override
    public void beginContact(Contact contact) {
        preventPlayerPush(contact);
        detectPlayerExplode(contact);
        detectPlayerItem(contact);
    }

    private void updateBombTangible(Contact contact) {
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
            boolean lock = false;
            Bomb bomb = (Bomb)body1;

            for (Player player : BoardInfo.players) {
                float playerGridX = player.getBody().getPosition().x - 1;
                float playerGridY = player.getBody().getPosition().y - 1;

                float bombGridX = bomb.getGridX();
                float bombGridY = bomb.getGridY();

                if (Math.abs(bombGridX - playerGridX) < 0.9 &&
                    Math.abs(bombGridY - playerGridY) < 0.9) {
                    lock = true;
                }
            }

            if (!lock)
                BoardInfo.players.get(bomb.getPlayerNumber() - 1).bombTangible(bomb);
        }
    }

    @Override
    public void endContact(Contact contact) {
        updateBombTangible(contact);
        enablePlayerMovement(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
