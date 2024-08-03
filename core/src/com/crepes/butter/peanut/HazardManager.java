package com.crepes.butter.peanut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.blocks.BuildingBlock.BuildingBlockType;
import com.crepes.butter.peanut.scenes.GameScene;

public class HazardManager extends Entity {
	// blank blocks that are still
	// blank blocks that move around - level 2
	// bathtubs - level 3
	// unidirectional blocks and four outward corners
	// teleporters
	// 50-point elbow pipes, all lower corners
	// sink

	// 6 obstacles in total

	public GameScene gameScene;

	public ArrayList<LevelHazards> levelHazards;
	public ArrayList<Hazard> activeHazards;
	public boolean readyForUpdate = false;

	public HazardManager(GameScene gameScene) {
		super(0, 0, 0, 0);
		this.gameScene = gameScene;
		
		levelHazards = new ArrayList<LevelHazards>();
		
		loadLevelHazards();
		reset();
	}

	public void loadLevelHazards() {
		BufferedReader hazardsFileInputStream = new BufferedReader(
				new InputStreamReader(Gdx.files.internal("assets\\LevelHazards.txt").read()));
		String line = "";
		try {
			while ((line = hazardsFileInputStream.readLine()) != null) {
				String[] hazards = line.split(",");

				for (String hazard : hazards)
					hazard.trim();

				int[] numberOfHazards = new int[hazards.length];
				for (int i = 0; i < hazards.length; i++)
					numberOfHazards[i] = Integer.parseInt(hazards[i]);

				levelHazards.add(new LevelHazards(numberOfHazards));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reset()
	{
		activeHazards = new ArrayList<Hazard>();
		
		int[] currentHazardArray;
		if (gameScene.gameUI.levelCount.levelCount - 1 < levelHazards.size())
			currentHazardArray = levelHazards.get(gameScene.gameUI.levelCount.levelCount - 1).numberOfHazards;
		else
			currentHazardArray = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 }; // TODO should replace with a random method
		
		for (int i = 0; i < currentHazardArray.length; i++)
			for (int j = 0; j < currentHazardArray[i]; j++)
			{
				switch (i)
				{
				case 0:
					activeHazards.add(new Hazard(HazardType.STATIONARY_BLOCK, 0, 0, 0, 0));
					break;
					
				case 1:
					activeHazards.add(new Hazard(HazardType.MOVING_BLOCK, 0, 0, 0, 0));
					break;
					
				case 2:
					activeHazards.add(new Hazard(HazardType.BATHTUB, 0, 0, 0, 0));
					break;
					
				case 3:
					activeHazards.add(new Hazard(HazardType.UNIDIRECTIONAL_BLOCK, 0, 0, 0, 0));
					break;
					
				case 4:
					activeHazards.add(new Hazard(HazardType.FOUR_CORNERS, 0, 0, 0, 0));
					break;
					
				case 5:
					activeHazards.add(new Hazard(HazardType.TELEPORTERS, 0, 0, 0, 0));
					break;
					
				case 6:
					activeHazards.add(new Hazard(HazardType.POINT_PIPE, 0, 0, 0, 0));
					break;
					
				case 7:
					activeHazards.add(new Hazard(HazardType.SINK, 0, 0, 0, 0));
					break;
				}
			}
	}
	
	@Override
	public void act(float delta) {
		
		if (!readyForUpdate)
			return;
		
		for (Hazard hazard : activeHazards) {
			switch (hazard.hazardType) {
			case STATIONARY_BLOCK:
				break;
				
			case MOVING_BLOCK:
				ArrayList<Integer> emptyXIndices = new ArrayList<Integer>();
				ArrayList<Integer> emptyYIndices = new ArrayList<Integer>();

				BuildingBlock[][] blockField = gameScene.bfManager.blockField;
				for (int i = 0; i < blockField.length - 1; i++)
					for (int j = 0; j < blockField[i].length - 1; j++)
						if (blockField[i][j] == null) {
							emptyXIndices.add(i);
							emptyYIndices.add(j);
						}

				BuildingBlock blockToMove = hazard.componentBlocks.get(0);

				for (int i = 0; i < blockField.length - 1; i++)
					for (int j = 0; j < blockField[i].length - 1; j++)
						if (blockField[i][j] == blockToMove)
							blockField[i][j] = null;

				int randomInt = (int) (Math.random() * emptyXIndices.size());
				
				gameScene.bfManager.addBlock(blockToMove, emptyXIndices.get(randomInt) + 2, emptyYIndices.get(randomInt) + 2);
				//blockField[emptyXIndices.get(randomInt)][emptyYIndices.get(randomInt)] = blockToMove;
				break;
			
			case BATHTUB:
				break;

			case FOUR_CORNERS:
				break;			

			case POINT_PIPE:
				break;

			case SINK:
				break;

			case TELEPORTERS:
				break;

			case UNIDIRECTIONAL_BLOCK:
				break;

			default:
				break;
			}
		}
		
		readyForUpdate = false;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub

	}

	public class Hazard extends Entity {
		public HazardType hazardType;
		public ArrayList<BuildingBlock> componentBlocks;

		public Hazard(HazardType hazardType, float x, float y, float sizeX, float sizeY) {
			super(0, 0, 0, 0);
			this.hazardType = hazardType;
			this.componentBlocks = new ArrayList<BuildingBlock>();

			switch (hazardType) {
			case STATIONARY_BLOCK:
				// TODO ensure placing block is empty
				BuildingBlock blockToAdd = new BuildingBlock(BuildingBlockType.BLANK);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, (int) (Math.random() * 14 + 2), (int) (Math.random() * 11 + 2));
				blockToAdd.replaceable = false;
				break;
				
			case MOVING_BLOCK:
				// TODO ensure placing block is empty
				blockToAdd = new BuildingBlock(BuildingBlockType.BLANK);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, (int) (Math.random() * 14 + 2), (int) (Math.random() * 11 + 2));
				blockToAdd.replaceable = false;
				break;
			
			case BATHTUB:
				componentBlocks.add(new BuildingBlock(BuildingBlockType.L_BATHTUB));
				componentBlocks.add(new BuildingBlock(BuildingBlockType.R_BATHTUB));
				break;

			case FOUR_CORNERS:
				int randomX = (int) (Math.random() * 14 + 2);
				int randomY = (int) (Math.random() * 11 + 2);
				blockToAdd = new BuildingBlock(BuildingBlockType.DARK_DLELBOW);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, randomX, randomY);
				
				blockToAdd = new BuildingBlock(BuildingBlockType.DARK_DRELBOW);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, randomX + 1, randomY);
				
				blockToAdd = new BuildingBlock(BuildingBlockType.DARK_ULELBOW);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, randomX, randomY + 1);
				
				blockToAdd = new BuildingBlock(BuildingBlockType.DARK_URELBOW);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, randomX + 1, randomY + 1);
				break;

			case POINT_PIPE:
				break;

			case SINK:
				break;

			case TELEPORTERS:
				componentBlocks.add(new BuildingBlock(BuildingBlockType.TELEPORT_CROSS));
				componentBlocks.add(new BuildingBlock(BuildingBlockType.TELEPORT_CROSS));
				break;

			case UNIDIRECTIONAL_BLOCK:
				break;

			default:
				break;
			}
		}

		@Override
		public void act(float delta) {
			for (BuildingBlock block : componentBlocks)
				block.act(delta);
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
			for (BuildingBlock block : componentBlocks)
				block.draw(batch, parentAlpha);
		}
	}

	public enum HazardType {
		STATIONARY_BLOCK, MOVING_BLOCK, BATHTUB, UNIDIRECTIONAL_BLOCK, FOUR_CORNERS, TELEPORTERS, POINT_PIPE, SINK
	}

	public class LevelHazards {
		// int blankStationaryBlocks, int blankMovingBlocks, int bathtubs, int
		// unidirectionalBlocks, int fourCorners, int teleporters, int pointPipes, int
		// sinks

		int[] numberOfHazards;

		public LevelHazards(int[] numberOfHazards) {
			this.numberOfHazards = numberOfHazards.clone();
		}
	}
}
