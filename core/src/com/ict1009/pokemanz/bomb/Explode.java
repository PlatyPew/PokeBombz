package com.ict1009.pokemanz.bomb;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.helper.GameInfo;
import com.ict1009.pokemanz.room.Map;
import com.ict1009.pokemanz.room.Obstacle;
import java.util.ArrayList;

public class Explode extends Sprite {
    final World world;
    private Body body;
    private Map map;
    private int gridX, gridY;
    private int timer = 2;
    private int range;

    private int counter = 0;
    private boolean exploded = false;
    private boolean toDestroy = false;
    private ArrayList<Explode> explosions = new ArrayList<Explode>();

    private BodyType bodyType = BodyType.StaticBody;

    public Explode(World world, Map map, String textureLocation, int gridX, int gridY, int range,
                     boolean isCenter) {
        super(new Texture(textureLocation));
        this.world = world;
        this.gridX = gridX;
        this.gridY = gridY;
        this.map = map;
        setPosition((gridX + 1) * GameInfo.PPM, (gridY + 1) * GameInfo.PPM);
        this.body = createBody();
        this.range = range;
        if (isCenter) {
            handleExplosion();
        }
    }

    private void handleExplosion() {
        Obstacle[][] obstacleMap = map.getObstacleMap();

        Obstacle rightOfExplosion = null;
        Obstacle leftOfExplosion = null;
        Obstacle topOfExplosion = null;
        Obstacle btmOfExplosion = null;

        boolean leftOfExplosionBreakable = true;
        boolean topOfExplosionBreakable = true;
        boolean rightOfExplosionBreakable = true;
        boolean btmOfExplosionBreakable = true;

        int leftTravel = 0;
        int topTravel = 0;
        int rightTravel = 0;
        int btmTravel = 0;

        // If the explosion is not on the left or right wall
        if (gridX > 0 && gridX < 15) {
            rightOfExplosion = obstacleMap[gridX + 1][gridY];
            leftOfExplosion = obstacleMap[gridX - 1][gridY];
        }
        // If the explosion is on the left wall
        else if (gridX == 0) {
            rightOfExplosion = obstacleMap[gridX + 1][gridY];
            leftOfExplosionBreakable = false;
        }
        // If the explosion is on the right wall
        else if (gridX == 15) {
            leftOfExplosion = obstacleMap[gridX - 1][gridY];
            rightOfExplosionBreakable = false;
        }

        // If the explosion is not on the top or bottom wall
        if (gridY > 0 && gridY < 9) {
            topOfExplosion = obstacleMap[gridX][gridY + 1];
            btmOfExplosion = obstacleMap[gridX][gridY - 1];
        }
        // If the explosion is on the bottom wall
        else if (gridY == 0) {
            topOfExplosion = obstacleMap[gridX][gridY + 1];
            btmOfExplosionBreakable = false;
        }
        // If the explosion is on the top wall
        else if (gridY == 9) {
            btmOfExplosion = obstacleMap[gridX][gridY - 1];
            topOfExplosionBreakable = false;
        }

        // Get whether the obstacles beside the explosion are breakable or not
        if (leftOfExplosion != null) {
            leftOfExplosionBreakable = leftOfExplosion.getBreakable();
        }
        if (topOfExplosion != null) {
            topOfExplosionBreakable = topOfExplosion.getBreakable();
        }
        if (rightOfExplosion != null) {
            rightOfExplosionBreakable = rightOfExplosion.getBreakable();
        }
        if (btmOfExplosion != null) {
            btmOfExplosionBreakable = btmOfExplosion.getBreakable();
        }

        boolean leftStop = !leftOfExplosionBreakable;
        boolean topStop = !topOfExplosionBreakable;
        boolean rightStop = !rightOfExplosionBreakable;
        boolean btmStop = !btmOfExplosionBreakable;

        // Handle explosion length of each side
        for (int i = 1; i < range; i++) {
            // Handle for the left side of the explosion
            if (!leftStop) {
                boolean explosionEnd = false;
                // If x - i is less than 0, stop rendering explosion sprite
                if (gridX - i == 0) {
                    leftStop = true;
                    explosionEnd = true;
                }
                // Otherwise, its safe to continue the explosion in the direction
                else {
                    Obstacle leftObstacle = obstacleMap[gridX - i][gridY];
                    if (leftObstacle != null) {
                        leftStop = true;
                        // If there is an obstacle, stop rendering, and check if it is a breakable
                        // obstacle or not
                        if (leftObstacle.getBreakable()) {
                            leftObstacle.setToDestroy();
                            explosionEnd = true;
                        }
                    } else {
                        // Else if its the end of the explosion, render the end sprite
                        if (i + 1 == range) {
                            explosionEnd = true;
                        } else {
                            explosionEnd = false;
                        }
                    }
                }

                // Render the respective explosion sprite according to the explosionEnd boolean
                if (explosionEnd) {
                    Explode explosion =
                        new Explode(world, map, "explosion/endleft.png", this.gridX - i,
                                      this.gridY, this.range, false);
                    explosions.add(explosion);
                } else {
                    Explode explosion =
                        new Explode(world, map, "explosion/middleleft.png", this.gridX - i,
                                      this.gridY, this.range, false);
                    explosions.add(explosion);
                }
            }

            if (!topStop) {
                boolean explosionEnd = false;
                // If y + i is less than 0, stop rendering explosion sprite
                if (gridY + i == 9) {
                    topStop = true;
                    explosionEnd = true;
                }
                // Otherwise, its safe to continue the explosion in the direction
                else {
                    Obstacle topObstacle = obstacleMap[gridX][gridY + i];
                    if (topObstacle != null) {
                        topStop = true;
                        // If there is an obstacle, stop rendering, and check if it is a breakable
                        // obstacle or not
                        if (topObstacle.getBreakable()) {
                            topObstacle.setToDestroy();
                            explosionEnd = true;
                        }
                    } else {
                        if (i + 1 == range) {
                            explosionEnd = true;
                        } else {
                            explosionEnd = false;
                        }
                    }
                }
                // Render the respective explosion sprite according to the explosionEnd boolean
                if (explosionEnd) {
                    Explode explosion =
                        new Explode(world, map, "explosion/endtop.png", this.gridX,
                                      this.gridY + i, this.range, false);
                    explosions.add(explosion);
                } else {
                    Explode explosion =
                        new Explode(world, map, "explosion/middletop.png", this.gridX,
                                      this.gridY + i, this.range, false);
                    explosions.add(explosion);
                }
            }
            if (!rightStop) {
                boolean explosionEnd = false;
                // If x + i is less than 0, stop rendering explosion sprite
                if (gridX + i == 15) {
                    rightStop = true;
                    explosionEnd = true;
                }
                // Otherwise, its safe to continue the explosion in the direction
                else {
                    Obstacle rightObstacle = obstacleMap[gridX + i][gridY];
                    if (rightObstacle != null) {
                        rightStop = true;
                        // If there is an obstacle, stop rendering, and check if it is a breakable
                        // obstacle or not
                        if (rightObstacle.getBreakable()) {
                            rightObstacle.setToDestroy();
                            explosionEnd = true;
                        }
                    } else {
                        if (i + 1 == range) {
                            explosionEnd = true;
                        } else {
                            explosionEnd = false;
                        }
                    }
                }
                // Render the respective explosion sprite according to the explosionEnd boolean
                if (explosionEnd) {
                    Explode explosion =
                        new Explode(world, map, "explosion/endright.png", this.gridX + i,
                                      this.gridY, this.range, false);
                    explosions.add(explosion);
                } else {
                    Explode explosion =
                        new Explode(world, map, "explosion/middleright.png", this.gridX + i,
                                      this.gridY, this.range, false);
                    explosions.add(explosion);
                }
            }
            if (!btmStop) {
                boolean explosionEnd = false;
                // If y - i is less than 0, stop rendering explosion sprite
                if (gridY - i == 0) {
                    btmStop = true;
                    explosionEnd = true;
                }
                // Otherwise, its safe to continue the explosion in the direction
                else {
                    Obstacle btmObstacle = obstacleMap[gridX][gridY - i];
                    if (btmObstacle != null) {
                        btmStop = true;
                        // If there is an obstacle, stop rendering, and check if it is a breakable
                        // obstacle or not
                        if (btmObstacle.getBreakable()) {
                            btmObstacle.setToDestroy();
                            explosionEnd = true;
                        }
                    } else {
                        if (i + 1 == range) {
                            explosionEnd = true;
                        } else {
                            explosionEnd = false;
                        }
                    }
                }
                // Render the respective explosion sprite according to the explosionEnd boolean
                if (explosionEnd) {
                    Explode explosion =
                        new Explode(world, map, "explosion/endbot.png", this.gridX,
                                      this.gridY - i, this.range, false);
                    explosions.add(explosion);
                } else {
                    Explode explosion =
                        new Explode(world, map, "explosion/middlebot.png", this.gridX,
                                      this.gridY - i, this.range, false);
                    explosions.add(explosion);
                }
            }
        }

        System.out.println("Explode info : ");
        System.out.println("leftOfExplosionBreakable ==> " + leftOfExplosionBreakable);
        System.out.println("topOfExplosionBreakable ==> " + topOfExplosionBreakable);
        System.out.println("rightOfExplosionBreakable ==> " + rightOfExplosionBreakable);
        System.out.println("btmOfExplosionBreakable ==> " + btmOfExplosionBreakable);
        // If left side of the explosion is unbreakable
        if (!leftOfExplosionBreakable) {
            super.setTexture(new Texture("explosion/edge1.png"));
            // If left and top side of the center is unbreakable
            if (!topOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/corner2.png"));
                // If left, top and right side of the center is unbreakable
                if (!rightOfExplosionBreakable) {
                    super.setTexture(new Texture("explosion/endtop.png"));
                }
                // If left, top and bottom side of the center is unbreakable
                else if (!btmOfExplosionBreakable) {
                    super.setTexture(new Texture("explosion/endleft.png"));
                }
            }
            // If left and bottom side of the center is unbreakable
            else if (!btmOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/corner1.png"));
                // If left, bottom and right side of the center is unbreakable
                if (!rightOfExplosionBreakable) {
                    super.setTexture(new Texture("explosion/endbtm.png"));
                }
            }
            // If left and right side of the center is unbreakable
            else if (!rightOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/middletop.png"));
            }
        }
        // Else if the top side of the center is unbreakable
        else if (!topOfExplosionBreakable) {
            super.setTexture(new Texture("explosion/edge2.png"));
            // If the top and right side of the center is unbreakable
            if (!rightOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/corner3.png"));
                // If the top, right and bottom of the center is unbreakable
                if (!btmOfExplosionBreakable) {
                    super.setTexture(new Texture("explosion/endright.png"));
                }
            }
            // If top and bottom of the center is unbreakable
            else if (!btmOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/middleleft.png"));
            }
        }
        // Else if the right side of the center is unbreakable
        else if (!rightOfExplosionBreakable) {
            super.setTexture(new Texture("explosion/edge3.png"));
            if (!btmOfExplosionBreakable) {
                super.setTexture(new Texture("explosion/corner4.png"));
            }
        }
        // Else only the bottom side of the center is unbreakable
        else if (!btmOfExplosionBreakable) {
            super.setTexture(new Texture("explosion/edge4.png"));
        }
    }

    private Body createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        bodyDef.fixedRotation = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        Body body = world.createBody(bodyDef);

        shape.dispose();
        return body;
    }

    public boolean getExploded() {
        return exploded;
    }

    private void countDown(float delta) {
        if (counter < timer / delta) {
            counter++;
        } else {
            toDestroy = true;
        }
    }

    public void update(float delta) {
        countDown(delta);

        if (toDestroy && !exploded) {
            this.world.destroyBody(this.body);
            exploded = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!exploded) {
            for (Explode explosion : explosions) {
                explosion.render(batch);
            }
            batch.draw(this, this.getX(), this.getY());
        }
    }
}
