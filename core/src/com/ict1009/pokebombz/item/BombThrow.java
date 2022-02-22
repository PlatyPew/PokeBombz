package com.ict1009.pokebombz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokebombz.entity.Player;

public class BombThrow extends Item {
    public BombThrow(World world, int gridX, int gridY) {
        super(world, "item/bombthrow.png", gridX, gridY);
    }

    public void applyProperty(Player player) {
        player.setThrowing(true);
        super.setToDestroy();
    }
}
