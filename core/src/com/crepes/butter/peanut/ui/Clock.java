package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.scenes.GameScene;
import com.crepes.butter.peanut.scenes.GameScene.GameState;

public class Clock extends Entity {

	public GameScene gameScene;

	public boolean running;
	public float time;

	public Clock(GameScene gameScene) {

		super(0.25f, 4f, 1.5f, 1f);

		this.gameScene = gameScene;

		time = 40;

		addSprite("Black.png", "black");
	}

	public void reset() {
		time = 40;
		running = true;
	}

	@Override
	public void act(float delta) {

		if (gameScene.gameState != GameState.RUNNING || !running)
			return;

		time -= delta;

		if (time <= 0) {
			time = 0;

			if (!gameScene.water.running)
				gameScene.water.running = true;

			running = false;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

		WallPipe.font.getData().setScale(0.75f, 0.75f);
		WallPipe.font.draw(batch, "Clock", this.getX() + 4, this.getY() + 56);

		WallPipe.font.getData().setScale(1, 1);
		WallPipe.font.draw(batch, String.valueOf((int) Math.ceil(time)), this.getX() + 14, this.getY() + 28);
	}
}
