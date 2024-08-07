package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.scenes.MainMenuScene;
import com.crepes.butter.peanut.scenes.Scene;

public class MenuCinematicsManager extends Entity {

	private float timer;

	private MainMenuScene scene;

	public MenuCinematicsManager(MainMenuScene scene) {

		super(8.5f, 7.5f, 1f, 1f);

		this.scene = scene;

		timer = 0;
	}

	@Override
	public void act(float delta) {

		timer += delta;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		WallPipe.font.getData().setScale(1f, 1f);
		WallPipe.font.setColor(1, 1, 1, 1);
		WallPipe.font.draw(batch, "FOR THE PATIENT USER", this.getX() - 110, this.getY() - 175);

		if (timer < 3) {

			WallPipe.font.getData().setScale(2f, 2f);
			WallPipe.font.setColor(0, 0, 0, 1);
			WallPipe.font.draw(batch, "Nathan\nKeenan", this.getX() - 70, this.getY() + 50);

		} else if (timer > 3 && timer < 6) {

			scene.displayYellow();

			WallPipe.font.getData().setScale(1.5f, 1.5f);
			WallPipe.font.setColor(0.5f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "PRESENTS", this.getX() - 70, this.getY() + 90);

			WallPipe.font.getData().setScale(1.5f, 1.5f);
			WallPipe.font.setColor(0f, 0f, 0.5f, 1);
			WallPipe.font.draw(batch, "WALL PIPE", this.getX() - 80, this.getY() + 40);

			WallPipe.font.getData().setScale(1.5f, 1.5f);
			WallPipe.font.setColor(0f, 0f, 0.5f, 1);
			WallPipe.font.draw(batch, "v3", this.getX() - 20, this.getY() - 10);

		} else {

			scene.readyToSwitch = true;
			scene.switchSceneType = Scene.SceneType.GAME;
		}
	}
}
