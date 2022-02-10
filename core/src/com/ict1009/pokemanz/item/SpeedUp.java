package com.ict1009.pokemanz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.entity.Player;

public class SpeedUp extends Item {
    public SpeedUp(World world, int gridX, int gridY) {
        super(world, "item/speedup.png", gridX, gridY);
    }

    public void applyProperty(Player player) {
        player.setBaseSpeed(player.getBaseSpeed() + 1);
        super.setToDestroy();
    }
}
