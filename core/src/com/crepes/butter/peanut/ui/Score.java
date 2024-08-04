package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.scenes.GameScene;
import com.crepes.butter.peanut.scenes.GameScene.GameState;

public class Score extends Entity {

	public float score;

	public GameScene gameScene;

	public Score(GameScene gameScene) {

		super(11f, 1.25f, 5f, 0.5f);

		score = 0;

		addSprite("Black.png", "black");

		this.gameScene = gameScene;
	}

	public void reset() {

		score = (int) score;
	}

	@Override
	public void act(float delta) {

		if (gameScene.gameState == GameState.RUNNING) {

			// TODO make score an integer
			if (gameScene.water.running)
				score += delta * 6.625;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

		WallPipe.font.getData().setScale(0.6f, 0.6f);
		WallPipe.font.draw(batch, "SCORE:", this.getX() + 4, this.getY() + 14);

		if ((int) (score) % 2 == 0) {

			WallPipe.font.getData().setScale(0.6f, 0.6f);
			WallPipe.font.draw(batch, String.valueOf((int) score), this.getX() + 48, this.getY() + 14);

		} else {

			WallPipe.font.getData().setScale(0.6f, 0.6f);
			WallPipe.font.draw(batch, String.valueOf((int) (score - 1)), this.getX() + 48, this.getY() + 14);
		}
	}
}
