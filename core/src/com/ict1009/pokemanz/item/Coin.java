package com.ict1009.pokemanz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.entity.Player;

public class Coin extends Item {
    final private int value;

    public Coin(World world, String textureLocation, int initialX, int initialY, int value) {
        super(world, textureLocation, initialX, initialY);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void applyProperty(Player player) {
        player.setCoin(player.getCoin() + this.value);
        this.toDestroy = true;
    }
}
