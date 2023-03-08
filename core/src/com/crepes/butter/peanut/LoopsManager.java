package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LoopsManager extends Entity {

	GameScene gameScene;
	
	public int loops;
	public boolean bonusAdded;
	
	public LoopsManager(GameScene gameScene) {
		
		this.gameScene = gameScene;
		
		this.x = 0.25f;
		this.y = 2f;
		
		this.sizeX = 1.5f;
		this.sizeY = 1f;
		
		loops = 0;
		bonusAdded = false;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Black.png");
		sprite.setTexture(texture);
	}
	
	public void reset() {
		
		loops = 0;
		bonusAdded = false;
	}
	
	public float getBonus() {
		
		if(loops <= 3)
			return 0;
		else if(loops > 3 && loops <= 7)
			return 400;
		else if(loops > 7 && loops <= 11)
			return 800;
		else 
			return 1500;
	}
	
	public void addBonus() {
		
		if(!bonusAdded) {
			
			gameScene.scoreManager.score += getBonus();
			bonusAdded = true;
		}
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		FontHelper.font.getData().setScale(0.75f, 0.75f);
		FontHelper.font.draw(batch, "Loops", this.getX() + 4, this.getY() + 56);
		
		FontHelper.font.getData().setScale(1, 1);
		FontHelper.font.draw(batch, String.valueOf(loops), this.getX() + 18, this.getY() + 28);
	}
}
