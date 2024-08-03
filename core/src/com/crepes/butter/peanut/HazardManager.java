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

	public HazardManager(GameScene gameScene) {
		super(0, 0, 0, 0);
		this.gameScene = gameScene;
	}

	public void loadLevelHazards() {
		BufferedReader hazardsFileInputStream = new BufferedReader(
				new InputStreamReader(Gdx.files.internal("LevelHazards.txt").read()));
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

	@Override
	public void act(float delta) {
		for (Hazard hazard : activeHazards) {
			switch (hazard.hazardType) {
			case BATHTUB:
				break;

			case FOUR_CORNERS:
				break;

			case MOVING_BLOCK:
				ArrayList<Integer> emptyXIndices = new ArrayList<Integer>();
				ArrayList<Integer> emptyYIndices = new ArrayList<Integer>();

				BuildingBlock[][] blockField = gameScene.bfManager.blockField;
				for (int i = 0; i < blockField.length; i++)
					for (int j = 0; j < blockField[i].length; j++)
						if (blockField[i][j] == null) {
							emptyXIndices.add(i);
							emptyYIndices.add(j);
						}

				BuildingBlock blockToMove = hazard.componentBlocks.get(0);

				for (int i = 0; i < blockField.length; i++)
					for (int j = 0; j < blockField[i].length; j++)
						if (blockField[i][j] == blockToMove)
							blockField[i][j] = null;

				int randomInt = (int) (Math.random() * emptyXIndices.size()) + 1;
				blockField[emptyXIndices.get(randomInt)][emptyXIndices.get(randomInt)] = blockToMove;
				break;

			case POINT_PIPE:
				break;

			case SINK:
				break;

			case STATIONARY_BLOCK:
				break;

			case TELEPORTERS:
				break;

			case UNIDIRECTIONAL_BLOCK:
				break;

			default:
				break;
			}
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub

	}

	public class Hazard extends Entity {
		public HazardType hazardType;
		public ArrayList<BuildingBlock> componentBlocks;

		public Hazard(HazardType hazardType, float x, float y, float sizeX, float sizeY) {
			super(x, y, sizeX, sizeY);
			this.hazardType = hazardType;

			switch (hazardType) {
			case BATHTUB:
				componentBlocks.add(new BuildingBlock(BuildingBlockType.L_BATHTUB));
				componentBlocks.add(new BuildingBlock(BuildingBlockType.R_BATHTUB));
				break;

			case FOUR_CORNERS:
				break;

			case MOVING_BLOCK:
				BuildingBlock blockToAdd = new BuildingBlock(BuildingBlockType.BLANK);
				componentBlocks.add(blockToAdd);
				gameScene.bfManager.addBlock(blockToAdd, (int) (Math.random() * 12 + 3), (int) (Math.random() * 9 + 3));
				break;

			case POINT_PIPE:
				break;

			case SINK:
				break;

			case STATIONARY_BLOCK:
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
