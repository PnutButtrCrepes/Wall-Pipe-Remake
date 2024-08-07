package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;

public class HotKeys extends Entity {
	public HotKeys() {

		super(2f, 0.25f, 14f, 0.5f);

		addSprite("Background.png", "background");
	}

	@Override
	public void act(float delta) {
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("background"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

		WallPipe.font.getData().setScale(0.6f, 0.6f);
		WallPipe.font.draw(batch, "<D>irections", this.getX() + 30, this.getY() + 14);
		WallPipe.font.draw(batch, "<P>ause", this.getX() + 150, this.getY() + 14);
		WallPipe.font.draw(batch, "<O>ptions", this.getX() + 255, this.getY() + 14);
		WallPipe.font.draw(batch, "<Q>uit", this.getX() + 360, this.getY() + 14);
	}
}
