package com.ict1009.pokemanz.room;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.bomb.Bomb;
import com.ict1009.pokemanz.entity.Player;
import com.ict1009.pokemanz.helper.BoardElement;
import com.ict1009.pokemanz.helper.BoardInfo;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.item.Item;
import java.util.ArrayList;

public abstract class Map implements BoardElement {
    private World world;
    private Body body;

    final private Texture texture;
    private int[][] unbreakable, breakable;

    private Obstacle[][] obstacleMap = new Obstacle[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];
    private Bomb[][] bombMap = new Bomb[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];
    private Item[][] itemMap = new Item[GameInfo.MAP_WIDTH][GameInfo.MAP_HEIGHT];

    private ArrayList<int[]> suddenDeathCoords = new ArrayList<int[]>();

    private int obTimer = 0;
    private int sdCounter = 0;
    Music gameMusic;
    Music sdMusic;

    public Map(String textureLocation, int[][] unbreakable, int[][] breakable) {
        this.texture = new Texture(textureLocation);
        this.unbreakable = unbreakable;
        this.breakable = breakable;

        spiral();

        sdMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_sd.ogg"));
        sdMusic.setLooping(true);

    }

    public Texture getTexture() {
        return this.texture;
    }

    public abstract Music getGameMusic();

    /**
     * Creates unbreakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createUnbreakable(World world) {
        for (int i = 0; i < unbreakable.length; i++) {
            int gridX = unbreakable[i][0];
            int gridY = unbreakable[i][1];
            obstacleMap[gridX][gridY] = new Obstacle(world, "room/unbreakable.png", gridX, gridY);
        }
    }

    /**
     * Creates breakable obstacles using 2d array
     *
     * @param world: World
     */
    private void createBreakable(World world) {
        for (int i = 0; i < breakable.length; i++) {
            int gridX = breakable[i][0];
            int gridY = breakable[i][1];
            obstacleMap[gridX][gridY] =
                new Obstacle(world, "room/breakable.png", gridX, gridY, true);
        }
    }

    private Body createBoundary(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;

        Body body = world.createBody(bodyDef);

        ChainShape shape = new ChainShape();
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0.5f, 0.5f);
        vertices[1] = new Vector2(16.5f, 0.5f);
        vertices[2] = new Vector2(GameInfo.MAP_WIDTH + 0.5f, GameInfo.MAP_HEIGHT + 0.5f);
        vertices[3] = new Vector2(0.5f, GameInfo.MAP_HEIGHT + 0.5f);
        vertices[4] = new Vector2(0.5f, 0.5f);
        shape.createChain(vertices);

        body.createFixture(shape, 1f);

        shape.dispose();
        return body;
    }

    /**
     * Creates both unbreakable and breakable obstacles
     *
     * @param world: World
     */
    public void createObstacles(World world) {
        this.world = world;
        createBreakable(world);
        createUnbreakable(world);
        this.body = createBoundary(world);
    }

    public Obstacle[][] getObstacleMap() {
        return obstacleMap;
    }

    public void setObstacleMap(int gridX, int gridY, Obstacle obstacle) {
        obstacleMap[gridX][gridY] = obstacle;
    }

    public Bomb[][] getBombMap() {
        return bombMap;
    }

    public void setBombMap(int gridX, int gridY, Bomb bomb) {
        bombMap[gridX][gridY] = bomb;
    }

    public Item[][] getItemMap() {
        return itemMap;
    }

    public void setItemMap(int gridX, int gridY, Item item) {
        itemMap[gridX][gridY] = item;
    }

    public void spiral() {
        int rows = obstacleMap.length;
        int cols = obstacleMap[0].length;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1, direction = 1;

        while (top <= bottom && left <= right) {
            switch (direction) {
            case 1:
                for (int i = left; i <= right; ++i) {
                    suddenDeathCoords.add(new int[] {top, i});
                }

                ++top;
                direction = 2;
                break;
            case 2:
                for (int i = top; i <= bottom; ++i) {
                    suddenDeathCoords.add(new int[] {i, right});
                }
                --right;
                direction = 3;
                break;
            case 3:
                for (int i = right; i >= left; --i) {
                    suddenDeathCoords.add(new int[] {bottom, i});
                }
                --bottom;
                direction = 4;
                break;
            case 4:
                for (int i = bottom; i >= top; --i) {
                    suddenDeathCoords.add(new int[] {i, left});
                }
                ++left;
                direction = 1;
                break;
            }
        }
    }

    public void suddenDeath(float delta) {
        if (GameInfo.timeElapsed < GameInfo.SUDDEN_DEATH)
            return;
        gameMusic = getGameMusic();
        gameMusic.dispose();
        sdMusic.play();
        if (obTimer % GameInfo.SUDDEN_DEATH_DROP == 0 && sdCounter < suddenDeathCoords.size()) {

            int gridX = suddenDeathCoords.get(sdCounter)[0];
            int gridY = suddenDeathCoords.get(sdCounter)[1];

            for (Player player : BoardInfo.players) {
                Vector2 playerCoords = player.getBody().getPosition();
                float playerGridX = playerCoords.x - 1;
                float playerGridY = playerCoords.y - 1;

                if (Math.abs(gridX - playerGridX) < 1 && Math.abs(gridY - playerGridY) < 1) {
                    player.setToDestroy();
                }
            }

            Bomb bomb = bombMap[gridX][gridY];
            if (bomb != null)
                bomb.setToDestroy();

            Item item = itemMap[gridX][gridY];
            if (item != null)
                item.setToDestroy();

            obstacleMap[gridX][gridY] = new Obstacle(world, "room/unbreakable.png", gridX, gridY);

            obTimer = 0;
            sdCounter += 1;
        }
        obTimer += 1;
    }

    /**
     * Provides updates to the obstacle class
     *
     * @param delta: 1/fps
     */
    @Override
    public void update(float delta) {
        for (Obstacle[] x : obstacleMap) {
            for (Obstacle y : x) {
                if (y instanceof Obstacle) {
                    y.update(delta);
                    if (y.getDestroyed())
                        obstacleMap[y.getGridX()][y.getGridY()] = null;
                }
            }
        }

        for (Item[] itemRow : itemMap) {
            for (Item item : itemRow) {
                if (item instanceof Item) {
                    item.update(delta);
                    if (item.getDestroyed())
                        itemMap[item.getGridX()][item.getGridY()] = null;
                }
            }
        }

        for (Bomb[] bombRow : bombMap) {
            for (Bomb bomb : bombRow) {
                if (bomb instanceof Bomb) {
                    bomb.update(delta);
                    if (bomb.getDestroyed())
                        bombMap[bomb.getGridX()][bomb.getGridY()] = null;
                }
            }
        }

        suddenDeath(delta);
    }

    /**
     * Renders the obstacles
     *
     * @param batch: SpriteBatch
     */
    @Override
    public void render(SpriteBatch batch) {
        for (Obstacle[] x : obstacleMap) {
            for (Obstacle y : x) {
                if (y instanceof Obstacle) {
                    y.render(batch);
                }
            }
        }

        for (Item[] itemRow : itemMap) {
            for (Item item : itemRow) {
                if (item instanceof Item) {
                    item.render(batch);
                }
            }
        }
    }

    public void setGameMusic() {
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/music_game2.ogg"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }
}
