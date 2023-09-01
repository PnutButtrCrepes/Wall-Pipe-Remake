package com.crepes.butter.peanut.scenes;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crepes.butter.peanut.Entity;

public abstract class Scene extends Stage {

	public Viewport viewport;
	
	public Entity placeHolder;
	
	public String backgroundName;
	
	public boolean readyToSwitch;
	public SceneType switchSceneType;
	
	public Scene(Viewport viewport) {
		
		super(viewport);
		
		this.viewport = viewport;
	}
	
	public abstract void addActors();
	
	public enum SceneType {

		MAIN_MENU,
		GAME
	}
}
