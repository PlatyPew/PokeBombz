package com.ict1009.pokebombz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokebombz.entity.Player;

public class BombExtra extends Item {
    public BombExtra(World world, int gridX, int gridY) {
        super(world, "item/bombextra.png", gridX, gridY);
    }

    public void applyProperty(Player player) {
        player.setMaxBombs(player.getMaxBombs() + 1);
        super.setToDestroy();
    }
}
