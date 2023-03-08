package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor {
	
	float x;
	float y;
	
	float sizeX;
	float sizeY;
	
	Sprite sprite;
	Texture texture;
	
	public Entity() {
		
	}
	
	public void scale(float scale) {
		
		this.setPosition(x * scale, y * scale);
		this.setSize(sizeX * scale, sizeY * scale);
	}
}