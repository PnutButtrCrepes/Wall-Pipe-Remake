package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WaterEmitter extends BuildingBlock
{

    public GameScene gameScene;

    public int typeIndex;

    public WaterEmitter(GameScene gameScene)
    {

	super(null);

	this.gameScene = gameScene;

	this.watered = true;

	init((int) (Math.random() * 12 + 3), (int) (Math.random() * 9 + 3), 1, 1);

	this.sprite = new Sprite();

	this.typeIndex = (int) (Math.random() * 2);

	if (typeIndex == 0)
	{

	    this.texture = new Texture("LWaterEmitter.png");
	    this.hasLeftExit = true;

	} else
	{

	    this.texture = new Texture("RWaterEmitter.png");
	    this.hasRightExit = true;
	}

	this.hasUpEntrance = false;
	this.hasDownEntrance = false;
	this.hasRightEntrance = false;
	this.hasLeftEntrance = false;

	this.sprite.setRegion(texture);

	gameScene.bfManager.addBlock(this, (int) this.getUnscaledX(), (int) this.getUnscaledY());
    }

    public void reset()
    {

	setPosition((int) (Math.random() * 12 + 3), (int) (Math.random() * 9 + 3));

	this.typeIndex = (int) (Math.random() * 2);

	if (typeIndex == 0)
	{

	    this.texture = new Texture("LWaterEmitter.png");
	    this.hasLeftExit = true;
	    this.hasRightExit = false;

	} else
	{

	    this.texture = new Texture("RWaterEmitter.png");
	    this.hasRightExit = true;
	    this.hasLeftExit = false;
	}

	this.sprite.setRegion(texture);

	gameScene.bfManager.addBlock(this, (int) this.getUnscaledX(), (int) this.getUnscaledY());
    }

    @Override
    public void act(float delta)
    {

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
