package com.crepes.butter.peanut;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BlockFieldManager extends Entity
{

    GameScene gameScene;

    public BuildingBlock[][] blockField;

    TextureRegion crack;
    Texture crackTexture;

    float blockBreakTimer;

    boolean replacing;

    BuildingBlock replacementBlock;

    int replacementXIndex;
    int replacementYIndex;

    boolean[][] cracks;

    public BlockFieldManager(GameScene gameScene)
    {

	super(0f, 0f, 0f, 0f);

	this.gameScene = gameScene;

	blockField = new BuildingBlock[15][12];

	crack = new TextureRegion();
	crackTexture = new Texture("Background.png");
	crack.setRegion(crackTexture);

	blockBreakTimer = 2;

	replacing = false;

	cracks = new boolean[32][32];
    }

    public void reset()
    {

	for (int i = 0; i < 15; i++)
	{
	    for (int j = 0; j < 12; j++)
	    {

		gameScene.bfManager.blockField[i][11 - j] = null;
	    }
	}
    }

    public void addBlock(BuildingBlock block, int xIndex, int yIndex)
    {

	blockField[xIndex - 2][yIndex - 2] = block;

	blockField[xIndex - 2][yIndex - 2].setX(xIndex);
	blockField[xIndex - 2][yIndex - 2].setY(yIndex);
    }

    public void replace(BuildingBlock replacementBlock, int xIndex, int yIndex)
    {

	this.replacementBlock = replacementBlock;

	this.replacementXIndex = xIndex;
	this.replacementYIndex = yIndex;

	blockField[replacementXIndex - 2][replacementYIndex - 2].beingReplaced = true;

	replacing = true;
    }

    @Override
    public void act(float delta)
    {

	if (replacing)
	{

	    blockBreakTimer -= delta;

	    for (int i = 0; i < (int) ((2 - blockBreakTimer) * 5); i++)
		cracks[(int) (Math.random() * 32)][(int) (Math.random() * 32)] = true;

	    if (blockBreakTimer <= 0)
	    {

		replacementBlock.beingReplaced = false;

		blockField[replacementXIndex - 2][replacementYIndex - 2] = replacementBlock;

		blockField[replacementXIndex - 2][replacementYIndex - 2].setX(replacementXIndex);
		blockField[replacementXIndex - 2][replacementYIndex - 2].setY(replacementYIndex);

		gameScene.nbManager.shiftBlocks();
		gameScene.mouseGrabbed = true;

		Gdx.input.setCursorPosition((int) ((gameScene.nbManager.getX() + 24) * gameScene.screenWidthRatio),
			(int) (gameScene.viewport.getScreenHeight()
				- ((gameScene.nbManager.getY() + 24) * gameScene.screenHeightRatio)));

		for (int i = 0; i < 32; i++)
		{
		    for (int j = 0; j < 32; j++)
		    {

			cracks[i][j] = false;
		    }
		}

		replacing = false;

		blockBreakTimer = 2;
	    }
	}
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	for (int i = 0; i < 14; i++)
	{
	    for (int j = 0; j < 11; j++)
	    {

		if (blockField[i][j] != null)
		    batch.draw(blockField[i][j].getSprite("block"), blockField[i][j].getX(), blockField[i][j].getY(),
			    blockField[i][j].getWidth(), blockField[i][j].getHeight());
	    }
	}

	for (int i = 0; i < 32; i++)
	{
	    for (int j = 0; j < 32; j++)
	    {

		if (cracks[i][j])
		    batch.draw(crackTexture, replacementXIndex * 32 + i, replacementYIndex * 32 + j, 1, 1);
	    }
	}
    }
}
