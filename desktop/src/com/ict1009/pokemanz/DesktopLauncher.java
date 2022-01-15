package com.ict1009.pokemanz;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.ict1009.pokemanz.helper.GameInfo;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM
// argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(GameInfo.FPS);
        config.setWindowedMode(GameInfo.WIDTH, GameInfo.HEIGHT);
        new Lwjgl3Application(new GameMain(), config);
    }
}
