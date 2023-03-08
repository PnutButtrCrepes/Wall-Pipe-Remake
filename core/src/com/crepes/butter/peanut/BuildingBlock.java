package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BuildingBlock extends Entity {

	BuildingBlockType type;
	
	public boolean hasUpEntrance;
	public boolean hasDownEntrance;
	public boolean hasLeftEntrance;
	public boolean hasRightEntrance;
	
	public boolean hasUpExit;
	public boolean hasDownExit;
	public boolean hasLeftExit;
	public boolean hasRightExit;
	
	public boolean watered;
	public boolean beingReplaced;
	
	boolean visible;
	
	public BuildingBlock(BuildingBlockType type) {
		
		watered = false;
		beingReplaced = false;
		
		this.type = type;
		
		this.sizeX = 1;
		this.sizeY = 1;
		
		this.sprite = new Sprite();
		
		if(type != null)
		switch(this.type) {
		
		case CROSS:
			
			this.texture = new Texture("Cross.png");
			
			hasUpEntrance = true;
			hasDownEntrance = true;
			hasLeftEntrance = true;
			hasRightEntrance = true;
			
			hasUpExit = true;
			hasDownExit = true;
			hasLeftExit = true;
			hasRightExit = true;
			
			break;
			
		case DLELBOW:
			
			this.texture = new Texture("DLElbow.png");
			
			hasUpEntrance = false;
			hasDownEntrance = true;
			hasLeftEntrance = true;
			hasRightEntrance = false;
			
			hasUpExit = false;
			hasDownExit = true;
			hasLeftExit = true;
			hasRightExit = false;
			
			break;
			
		case DRELBOW:
			
			this.texture = new Texture("DRElbow.png");
			
			hasUpEntrance = false;
			hasDownEntrance = true;
			hasLeftEntrance = false;
			hasRightEntrance = true;
			
			hasUpExit = false;
			hasDownExit = true;
			hasLeftExit = false;
			hasRightExit = true;
			
			break;
			
		case HSQUIGGLY:
			
			this.texture = new Texture("HSquiggly.png");
			
			hasUpEntrance = false;
			hasDownEntrance = false;
			hasLeftEntrance = true;
			hasRightEntrance = true;
			
			hasUpExit = false;
			hasDownExit = false;
			hasLeftExit = true;
			hasRightExit = true;
			
			break;
			
		case HSTRAIGHT:
			
			this.texture = new Texture("HStraight.png");
			
			hasUpEntrance = false;
			hasDownEntrance = false;
			hasLeftEntrance = true;
			hasRightEntrance = true;
			
			hasUpExit = false;
			hasDownExit = false;
			hasLeftExit = true;
			hasRightExit = true;
			
			break;
			
		case ULELBOW:
			
			this.texture = new Texture("ULElbow.png");
			
			hasUpEntrance = true;
			hasDownEntrance = false;
			hasLeftEntrance = true;
			hasRightEntrance = false;
			
			hasUpExit = true;
			hasDownExit = false;
			hasLeftExit = true;
			hasRightExit = false;
			
			break;
			
		case URELBOW:
			
			this.texture = new Texture("URElbow.png");
			
			hasUpEntrance = true;
			hasDownEntrance = false;
			hasLeftEntrance = false;
			hasRightEntrance = true;
			
			hasUpExit = true;
			hasDownExit = false;
			hasLeftExit = false;
			hasRightExit = true;
			
			break;
			
		case VSQUIGGLY:
			
			this.texture = new Texture("VSquiggly.png");
			
			hasUpEntrance = true;
			hasDownEntrance = true;
			hasLeftEntrance = false;
			hasRightEntrance = false;
			
			hasUpExit = true;
			hasDownExit = true;
			hasLeftExit = false;
			hasRightExit = false;
			
			break;
			
		case VSTRAIGHT:
			
			this.texture = new Texture("VStraight.png");
			
			hasUpEntrance = true;
			hasDownEntrance = true;
			hasLeftEntrance = false;
			hasRightEntrance = false;
			
			hasUpExit = true;
			hasDownExit = true;
			hasLeftExit = false;
			hasRightExit = false;
			
			break;
			
		default:
			break;
		}
		
		if(texture != null)
			sprite.setRegion(texture);
		
		visible = true;
	}
	
	@Override
	public void act(float delta) {
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		this.toFront();
		
		batch.end();
		batch.begin();
		
		if(visible)
			batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
