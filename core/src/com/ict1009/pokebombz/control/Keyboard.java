package com.ict1009.pokebombz.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Keyboard {
    public static boolean up(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.W;
            break;
        case 2:
            key = Keys.T;
            break;
        case 3:
            key = Keys.I;
            break;
        case 4:
            key = Keys.UP;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean left(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.A;
            break;
        case 2:
            key = Keys.F;
            break;
        case 3:
            key = Keys.J;
            break;
        case 4:
            key = Keys.LEFT;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean down(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.S;
            break;
        case 2:
            key = Keys.G;
            break;
        case 3:
            key = Keys.K;
            break;
        case 4:
            key = Keys.DOWN;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean right(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.D;
            break;
        case 2:
            key = Keys.H;
            break;
        case 3:
            key = Keys.L;
            break;
        case 4:
            key = Keys.RIGHT;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean bomb(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.BACKSPACE;
            break;
        case 2:
            key = Keys.BACKSLASH;
            break;
        case 3:
            key = Keys.ENTER;
            break;
        case 4:
            key = Keys.SHIFT_RIGHT;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean kicking(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.MINUS;
            break;
        case 2:
            key = Keys.LEFT_BRACKET;
            break;
        case 3:
            key = Keys.SEMICOLON;
            break;
        case 4:
            key = Keys.PERIOD;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }

    public static boolean throwing(int playerNumber) {
        int key;
        switch (playerNumber) {
        case 1:
            key = Keys.EQUALS;
            break;
        case 2:
            key = Keys.RIGHT_BRACKET;
            break;
        case 3:
            key = Keys.APOSTROPHE;
            break;
        case 4:
            key = Keys.SLASH;
            break;
        default:
            return false;
        }

        return Gdx.input.isKeyPressed(key);
    }
}
