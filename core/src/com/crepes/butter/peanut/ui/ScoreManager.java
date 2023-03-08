package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class ScoreManager extends Entity {

	public float score;
	
	public GameScene gameScene;
	
	public ScoreManager(GameScene gameScene) {
		
		this.x = 11f;
		this.y = 1.25f;
		
		this.sizeX = 5f;
		this.sizeY = 0.5f;
		
		score = 0;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Black.png");
		sprite.setTexture(texture);
		
		this.gameScene = gameScene;
	}
	
	public void reset() {
		
		score = (int) score;
	}
	
	@Override
	public void act(float delta) {
		
		if(!gameScene.isPaused() && gameScene.isLevelStarted()) {
		
		if(gameScene.water.running)
			score += delta * 8;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.draw(batch, "SCORE:", this.getX() + 4, this.getY() + 14);
		
		if((int)(score)%2 == 0) {
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.draw(batch, String.valueOf((int) score), this.getX() + 48, this.getY() + 14);
		
		} else {
			
			FontHelper.font.getData().setScale(0.6f, 0.6f);
			FontHelper.font.draw(batch, String.valueOf((int) (score - 1)), this.getX() + 48, this.getY() + 14);
		}
	}
}
