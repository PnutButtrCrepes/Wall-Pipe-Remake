package com.crepes.butter.peanut;

public class CachedWaterBlock extends Entity {
	
	public boolean looped;
	
	public CachedWaterBlock(float x, float y, float sizeX, float sizeY) {
		
	    super(x, y, sizeX, sizeY);
		
	    	addSprite("Water.png", "water");
		
		looped = false;
	}
}