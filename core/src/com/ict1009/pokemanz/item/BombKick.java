package com.ict1009.pokemanz.item;

import com.badlogic.gdx.physics.box2d.World;
import com.ict1009.pokemanz.entity.Player;

public class BombKick extends Item {
    public BombKick(World world, int gridX, int gridY) {
        super(world, "item/bombkick.png", gridX, gridY);
    }

    public void applyProperty(Player player) {
        player.setKick(true);
        super.setToDestroy();
    }
}
