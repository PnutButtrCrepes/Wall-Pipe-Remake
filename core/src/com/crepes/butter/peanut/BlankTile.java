package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlankTile extends Entity {

	public BlankTile(float x, float y, float sizeX, float sizeY, String texture) {
		
		this.x = x;
		this.y = y;
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.sprite = new Sprite();
		this.texture = new Texture(texture);
		sprite.setTexture(this.texture);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
