package com.crepes.butter.peanut;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.crepes.butter.peanut.WallPipe;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayModes()[Lwjgl3ApplicationConfiguration.getDisplayModes().length - 2]);
		config.setTitle("Wall Pipe");
		new Lwjgl3Application(new WallPipe(), config);
	}
}
