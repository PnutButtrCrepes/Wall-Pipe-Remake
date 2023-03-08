package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NextBlocksManager extends Entity {
	
	public GameScene gameScene;
	
	public BuildingBlock[] blockQueue;
	
	public NextBlocksManager(GameScene gameScene) {
		
		this.gameScene = gameScene;
		
		this.x = 0.25f;
		this.y = 6f;
		
		this.sizeX = 1.5f;
		this.sizeY = 6f;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Black.png");
		sprite.setRegion(texture);
		
		blockQueue = new BuildingBlock[4];
		
		resetBlocks();
	}
	
	public void reset() {
		
		blockQueue[0] = null;
		blockQueue[1] = null;
		blockQueue[2] = null;
		blockQueue[3] = null;
		
		resetBlocks();
	}
	
	public void resetBlocks() {
		
		blockQueue[0] = BlockGenerator.generateRandomBlock();
		blockQueue[1] = BlockGenerator.generateRandomBlock();
		blockQueue[2] = BlockGenerator.generateRandomBlock();
		blockQueue[3] = BlockGenerator.generateRandomBlock();
		
		blockQueue[0].x = 0.5f;
		blockQueue[0].y = 6.25f;
		
		blockQueue[1].x = 0.5f;
		blockQueue[1].y = 8.25f;
		
		blockQueue[2].x = 0.5f;
		blockQueue[2].y = 9.5f;
		
		blockQueue[3].x = 0.5f;
		blockQueue[3].y = 10.75f;
		
		gameScene.addActor(blockQueue[0]);
		gameScene.addActor(blockQueue[1]);
		gameScene.addActor(blockQueue[2]);
		gameScene.addActor(blockQueue[3]);
	}
	
	public void shiftBlocks() {
		
		blockQueue[0] = blockQueue[1];
		blockQueue[1] = blockQueue[2];
		blockQueue[2] = blockQueue[3];
		blockQueue[3] = BlockGenerator.generateRandomBlock();
		
		blockQueue[0].x = 0.5f;
		blockQueue[0].y = 6.25f;
		
		blockQueue[1].x = 0.5f;
		blockQueue[1].y = 8.25f;
		
		blockQueue[2].x = 0.5f;
		blockQueue[2].y = 9.5f;
		
		blockQueue[3].x = 0.5f;
		blockQueue[3].y = 10.75f;
		
		gameScene.addActor(blockQueue[3]);
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		batch.begin();
		
		batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
