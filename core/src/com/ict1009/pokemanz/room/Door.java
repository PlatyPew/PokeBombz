package com.ict1009.pokemanz.room;

public class Door {
    private int direction;  // Direction the door is facing
    private String texture; // Display texture

    private boolean open = true; // Check if door is open or closed

    public Door(int direction, String texture) {
        this.direction = direction;
        this.texture = texture;
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }
    public String getTexture() {
        return texture;
    }
    public void setTexture(String texture) {
        this.texture = texture;
    }
}
