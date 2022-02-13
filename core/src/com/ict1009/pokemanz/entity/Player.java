package com.ict1009.pokemanz.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.bomb.Explode;
import com.ict1009.pokemanz.helper.BoardElement;
import com.ict1009.pokemanz.helper.BoardInfo;
import com.ict1009.pokemanz.helper.Destoryable;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Map;
import com.ict1009.pokemanz.room.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite implements ControllerListener, Destoryable, BoardElement {
    final private World world;
    private Body body;
    final private Map map;
    final private String name;
    final private int playerNumber;

    private int gridX, gridY;

    private boolean toDestroy = false;
    private boolean destroyed = false;
    private boolean unloadOnly = false;

    private boolean dead = false;

    final private TextureAtlas playerAtlasSide;
    final private TextureAtlas playerAtlasDown;
    final private TextureAtlas playerAtlasUp;

    final private TextureAtlas playerDeadAtlasSide;
    final private TextureAtlas playerDeadAtlasDown;
    final private TextureAtlas playerDeadAtlasUp;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private float elapsedTime;

    private boolean isWalking = false;
    private Texture texture;

    private int bombRange = GameInfo.PLAYER_BOMB_RANGE;
    private int maxBombs = GameInfo.PLAYER_BOMBS;
    private float baseSpeed = GameInfo.PLAYER_VELOCITY;
    private boolean kick = false;
    private boolean throwing = false;

    private String player_direction;

    private ArrayList<Bomb> bombs = new ArrayList<Bomb>();

    private ArrayList<Explode> explosions = new ArrayList<Explode>();

    private String controllerID;

    private boolean up = false;
    private boolean left = false;
    private boolean down = false;
    private boolean right = false;

    private boolean disableUp = false;
    private boolean disableLeft = false;
    private boolean disableDown = false;
    private boolean disableRight = false;

    public Player(World world, Map map, int playerNumber, String textureLocation, int gridX,
                  int gridY, String name) {
        super(new Texture(String.format("player/%d/%s", playerNumber, textureLocation)));
        this.name = name;
        this.world = world;
        this.map = map;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.body = createBody();
        this.gridX = gridX;
        this.gridY = gridY;

        this.playerNumber = playerNumber;
        this.texture = new Texture(String.format("player/%d/%s", playerNumber, textureLocation));
        this.playerAtlasSide =
            new TextureAtlas(String.format("player/%d/left.atlas", playerNumber));
        this.playerAtlasDown =
            new TextureAtlas(String.format("player/%d/down.atlas", playerNumber));
        this.playerAtlasUp = new TextureAtlas(String.format("player/%d/up.atlas", playerNumber));

        this.playerDeadAtlasSide =
            new TextureAtlas(String.format("player/d%d/left.atlas", playerNumber));
        this.playerDeadAtlasDown =
            new TextureAtlas(String.format("player/d%d/down.atlas", playerNumber));
        this.playerDeadAtlasUp = new TextureAtlas(String.format("player/d%d/up.atlas", playerNumber));

        if (Controllers.getControllers().notEmpty()) {
            this.controllerID = Controllers.getControllers().pop().getUniqueId();
            Controllers.addListener(this);
        }
    }

    @Override
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
        if (maxBombs <= GameInfo.MAX_PLAYER_BOMBS)
            this.maxBombs = maxBombs;
    }

    public String getControllerID() {
        return controllerID;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void disableUp() {
        disableUp = true;
    }

    public void disableLeft() {
        disableLeft = true;
    }

    public void disableDown() {
        disableDown = true;
    }

    public void disableRight() {
        disableRight = true;
    }

    public void enableAll() {
        disableUp = false;
        disableLeft = false;
        disableDown = false;
        disableRight = false;
    }

    public void setBaseSpeed(float baseSpeed) {
        if (baseSpeed <= GameInfo.MAX_PLAYER_SPEED)
            this.baseSpeed = baseSpeed;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBombRange(int bombRange) {
        if (bombRange <= GameInfo.MAX_PLAYER_BOMB_RANGE)
            this.bombRange = bombRange;
    }

    public int getBombRange() {
        return bombRange;
    }

    public void setKick(boolean kick) {
        this.kick = kick;
    }

    public boolean getKick() {
        return kick;
    }

    public void setThrowing(boolean throwing) {
        this.throwing = throwing;
    }

    public boolean getThrowing() {
        return throwing;
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

        Fixture fixture = body.createFixture(shape, 1f);
        fixture.setUserData(this);
        fixture.setFriction(0);
        shape.dispose();
        return body;
    }

    /**
     * Gets keyboard input and moves the character using WASD Also checks that
     * player does not go over edge
     */
    private void handleMovement() {
        float velX = 0, velY = 0;
        float currX = (getBody().getPosition().x) * GameInfo.PPM;
        float currY = (getBody().getPosition().y) * GameInfo.PPM;

        isWalking = false;

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || up) &&
            currY < GameInfo.HEIGHT - GameInfo.PPM * 2 && !disableUp) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasUp.getRegions());
            texture = new Texture(String.format("player/%d/upstill.png", playerNumber));
            player_direction = "up";
            velY = baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.A) || left) && currX > GameInfo.PPM &&
                   !disableLeft) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasSide.getRegions());
            texture = new Texture(String.format("player/%d/leftstill.png", playerNumber));
            player_direction = "left";
            velX = -baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.S) || down) && currY > GameInfo.PPM &&
                   !disableDown) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasDown.getRegions());
            texture = new Texture(String.format("player/%d/downstill.png", playerNumber));
            player_direction = "down";
            velY = -baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.D) || right) &&
                   currX < GameInfo.WIDTH - (GameInfo.WIDTH - GameInfo.PPM * 16) && !disableRight) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerAtlasSide.getRegions());
            texture = new Texture(String.format("player/%d/rightstill.png", playerNumber));
            player_direction = "right";
            velX = baseSpeed;
        } else {
            getBody().setLinearVelocity(0f, 0.0001f);
            getBody().setLinearVelocity(0f, -0.0001f);
        }

        getBody().setLinearVelocity(velX, velY);
    }

    private void placeBomb() {
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

        // Place bombs only if player has remaining bombs and bomb does not exist in the
        // spot
        if (bombX >= 0 && bombX < GameInfo.MAP_WIDTH && bombY >= 0 && bombY < GameInfo.MAP_HEIGHT) {
            if (bombs.size() < maxBombs && map.getBombMap()[bombX][bombY] == null) {
                Bomb bomb = new Bomb(world, "bomb/bomb1.png", bombX, bombY, playerNumber);
                bombs.add(bomb);
                map.setBombMap(bombX, bombY, bomb); // Places bomb is grid
            }
        }
    }

    private void controllerHandleThrow() {
        float currX = getX() / GameInfo.PPM;
        float currY = getY() / GameInfo.PPM;
        int posX = (int)Math.floor(currX);
        int posY = (int)Math.floor(currY);

        // Snaps the bomb if player passes the 0.5 threshold
        if (currX - posX < 0.5) {
            posX -= 1;
        }
        if (currY - posY < 0.5) {
            posY -= 1;
        }

        if (posX >= 0 && posX < GameInfo.MAP_WIDTH && posY >= 0 && posY < GameInfo.MAP_HEIGHT) {
            if (map.getBombMap()[posX][posY] != null && !destroyed && getThrowing()) {
                handleBombThrow(posX, posY, player_direction);
            }
        }
    }

    /**
     * Check for player direction when F is pressed
     */
    private void handleThrow() {
        float currX = getX() / GameInfo.PPM;
        float currY = getY() / GameInfo.PPM;
        int posX = (int)Math.floor(currX);
        int posY = (int)Math.floor(currY);

        // Snaps the bomb if player passes the 0.5 threshold
        if (currX - posX < 0.5) {
            posX -= 1;
        }
        if (currY - posY < 0.5) {
            posY -= 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F) && map.getBombMap()[posX][posY] != null &&
            !destroyed) {

            // if-else method with up, down as one condition and left, right as one
            // condition
            //        	if (player_direction == "up" || player_direction == "down") {
            //        		System.out.println("character facing up/down");
            //        	}else if (player_direction == "left" || player_direction == "right") {
            //        		System.out.println("character facing left/right");
            //        	}else {
            //        		System.out.println("player have not moved");
            //        	}

            // if-else method with each direction as one condition
            //        	if (player_direction == "up") {
            //        		System.out.println("character facing up");
            //        	}else if ( player_direction == "down"){
            //        		System.out.println("character facing down");
            //        	}else if (player_direction == "left") {
            //        		System.out.println("character facing left");
            //        	}else if ( player_direction == "right") {
            //        		System.out.println("character facing right");
            //        	}else {
            //        		System.out.println("player have not moved");
            //        	}

            // using if-else to check if the player direction is null
            // if player_direction is not null then execute the case condition for each
            // direction
            //			if (player_direction == null) {
            //				player_direction = "up";
            //			}
            //			else {
            //				switch (player_direction) {
            //				case "up":
            //					System.out.println("character facing up");
            //					break;
            //				case "down":
            //					System.out.println("character facing down");
            //					break;
            //				case "right":
            //					System.out.println("character facing right");
            //					break;
            //				case "left":
            //					System.out.println("character facing left");
            //					break;
            //				}
            //			}
            if (getThrowing() == true) {
                handleBombThrow(posX, posY, player_direction);
            }
        }
    }

    /**
     * Handles bomb placement when spacebar is pressed
     */
    private void handleBomb(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !destroyed && !dead) {
            placeBomb();
        }

        List<Bomb> bombToRemove = new ArrayList<Bomb>();

        // Remove bombs from arraylist and bombMap once bomb has exploded
        for (Bomb bomb : bombs) {
            if (bomb.getDestroyed()) {
                bombToRemove.add(bomb);
                int bombX = bomb.getGridX();
                int bombY = bomb.getGridY();

                explosions.add(new Explode(world, map, "explosion/start.png", bombX, bombY,
                                           bombRange, playerNumber, true));
            }
        }

        bombs.removeAll(bombToRemove);

        List<Explode> explodeToRemove = new ArrayList<Explode>();

        // Remove explosions from arraylist
        for (Explode explosion : explosions) {
            explosion.update(delta);

            if (explosion.getDestroyed()) {
                explodeToRemove.add(explosion);
            }
        }

        explosions.removeAll(explodeToRemove);
    }

    private boolean isPlayerAt(int gridX, int gridY) {
        for (Player player : BoardInfo.players) {
            Vector2 playerCoords = player.getBody().getPosition();
            float playerGridX = playerCoords.x - 1;
            float playerGridY = playerCoords.y - 1;

            if (Math.abs(gridX - playerGridX) < 1 && Math.abs(gridY - playerGridY) < 1) {
                return true;
            }
        }
        return false;
    }

    public void handleBombThrow(int gridX, int gridY, String direction) {
        //		System.out.println("handleBombThrow with parameters " + gridX + gridY +
        // direction);
        Bomb bomb = (Bomb)map.getBombMap()[gridX][gridY];

        // Calculation of bomb throw
        // Logic for bomb throwing left
        if (direction == "left" && gridX > 0) {
            for (int i = 1; gridX - i >= 0; i++) {
                if (map.getBombMap()[gridX - i][gridY] == null &&
                    map.getObstacleMap()[gridX - i][gridY] == null) {
                    bomb.updatePosition(gridX - i, gridY);
                    if (!isPlayerAt(gridX - i, gridY))
                        bomb.updateBody(false);
                    map.setBombMap(gridX - i, gridY, bomb);
                    map.setBombMap(gridX, gridY, null);
                    return;
                }
            }
        }
        // Logic for bomb throwing up
        else if (direction == "up" && gridY < 9) {
            for (int i = 1; gridY + i <= 9; i++) {
                if (map.getBombMap()[gridX][gridY + i] == null &&
                    map.getObstacleMap()[gridX][gridY + i] == null) {
                    bomb.updatePosition(gridX, gridY + i);
                    if (!isPlayerAt(gridX, gridY + i))
                        bomb.updateBody(false);
                    map.setBombMap(gridX, gridY + i, bomb);
                    map.setBombMap(gridX, gridY, null);
                    return;
                }
            }
        }
        // Logic for bomb throwing right
        else if (direction == "right" && gridX < 15) {

            for (int i = 1; gridX + i <= 15; i++) {
                if (map.getBombMap()[gridX + i][gridY] == null &&
                    map.getObstacleMap()[gridX + i][gridY] == null) {
                    bomb.updatePosition(gridX + i, gridY);
                    if (!isPlayerAt(gridX + i, gridY))
                        bomb.updateBody(false);
                    map.setBombMap(gridX + i, gridY, bomb);
                    map.setBombMap(gridX, gridY, null);
                    return;
                }
            }
        }
        // Logic for bomb throwing down
        else if (direction == "down" && gridY > 0) {
            for (int i = 1; gridY - i >= 0; i++) {
                int finalY = gridY - i;
                System.out.print("calculating " + finalY);
                if (map.getBombMap()[gridX][gridY - i] == null &&
                    map.getObstacleMap()[gridX][gridY - i] == null) {
                    bomb.updatePosition(gridX, gridY - i);
                    if (!isPlayerAt(gridX, gridY - i))
                        bomb.updateBody(false);
                    map.setBombMap(gridX, gridY - i, bomb);
                    map.setBombMap(gridX, gridY, null);
                    return;
                }
            }
        }
    }

    private boolean isObjectAt(int gridX, int gridY) {
        if (map.getBombMap()[gridX][gridY] instanceof Bomb) {
            return true;
        }
        else if (isPlayerAt(gridX, gridY)) {
            return true;
        }
        else if (map.getObstacleMap()[gridX][gridY] instanceof Obstacle) {
            return true;
        }
        else
            return false;
    }

    private void handleBombKick() {
        float currX = getX() / GameInfo.PPM;
        float currY = getY() / GameInfo.PPM;
        int posX = (int)Math.floor(currX);
        int posY = (int)Math.floor(currY);

        // Snaps the bomb if player passes the 0.5 threshold
        if (currX - posX < 0.5) {
            posX -= 1;
        }
        if (currY - posY < 0.5) {
            posY -= 1;
        }

        if (player_direction == null)
            return;

        switch(player_direction) {
            case "up":
                if (posY < GameInfo.MAP_HEIGHT - 1 && map.getBombMap()[posX][++posY] instanceof Bomb) {
                    System.out.println(posX + " " + posY);
                    int i;
                    for (i = posY; i < GameInfo.MAP_HEIGHT - 1; i++) {
                        if (isObjectAt(posX, i + 1))
                            break;
                    }
                    if (map.getBombMap()[posX][i] == null) {
                        System.out.println(String.format("Land in %d %d", posX, i));
                        Bomb bomb = map.getBombMap()[posX][posY];
                        bomb.updatePosition(posX, i);
                        map.setBombMap(posX, i, bomb);
                        map.setBombMap(posX, posY, null);
                    }
                }
                break;
            case "left":
                if (posX > 0 && map.getBombMap()[--posX][posY] instanceof Bomb) {
                    int i;
                    for (i = posX; i > 0; i--) {
                        if (isObjectAt(i - 1, posY))
                            break;
                    }
                    if (map.getBombMap()[i][posY] == null) {
                        System.out.println(String.format("Land in %d %d", i, posY));
                        Bomb bomb = map.getBombMap()[posX][posY];
                        bomb.updatePosition(i, posY);
                        map.setBombMap(i, posY, bomb);
                        map.setBombMap(posX, posY, null);
                    }
                }
                break;
            case "down":
                if (posY > 0 && map.getBombMap()[posX][--posY] instanceof Bomb) {
                    int i;
                    for (i = posY; i > 0; i--) {
                        if (isObjectAt(posX, i - 1))
                            break;
                    }
                    if (map.getBombMap()[posX][i] == null) {
                        System.out.println(String.format("Land in %d %d", posX, i));
                        Bomb bomb = map.getBombMap()[posX][posY];
                        bomb.updatePosition(posX, i);
                        map.setBombMap(posX, i, bomb);
                        map.setBombMap(posX, posY, null);
                    }
                }
                break;
            case "right":
                if (posX < GameInfo.MAP_WIDTH - 1 && map.getBombMap()[++posX][posY] instanceof Bomb) {
                    int i;
                    for (i = posX; i < GameInfo.MAP_WIDTH - 1; i++) {
                        if (isObjectAt(i + 1, posY))
                            break;
                    }
                    if (map.getBombMap()[i][posY] == null) {
                        System.out.println(String.format("Land in %d %d", i, posY));
                        Bomb bomb = map.getBombMap()[posX][posY];
                        bomb.updatePosition(i, posY);
                        map.setBombMap(i, posY, bomb);
                        map.setBombMap(posX, posY, null);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void updatePosition(int gridX, int gridY) {
        super.setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        body.setTransform(getX() / GameInfo.PPM, getY() / GameInfo.PPM, 0);
    }

    /**
     * Renders the body of the player
     *
     * @param batch: The spritebatch of the game
     */
    @Override
    public void render(SpriteBatch batch) {
        for (Bomb bomb : bombs) {
            bomb.render(batch);
        }

        for (Explode explosion : explosions) {
            explosion.render(batch);
        }

        if (!destroyed) {
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

                frames = playerDeadAtlasSide.getRegions();

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
    }

    public void setDead() {
        dead = true;
        unloadOnly = true;
        toDestroy = true;
        bombRange = GameInfo.PLAYER_BOMB_RANGE;
        maxBombs = GameInfo.PLAYER_BOMBS;
        baseSpeed = GameInfo.PLAYER_VELOCITY;
        kick = false;
        throwing = false;
    }

    public void setAlive(float locX, float locY) {
        float currX = locX / GameInfo.PPM;
        float currY = locY / GameInfo.PPM;
        int posX = (int)Math.floor(currX);
        int posY = (int)Math.floor(currY);

        // Snaps the bomb if player passes the 0.5 threshold
        if (currX - posX < 0.5) {
            posX -= 1;
        }
        if (currY - posY < 0.5) {
            posY -= 1;
        }

        this.gridX = posX;
        this.gridY = posY;

        dead = false;
        unloadOnly = true;
        toDestroy = true;
    }

    public boolean getDead() {
        return dead;
    }

    private String getDeadDirection() {
        float bodyX = body.getPosition().x;
        float bodyY = body.getPosition().y;
        if (bodyX > 0.5 && bodyX < 16.5 && bodyY > -0.1 && bodyY < 0.1)
            return "down";
        else if (bodyX > -0.1 && bodyX < 0.1 && bodyY > 0.5 && bodyY < 10.5)
            return "left";
        else if (bodyX > 0.5 && bodyX < 16.5 && bodyY > 10.9 && bodyY < 11.1)
            return "up";
        else if (bodyX > 16.9 && bodyX < 17.1 && bodyY > 0.5 && bodyY < 10.5)
            return "right";

        return null;
    }

    private void handleDeadMovement() {
        float velX = 0, velY = 0;
        float currX = (getBody().getPosition().x) * GameInfo.PPM;
        float currY = (getBody().getPosition().y) * GameInfo.PPM;

        isWalking = false;

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || up) && currY < GameInfo.HEIGHT - GameInfo.PPM && !disableUp) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerDeadAtlasUp.getRegions());
            velY = baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.A) || left) && currX > 0 && !disableLeft) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerDeadAtlasSide.getRegions());
            velX = -baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.S) || down) && currY > 0 && !disableDown) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerDeadAtlasDown.getRegions());
            velY = -baseSpeed;
        } else if ((Gdx.input.isKeyPressed(Input.Keys.D) || right) && currX < GameInfo.WIDTH - (GameInfo.WIDTH - GameInfo.PPM * 17) && !disableRight) {
            isWalking = true;
            animation =
                new Animation<TextureAtlas.AtlasRegion>(1f / 10f, playerDeadAtlasSide.getRegions());
            velX = baseSpeed;
        }

        player_direction = getDeadDirection();


        if (player_direction != null) {
            switch(player_direction) {
                case "up":
                    texture = new Texture(String.format("player/d%d/downstill.png", playerNumber));
                    break;
                case "left":
                    texture = new Texture(String.format("player/d%d/rightstill.png", playerNumber));
                    break;
                case "down":
                    texture = new Texture(String.format("player/d%d/upstill.png", playerNumber));
                    break;
                case "right":
                    texture = new Texture(String.format("player/d%d/leftstill.png", playerNumber));
                    break;
                default:
                    break;
            }
        }

        getBody().setLinearVelocity(velX, velY);
    }

    private void handleDeadBomb() {
        float currX = getX() / GameInfo.PPM;
        float currY = getY() / GameInfo.PPM;
        int posX = (int)Math.floor(currX);
        int posY = (int)Math.floor(currY);

        // Snaps the bomb if player passes the 0.5 threshold
        if (currX - posX < 0.5) {
            posX -= 1;
        }
        if (currY - posY < 0.5) {
            posY -= 1;
        }

        if (player_direction != null && posX >= -1 && posY >= -1) {
            int i;
            switch(player_direction) {
                case "up":
                    posY -= 2;
                    for (i = posY; i >= 0; i--) {
                        if (!isObjectAt(posX, i))
                            break;
                    }
                    if (i >= 0 && i < GameInfo.MAP_HEIGHT && bombs.size() == 0) {
                        Bomb bomb = new Bomb(world, "bomb/bomb1.png", posX, i, playerNumber, false);
                        bombs.add(bomb);
                        map.setBombMap(posX, i, bomb);
                    }
                    break;
                case "left":
                    posX += 2;
                    for (i = posX; i < GameInfo.MAP_WIDTH; i++) {
                        if (!isObjectAt(i, posY))
                            break;
                    }
                    if (i >= 0 && i < GameInfo.MAP_WIDTH && bombs.size() == 0) {
                        Bomb bomb = new Bomb(world, "bomb/bomb1.png", i, posY, playerNumber, false);
                        bombs.add(bomb);
                        map.setBombMap(i, posY, bomb);
                    }
                    break;
                case "down":
                    posY += 2;
                    for (i = posY; i < GameInfo.MAP_HEIGHT; i++) {
                        if (!isObjectAt(posX, i))
                            break;
                    }
                    if (i >= 0 && i < GameInfo.MAP_HEIGHT && bombs.size() == 0) {
                        Bomb bomb = new Bomb(world, "bomb/bomb1.png", posX, i, playerNumber, false);
                        bombs.add(bomb);
                        map.setBombMap(posX, i, bomb);
                    }
                    break;
                case "right":
                    posX -= 2;
                    for (i = posX; i >= 0; i--) {
                        if (!isObjectAt(i, posY))
                            break;
                    }
                    if (i >= 0 && i < GameInfo.MAP_WIDTH && bombs.size() == 0) {
                        Bomb bomb = new Bomb(world, "bomb/bomb1.png", i, posY, playerNumber, false);
                        bombs.add(bomb);
                        map.setBombMap(i, posY, bomb);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Updates the sprite texture according to where the body is
     *
     * @param delta: 1/fps
     */
    @Override
    public void update(float delta) {
        if (destroyed) {
            handleBomb(delta);
            return;
        }

        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        } else if (dead && !unloadOnly) {
            handleDeadMovement();
            handleBomb(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                handleDeadBomb();
            setPosition((body.getPosition().x) * GameInfo.PPM,
                        (body.getPosition().y) * GameInfo.PPM);
        } else {
            handleMovement();
            handleThrow();
            handleBomb(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.K) && kick)
                handleBombKick();
            setPosition((body.getPosition().x) * GameInfo.PPM,
                        (body.getPosition().y) * GameInfo.PPM);
        }

        if (dead && destroyed && unloadOnly) {
            this.body = createBody();
            toDestroy = false;
            destroyed = false;
            unloadOnly = false;
            switch (playerNumber) {
                case 1:
                    updatePosition(-1, -1);
                    break;
                case 2:
                    updatePosition(16, 10);
                    break;
                case 3:
                    updatePosition(16, -1);
                    break;
                case 4:
                    updatePosition(-1, 10);
                    break;
                default:
                    break;
            }
            this.texture = new Texture(String.format("player/d%d/downstill.png", playerNumber));
        }

        if (!dead && destroyed && unloadOnly) {
            this.body = createBody();
            toDestroy = false;
            destroyed = false;
            unloadOnly = false;

            updatePosition(gridX, gridY);
            this.texture = new Texture(String.format("player/%d/upstill.png", playerNumber));
        }
    }

    public void bombTangible(Bomb detectedBomb) {
        for (Bomb bomb : bombs) {
            if (bomb == detectedBomb && !bomb.getUpdated()) {
                bomb.updateBody(false);
                break;
            }
        }
    }

    @Override
    public void connected(Controller controller) {}

    @Override
    public void disconnected(Controller controller) {}

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        if (controller.getUniqueId() == controllerID) {
            if (buttonCode == 11)
                up = true;
            else if (buttonCode == 13)
                left = true;
            else if (buttonCode == 12)
                down = true;
            else if (buttonCode == 14)
                right = true;
            else if (buttonCode == 1 && !destroyed) {
                placeBomb();
                handleDeadBomb();
            }
            else if (buttonCode == 10)
                controllerHandleThrow();
            else if (buttonCode == 9 && kick)
                handleBombKick();
        }

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (controller.getUniqueId() == controllerID) {
            if (buttonCode == 11)
                up = false;
            else if (buttonCode == 13)
                left = false;
            else if (buttonCode == 12)
                down = false;
            else if (buttonCode == 14)
                right = false;
        }

        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    @Override
    public boolean getDestroyed() {
        return destroyed;
    }

    @Override
    public void setToDestroy() {
        toDestroy = true;
    }
    public void changeSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }
}
