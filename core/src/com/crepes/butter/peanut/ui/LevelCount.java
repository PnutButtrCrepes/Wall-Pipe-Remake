package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;

public class LevelCount extends Entity {
	public int levelCount;

	public LevelCount() {

		super(7f, 1.25f, 3f, 0.5f);

		addSprite("Black.png", "black");

		levelCount = 0;
		levelCount++;
	}

	public void reset() {

	}

	@Override
	public void act(float delta) {

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

		WallPipe.font.getData().setScale(0.6f, 0.6f);
		WallPipe.font.draw(batch, "LEVEL:", this.getX() + 4, this.getY() + 14);

		WallPipe.font.getData().setScale(0.6f, 0.6f);
		WallPipe.font.draw(batch, String.valueOf(levelCount), this.getX() + 48, this.getY() + 14);
	}
}
