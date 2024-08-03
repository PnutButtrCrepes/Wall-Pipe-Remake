package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.scenes.GameScene;

public class NextBlocks extends Entity {

	public GameScene gameScene;

	public BuildingBlock[] blockQueue;

	public NextBlocks(GameScene gameScene) {

		super(0.25f, 6f, 1.5f, 6f);

		this.gameScene = gameScene;

		addSprite("Black.png", "black");

		blockQueue = new BuildingBlock[4];

		resetBlocks();
	}

	private BuildingBlock generateRandomBlock() {

		int index = (int) (Math.random() * 100 + 1);

		if (index <= 14)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.ULELBOW);

		if (index <= 28)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.URELBOW);

		if (index <= 42)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.DLELBOW);

		if (index <= 56)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.DRELBOW);

		if (index <= 70)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.HSTRAIGHT);

		if (index <= 84)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.VSTRAIGHT);

		if (index <= 92)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.CROSS);

		if (index <= 96)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.HSQUIGGLY);

		if (index <= 100)
			return new BuildingBlock(BuildingBlock.BuildingBlockType.VSQUIGGLY);

		return null;
	}

	public void reset() {

		blockQueue[0] = null;
		blockQueue[1] = null;
		blockQueue[2] = null;
		blockQueue[3] = null;

		resetBlocks();
	}

	public void resetBlocks() {

		blockQueue[0] = generateRandomBlock();
		blockQueue[1] = generateRandomBlock();
		blockQueue[2] = generateRandomBlock();
		blockQueue[3] = generateRandomBlock();

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

	public void shiftBlocks() {

		blockQueue[0] = blockQueue[1];
		blockQueue[1] = blockQueue[2];
		blockQueue[2] = blockQueue[3];
		blockQueue[3] = generateRandomBlock();

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
	public void act(float delta) {

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}
