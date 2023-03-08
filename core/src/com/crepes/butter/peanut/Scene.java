package com.crepes.butter.peanut;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Scene extends Stage implements InputProcessor {

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
	
	public void scaleEntities(float scale) {
		
		for(Actor a : this.getActors()) {
					
				placeHolder = (Entity) a;
				placeHolder.scale(scale);
		}
	}
}
