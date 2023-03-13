package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CachedWaterBlock extends Entity {
	
	public boolean looped;
	
	public CachedWaterBlock(float x, float y, float sizeX, float sizeY) {
		
	    super(x, y, sizeX, sizeY);
		
	    	addSprite("Water.png", "water");
		
		looped = false;
	}
}