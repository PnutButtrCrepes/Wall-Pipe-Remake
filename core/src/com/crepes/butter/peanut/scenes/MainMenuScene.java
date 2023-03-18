package com.crepes.butter.peanut.scenes;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crepes.butter.peanut.BlankTile;
import com.crepes.butter.peanut.MenuCinematicsManager;
import com.crepes.butter.peanut.Scene;

public class MainMenuScene extends Scene implements InputProcessor {

	public MenuCinematicsManager cinematics;
	
	public BlankTile white;
	public BlankTile yellow;
	
	public MainMenuScene(Viewport viewport) {
		
		super(viewport);
		
		this.viewport = viewport;
		
		this.backgroundName = "LoadingScreenBackground";
		
		white = new BlankTile(4.5f, 2.5f, 8, 10, "White.png");
		yellow = new BlankTile(4.75f, 2.75f, 7.5f, 9.5f, "Yellow.png");
		
		cinematics = new MenuCinematicsManager(this);
		
		addActors();
	}

	@Override
	public void addActors() {
		// TODO Auto-generated method stub
		this.addActor(white);
		this.addActor(cinematics);
	}
	
	public void displayYellow() {
		
		this.clear();
		this.addActor(white);
		this.addActor(yellow);
		this.addActor(cinematics);
	}
}