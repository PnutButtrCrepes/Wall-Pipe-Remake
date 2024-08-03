package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.g2d.Batch;

public class BlankTile extends Entity {

	public BlankTile(float x, float y, float sizeX, float sizeY, String texture) {

		super(x, y, sizeX, sizeY);

		addSprite(texture, "texture");
	}

	@Override
	public void act(float delta) {
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("texture"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
