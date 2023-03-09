package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class LevelCountManager extends Entity {
	
	GameScene gameScene;
	
	public LevelCountManager(GameScene gameScene) {
		
	    super(7f, 1.25f, 3f, 0.5f);
	    
		this.gameScene = gameScene;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Black.png");
		sprite.setTexture(texture);
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
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.draw(batch, "LEVEL:", this.getX() + 4, this.getY() + 14);
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.draw(batch, String.valueOf(gameScene.levelCount), this.getX() + 48, this.getY() + 14);
	}
}
