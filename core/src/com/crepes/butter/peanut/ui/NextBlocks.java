package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;

import com.crepes.butter.peanut.*;

public class NextBlocks extends Entity
{

    public GameScene gameScene;

    public BuildingBlock[] blockQueue;

    public NextBlocks(GameScene gameScene)
    {

	super(0.25f, 6f, 1.5f, 6f);

	this.gameScene = gameScene;

	addSprite("Black.png", "black");

	blockQueue = new BuildingBlock[4];

	resetBlocks();
    }

    public void reset()
    {

	blockQueue[0] = null;
	blockQueue[1] = null;
	blockQueue[2] = null;
	blockQueue[3] = null;

	resetBlocks();
    }

    public void resetBlocks()
    {

	blockQueue[0] = BlockGenerator.generateRandomBlock();
	blockQueue[1] = BlockGenerator.generateRandomBlock();
	blockQueue[2] = BlockGenerator.generateRandomBlock();
	blockQueue[3] = BlockGenerator.generateRandomBlock();

	blockQueue[0].setX(0.5f);
	blockQueue[0].setY(6.25f);

	blockQueue[1].setX(0.5f);
	blockQueue[1].setY(8.25f);

	blockQueue[2].setX(0.5f);
	blockQueue[2].setY(9.5f);

	blockQueue[3].setX(0.5f);
	blockQueue[3].setY(10.75f);

	gameScene.addActor(blockQueue[0]);
	gameScene.addActor(blockQueue[1]);
	gameScene.addActor(blockQueue[2]);
	gameScene.addActor(blockQueue[3]);
    }

    public void shiftBlocks()
    {

	blockQueue[0] = blockQueue[1];
	blockQueue[1] = blockQueue[2];
	blockQueue[2] = blockQueue[3];
	blockQueue[3] = BlockGenerator.generateRandomBlock();

	blockQueue[0].setX(0.5f);
	blockQueue[0].setY(6.25f);

	blockQueue[1].setX(0.5f);
	blockQueue[1].setY(8.25f);

	blockQueue[2].setX(0.5f);
	blockQueue[2].setY(9.5f);

	blockQueue[3].setX(0.5f);
	blockQueue[3].setY(10.75f);

	gameScene.addActor(blockQueue[3]);
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

	batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
