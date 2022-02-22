package com.ict1009.pokebombz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokebombz.entity.Player;

public class BombRange extends Item {
    public BombRange(World world, int gridX, int gridY) {
        super(world, "item/bombrange.png", gridX, gridY);
    }

    public void applyProperty(Player player) {
        player.setBombRange(player.getBombRange() + 1);
        super.setToDestroy();
    }
}
