package com.ict1009.pokemanz.attack;

import com.badlogic.gdx.physics.box2d.World;

public class BulletLow extends Bullet {
    final private static String textureLocation = "attack/attack1.png";
    final private static int damage = 1;

    public BulletLow(World world) {
        super(world, textureLocation, damage);
    }
}
