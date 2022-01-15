package com.ict1009.pokemanz.room;

enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}

public class Door {
    private Direction direction;  // Direction the door is facing
    private String texture; // Display texture

    private boolean open = true; // Check if door is open or closed

    public Door(Direction direction, String texture) {
        this.direction = direction;
        this.texture = texture;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getTexture() {
        return this.texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}
