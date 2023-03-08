package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.g2d.Batch;

public class MenuCinematicsManager extends Entity {

	private float timer;
	
	private MainMenuScene scene;
	
	public MenuCinematicsManager(MainMenuScene scene) {
		
		this.x = 8.5f;
		this.y = 7.5f;
		
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
		
		FontHelper.font.getData().setScale(1f, 1f);
		FontHelper.font.setColor(1, 1, 1, 1);
		FontHelper.font.draw(batch, "FOR THE PATIENT USER", this.getX() - 110, this.getY() - 175);
		
		if(timer < 3) {
			
			FontHelper.font.getData().setScale(2f, 2f);
			FontHelper.font.setColor(0, 0, 0, 1);
			FontHelper.font.draw(batch, "Nathan\nKeenan", this.getX() - 70, this.getY() + 50);
			
		} else if(timer > 3 && timer < 6) {
			
			scene.displayYellow();
			
			FontHelper.font.getData().setScale(1.5f, 1.5f);
			FontHelper.font.setColor(0.5f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "PRESENTS", this.getX() - 70, this.getY() + 90);
			
			FontHelper.font.getData().setScale(1.5f, 1.5f);
			FontHelper.font.setColor(0f, 0f, 0.5f, 1);
			FontHelper.font.draw(batch, "WALL PIPE", this.getX() - 80, this.getY() + 40);
			
			FontHelper.font.getData().setScale(1.5f, 1.5f);
			FontHelper.font.setColor(0f, 0f, 0.5f, 1);
			FontHelper.font.draw(batch, "v3", this.getX() - 20, this.getY() - 10);
			
		} else {
			
			scene.readyToSwitch = true;
			scene.switchSceneType = SceneType.GAME;
		}
	}
}
