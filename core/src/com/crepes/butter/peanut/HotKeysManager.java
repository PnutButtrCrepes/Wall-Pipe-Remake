package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class HotKeysManager extends Entity {

	public float score;
	
	public GameScene gameScene;
	
	public HotKeysManager(GameScene gameScene) {
		
		this.x = 2f;
		this.y = 0.25f;
		
		this.sizeX = 14f;
		this.sizeY = 0.5f;
		
		score = 0;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Background.png");
		sprite.setTexture(texture);
		
		this.gameScene = gameScene;
	}
	
	@Override
	public void act(float delta) {
		
		if(gameScene.water.running)
			score += delta * 8;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.draw(batch, "<D>irections", this.getX() + 30, this.getY() + 14);
		FontHelper.font.draw(batch, "<P>ause", this.getX() + 150, this.getY() + 14);
		FontHelper.font.draw(batch, "<O>ptions", this.getX() + 255, this.getY() + 14);
		FontHelper.font.draw(batch, "<Q>uit", this.getX() + 360, this.getY() + 14);
	}
}
