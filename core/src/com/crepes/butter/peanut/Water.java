package com.crepes.butter.peanut;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.blocks.BuildingBlock.BuildingBlockType;
import com.crepes.butter.peanut.blocks.BuildingBlock.TeleportPoint;
import com.crepes.butter.peanut.blocks.BuildingBlock.TurningPoint;
import com.crepes.butter.peanut.scenes.GameScene;
import com.crepes.butter.peanut.scenes.GameScene.GameState;

public class Water extends Entity {
	public static final float STREAM_WIDTH = 0.1875f;

	public GameScene gameScene;

	public float posX;
	public float posY;

	public int squigglyCounter;

	public WaterDirection direction;
	public boolean running;
	public float speed;

	public BuildingBlock currentBlock;
	public boolean newBlock;
	public boolean positiveDirection;
	public boolean canTeleport;

	public ArrayList<CachedWaterBlock> water;
	public CachedWaterBlock placeHolder;

	public Water(GameScene gameScene) {

		super(0f, 0f, 0f, 0f);

		this.gameScene = gameScene;

		if (gameScene.emitter.typeIndex == 0) {

			direction = WaterDirection.LEFT;

			this.setX(gameScene.emitter.getUnscaledX() + 0.25f);
			this.setWidth(-0.125f - 0.015625f);

		} else {

			direction = WaterDirection.RIGHT;

			this.setX(gameScene.emitter.getUnscaledX() + 0.75f);
			this.setWidth(0.125f + 0.015625f);
		}

		this.setY(gameScene.emitter.getUnscaledY() + 0.5f - STREAM_WIDTH / 2);
		this.setHeight(STREAM_WIDTH);

		squigglyCounter = 0;

		newBlock = false;

		speed = 0.525f;

		addSprite("Water.png", "water");

		water = new ArrayList<CachedWaterBlock>();
		
		canTeleport = true;
	}

	private void cacheWaterBlock() {

		placeHolder = new CachedWaterBlock(getUnscaledX(), getUnscaledY(), getUnscaledWidth(), getUnscaledHeight());
		gameScene.addActor(placeHolder);
		water.add(placeHolder);
	}

	private void changeDirection(WaterDirection direction) {
		// TODO remember, top-left of screen is (0, 0)

		if (this.direction == direction)
		{
			if (this.currentBlock.type != BuildingBlockType.TELEPORT_CROSS)
				return;
			
			return;
		}

		posX = (posX) + (int) ((posX - (int) posX) * 32) / 32;
		posY = (posY) + (int) ((posY - (int) posY) * 32) / 32;

		setWidth(posX - getUnscaledX());
		setHeight(posY - getUnscaledY());

		cacheWaterBlock();

		switch (direction) {

		case UP:

			if (this.direction == WaterDirection.LEFT) {

				this.setX(posX);
				this.setY(posY - STREAM_WIDTH);

			} else {

				this.setX(posX - STREAM_WIDTH);
				this.setY(posY - STREAM_WIDTH);
			}

			this.setWidth(STREAM_WIDTH);
			this.setHeight(STREAM_WIDTH);

			break;

		case DOWN:

			if (this.direction == WaterDirection.LEFT) {

				this.setX(posX);
				this.setY(posY);

			} else {

				this.setX(posX - STREAM_WIDTH);
				this.setY(posY);
			}

			this.setWidth(STREAM_WIDTH);
			this.setHeight(-STREAM_WIDTH);

			break;

		case LEFT:

			if (this.direction == WaterDirection.UP) {

				this.setX(posX);
				this.setY(posY - STREAM_WIDTH);

			} else {

				this.setX(posX);
				this.setY(posY);
			}

			this.setWidth(-STREAM_WIDTH);
			this.setHeight(STREAM_WIDTH);

			break;

		case RIGHT:

			if (this.direction == WaterDirection.UP) {

				this.setX(posX - STREAM_WIDTH);
				this.setY(posY - STREAM_WIDTH);

			} else {

				this.setX(posX - STREAM_WIDTH);
				this.setY(posY);
			}

			this.setWidth(STREAM_WIDTH);
			this.setHeight(STREAM_WIDTH);

			break;

		default:
			break;
		}

		this.direction = direction;
	}

	private void setPositionDeltaAndChangeDirection(float positionDelta, boolean xAxis, int increment,
			WaterDirection direction) {
		if (xAxis)
			posX = currentBlock.getUnscaledX() + positionDelta;
		else
			posY = currentBlock.getUnscaledY() + positionDelta;

		changeDirection(direction);
		squigglyCounter += increment;
	}
	
	private void setPositionDeltaAndChangeDirection(float positionDeltaX, float positionDeltaY, boolean xAxis, int increment,
			WaterDirection direction) {
		posX = currentBlock.getUnscaledX() + positionDeltaX;
		posY = currentBlock.getUnscaledY() + positionDeltaY;
		
		posX = (posX) + (int) ((posX - (int) posX) * 32) / 32;
		posY = (posY) + (int) ((posY - (int) posY) * 32) / 32;
		
		cacheWaterBlock();
		
		switch (direction)
		{
		case DOWN:
			setX(posX - STREAM_WIDTH / 2);
			setY(posY);
			
			setWidth(STREAM_WIDTH);
			setHeight(0);
			break;
			
		case LEFT:
			setX(posX);
			setY(posY - STREAM_WIDTH / 2);
			
			setWidth(0);
			setHeight(STREAM_WIDTH);
			break;
			
		case RIGHT:
			setX(posX);
			setY(posY - STREAM_WIDTH / 2);
			
			setWidth(0);
			setHeight(STREAM_WIDTH);
			break;
			
		case UP:
			setX(posX - STREAM_WIDTH / 2);
			setY(posY);
			
			setWidth(STREAM_WIDTH);
			setHeight(0);
			break;
			
		default:
			break;
		}
		
		gameScene.gameUI.scoreManager.score += 75;
		
		//squigglyCounter += increment;
	}

	private void checkForLoops() {
		
		if (currentBlock.type == BuildingBlockType.TELEPORT_CROSS)
			return;
		
		for (CachedWaterBlock wb : water) {

			if (currentBlock.looped)
				continue;

			if ((wb.getUnscaledX() + wb.getUnscaledWidth()) > wb.getUnscaledX()) {

				if ((wb.getUnscaledY() + wb.getUnscaledHeight()) > wb.getUnscaledY()) {

					if (posX > wb.getUnscaledX() && posX < wb.getUnscaledX() + wb.getUnscaledWidth()
							&& posY > wb.getUnscaledY() && posY < wb.getUnscaledY() + wb.getUnscaledHeight()) {
						gameScene.gameUI.loopsManager.loops++;
						gameScene.gameUI.scoreManager.score += 50;
						currentBlock.looped = true;
					}

				} else {

					if (posX > wb.getUnscaledX() && posX < wb.getUnscaledX() + wb.getUnscaledWidth()
							&& posY < wb.getUnscaledY() && posY > wb.getUnscaledY() + wb.getUnscaledHeight()) {
						gameScene.gameUI.loopsManager.loops++;
						gameScene.gameUI.scoreManager.score += 50;
						currentBlock.looped = true;
					}
				}

			} else {

				if ((wb.getUnscaledY() + wb.getUnscaledHeight()) > wb.getUnscaledY()) {

					if (posX < wb.getUnscaledX() && posX > wb.getUnscaledX() + wb.getUnscaledWidth()
							&& posY > wb.getUnscaledY() && posY < wb.getUnscaledY() + wb.getUnscaledHeight()) {
						gameScene.gameUI.loopsManager.loops++;
						gameScene.gameUI.scoreManager.score += 50;
						currentBlock.looped = true;

					} else {

						if (posX < wb.getUnscaledX() && posX > wb.getUnscaledX() + wb.getUnscaledWidth()
								&& posY < wb.getUnscaledY() && posY > wb.getUnscaledY() + wb.getUnscaledHeight()) {
							gameScene.gameUI.loopsManager.loops++;
							gameScene.gameUI.scoreManager.score += 50;
							currentBlock.looped = true;
						}
					}
				}
			}
		}
	}

	public static WaterDirection invertWaterDirection(WaterDirection direction) {
		WaterDirection invertedWaterDirection;

		switch (direction) {
		case DOWN:
			invertedWaterDirection = WaterDirection.UP;
			break;

		case LEFT:
			invertedWaterDirection = WaterDirection.RIGHT;
			break;

		case RIGHT:
			invertedWaterDirection = WaterDirection.LEFT;
			break;

		case UP:
			invertedWaterDirection = WaterDirection.DOWN;
			break;

		default:
			invertedWaterDirection = null;
			break;
		}

		return invertedWaterDirection;
	}

	public void reset() {

		if (gameScene.emitter.typeIndex == 0) {

			direction = WaterDirection.LEFT;

			this.setX(gameScene.emitter.getUnscaledX() + 0.25f);
			this.setWidth(-0.125f);
			positiveDirection = false;

		} else {

			direction = WaterDirection.RIGHT;

			this.setX(gameScene.emitter.getUnscaledX() + 0.75f);
			this.setWidth(0.125f);
			positiveDirection = true;
		}

		this.setY(gameScene.emitter.getUnscaledY() + 0.5f - STREAM_WIDTH / 2);
		this.setHeight(STREAM_WIDTH);

		squigglyCounter = 0;

		newBlock = false;
		running = false;

		water.clear();
	}

	@Override
	public void act(float delta) {

		if (gameScene.gameState != GameState.RUNNING)
			return;

		posX = (this.getUnscaledX() + this.getUnscaledWidth());
		posY = (this.getUnscaledY() + this.getUnscaledHeight());

		BuildingBlock previousBlock = currentBlock;
		if (posX >= 2 && posX <= 17 && posY >= 2 && posY <= 14) {

			if (gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)] != null && currentBlock != null)
				if (!gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)].equals(currentBlock))
					newBlock = true;
				else
					newBlock = false;

			currentBlock = gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)];

		} else {

			currentBlock = null;
		}

		if (currentBlock == null || currentBlock.beingReplaced) {
			running = false;
			gameScene.setLevelEnded(true);
		}

		if (!running)
			return;

		if (newBlock && ((previousBlock.type == BuildingBlockType.L_BATHTUB && currentBlock.type == BuildingBlockType.R_BATHTUB) ||
				(previousBlock.type == BuildingBlockType.R_BATHTUB && currentBlock.type == BuildingBlockType.L_BATHTUB)))
		{
			gameScene.gameUI.scoreManager.score += 300;
		}
		
		if (newBlock && previousBlock.type != BuildingBlockType.TELEPORT_CROSS && currentBlock.type == BuildingBlockType.TELEPORT_CROSS)
			canTeleport = true;
		
		/***************************************************************/

		if (newBlock && currentBlock.turningPoints.size() > 0)
			if (direction == currentBlock.turningPoints.get(0).previousWaterDirection) {
				positiveDirection = true;
				squigglyCounter = 0;
			} else if (direction == invertWaterDirection(
					currentBlock.turningPoints.get(currentBlock.turningPoints.size() - 1).targetWaterDirection)) {
				positiveDirection = false;
				squigglyCounter = currentBlock.turningPoints.size() - 1;
			}

		if (newBlock)
			if (!currentBlock.hasDirectionalEntrace(direction)) {
				running = false;
				gameScene.setLevelEnded(true);
			} else {
				currentBlock.watered = true;
			}

		switch (direction) {
		case DOWN:
			this.setHeight(getUnscaledHeight() - delta * speed);
			break;

		case LEFT:
			this.setWidth(getUnscaledWidth() - delta * speed);
			break;

		case RIGHT:
			this.setWidth(getUnscaledWidth() + delta * speed);
			break;

		case UP:
			this.setHeight(getUnscaledHeight() + delta * speed);
			break;

		default:
			break;
		}

		// TODO actually perform teleporting
		if (currentBlock.teleportPoint != null && canTeleport) {
			TeleportPoint teleportPoint = currentBlock.teleportPoint;

			switch (direction) {
			case DOWN:
				if (posY <= currentBlock.getUnscaledY() + 0.5f)
				{
					setPositionDeltaAndChangeDirection(teleportPoint.teleportX - teleportPoint.x + 0.5f, teleportPoint.teleportY - teleportPoint.y + 0.5f, false, 0, direction);
					canTeleport = false;
				}
				break;

			case LEFT:
				if (posX <= currentBlock.getUnscaledX() + 0.5f)
				{
					setPositionDeltaAndChangeDirection(teleportPoint.teleportX - teleportPoint.x + 0.5f, teleportPoint.teleportY - teleportPoint.y + 0.5f, false, 0, direction);
					canTeleport = false;
				}
				break;

			case RIGHT:
				if (posX >= currentBlock.getUnscaledX() + 0.5f)
				{
					setPositionDeltaAndChangeDirection(teleportPoint.teleportX - teleportPoint.x + 0.5f, teleportPoint.teleportY - teleportPoint.y + 0.5f, false, 0, direction);
					canTeleport = false;
				}
				break;

			case UP:
				if (posY >= currentBlock.getUnscaledY() + 0.5f)
				{
					setPositionDeltaAndChangeDirection(teleportPoint.teleportX - teleportPoint.x + 0.5f, teleportPoint.teleportY - teleportPoint.y + 0.5f, false, 0, direction);
					canTeleport = false;
				}
				break;

			default:
				break;
			}
		}
		
		if ((squigglyCounter > -1 && squigglyCounter < currentBlock.turningPoints.size())
				&& currentBlock.turningPoints.size() > 0) {
			TurningPoint currentTurningPoint = currentBlock.turningPoints.get(squigglyCounter);

			switch (direction) {
			case DOWN:
				if (posY <= currentBlock.getUnscaledY() + currentTurningPoint.y - STREAM_WIDTH / 2)
					setPositionDeltaAndChangeDirection(currentTurningPoint.y - STREAM_WIDTH / 2, false,
							positiveDirection ? 1 : -1, positiveDirection ? currentTurningPoint.targetWaterDirection
									: invertWaterDirection(currentTurningPoint.previousWaterDirection));
				break;

			case LEFT:
				if (posX <= currentBlock.getUnscaledX() + currentTurningPoint.x - STREAM_WIDTH / 2)
					setPositionDeltaAndChangeDirection(currentTurningPoint.x - STREAM_WIDTH / 2, true,
							positiveDirection ? 1 : -1, positiveDirection ? currentTurningPoint.targetWaterDirection
									: invertWaterDirection(currentTurningPoint.previousWaterDirection));
				break;

			case RIGHT:
				if (posX >= currentBlock.getUnscaledX() + currentTurningPoint.x + STREAM_WIDTH / 2)
					setPositionDeltaAndChangeDirection(currentTurningPoint.x + STREAM_WIDTH / 2, true,
							positiveDirection ? 1 : -1, positiveDirection ? currentTurningPoint.targetWaterDirection
									: invertWaterDirection(currentTurningPoint.previousWaterDirection));
				break;

			case UP:
				if (posY >= currentBlock.getUnscaledY() + currentTurningPoint.y + STREAM_WIDTH / 2)
					setPositionDeltaAndChangeDirection(currentTurningPoint.y + STREAM_WIDTH / 2, false,
							positiveDirection ? 1 : -1, positiveDirection ? currentTurningPoint.targetWaterDirection
									: invertWaterDirection(currentTurningPoint.previousWaterDirection));
				break;

			default:
				break;
			}
		}

		checkForLoops();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();
		batch.begin();

		for (CachedWaterBlock wb : water)
			batch.draw(wb.getSprite("water"), wb.getX(), wb.getY(), wb.getWidth(), wb.getHeight());

		batch.draw(getSprite("water"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public enum WaterDirection {

		UP, DOWN, LEFT, RIGHT
	}

	private class CachedWaterBlock extends Entity {
		public CachedWaterBlock(float x, float y, float sizeX, float sizeY) {
			super(x, y, sizeX, sizeY);
			addSprite("Water.png", "water");
		}

		@Override
		public void act(float delta) {
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {
		}
	}
}
