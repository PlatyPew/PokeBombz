package com.ict1009.pokebombz.control;

public class Gamepad {
    public static boolean up(int buttonCode) {
        return buttonCode == 11;
    }

    public static boolean left(int buttonCode) {
        return buttonCode == 13;
    }

    public static boolean down(int buttonCode) {
        return buttonCode == 12;
    }

    public static boolean right(int buttonCode) {
        return buttonCode == 14;
    }

    public static boolean bomb(int buttonCode) {
        return buttonCode == 1;
    }

    public static boolean kicking(int buttonCode) {
        return buttonCode == 9;
    }

    public static boolean throwing(int buttonCode) {
        return buttonCode == 10;
    }
}
