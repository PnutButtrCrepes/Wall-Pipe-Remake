package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelCountManager extends Entity {
	
	GameScene gameScene;
	
	public LevelCountManager(GameScene gameScene) {
		
		this.gameScene = gameScene;
		
		this.x = 7f;
		this.y = 1.25f;
		
		this.sizeX = 3f;
		this.sizeY = 0.5f;
		
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
