package com.crepes.butter.peanut.blocks;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.scenes.GameScene;

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

	this.typeIndex = (int) (Math.random() * 2);

	if (typeIndex == 0)
	{
	    addSprite("LWaterEmitter.png", "block");

	} else
	{
	    addSprite("RWaterEmitter.png", "block");
	}

	gameScene.bfManager.addBlock(this, (int) this.getUnscaledX(), (int) this.getUnscaledY());
    }

    public void reset()
    {

	setPosition((int) (Math.random() * 12 + 3), (int) (Math.random() * 9 + 3));

	this.typeIndex = (int) (Math.random() * 2);

	if (typeIndex == 0)
	{

	    addSprite("LWaterEmitter.png", "block");

	} else
	{

	    addSprite("RWaterEmitter.png", "block");
	}

	gameScene.bfManager.attemptAddBlock(this, (int) this.getUnscaledX(), (int) this.getUnscaledY());
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

	batch.draw(getSprite("block"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
