package com.crepes.butter.peanut;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.scenes.GameScene;
import com.crepes.butter.peanut.scenes.GameScene.GameState;

public class Water extends Entity
{

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

    public ArrayList<CachedWaterBlock> water;
    public CachedWaterBlock placeHolder;

    public Water(GameScene gameScene)
    {

	super(0f, 0f, 0f, 0f);

	this.gameScene = gameScene;

	if (gameScene.emitter.typeIndex == 0)
	{

	    direction = WaterDirection.LEFT;

	    this.setX(gameScene.emitter.getUnscaledX() + 0.25f);
	    this.setWidth(-0.125f);

	} else
	{

	    direction = WaterDirection.RIGHT;

	    this.setX(gameScene.emitter.getUnscaledX() + 0.75f);
	    this.setWidth(0.125f);
	}

	this.setY(gameScene.emitter.getUnscaledY() + 0.5f - 3f / 32f);
	this.setHeight(6f / 32f);

	squigglyCounter = 0;

	newBlock = false;

	speed = 0.5f;

	addSprite("Water.png", "water");

	water = new ArrayList<CachedWaterBlock>();
    }

    private void cacheWaterBlock()
    {

	placeHolder = new CachedWaterBlock(getUnscaledX(), getUnscaledY(), getUnscaledWidth(), getUnscaledHeight());
	gameScene.addActor(placeHolder);
	water.add(placeHolder);
    }

    private void changeDirection(WaterDirection direction)
    {

	posX = (posX) + (int) ((posX - (int) posX) * 32) / 32;
	posY = (posY) + (int) ((posY - (int) posY) * 32) / 32;

	setWidth(posX - getUnscaledX());
	setHeight(posY - getUnscaledY());

	cacheWaterBlock();

	switch (direction)
	{

	case UP:

	    if (this.direction == WaterDirection.LEFT)
	    {

		this.setX(posX);
		this.setY(posY - 6f / 32f);

	    } else
	    {

		this.setX(posX - 6f / 32f);
		this.setY(posY - 6f / 32f);
	    }

	    this.setWidth(6f / 32f);
	    this.setHeight(6f / 32f);

	    break;

	case DOWN:

	    if (this.direction == WaterDirection.LEFT)
	    {

		this.setX(posX);
		this.setY(posY);

	    } else
	    {

		this.setX(posX - 6f / 32f);
		this.setY(posY);
	    }

	    this.setWidth(6f / 32f);
	    this.setHeight(-6f / 32f);

	    break;

	case LEFT:

	    if (this.direction == WaterDirection.UP)
	    {

		this.setX(posX);
		this.setY(posY - 6f / 32f);

	    } else
	    {

		this.setX(posX);
		this.setY(posY);
	    }

	    this.setWidth(-6f / 32f);
	    this.setHeight(6f / 32f);

	    break;

	case RIGHT:

	    if (this.direction == WaterDirection.UP)
	    {

		this.setX(posX - 6f / 32f);
		this.setY(posY - 6f / 32f);

	    } else
	    {

		this.setX(posX - 6f / 32f);
		this.setY(posY);
	    }

	    this.setWidth(6f / 32f);
	    this.setHeight(6f / 32f);

	    break;

	default:
	    break;
	}

	this.direction = direction;
    }

    private void setPositionDeltaAndChangeDirection(float positionDelta, boolean xAxis, int increment,
	    WaterDirection direction)
    {
	if (xAxis)
	    posX = currentBlock.getUnscaledX() + positionDelta;
	else
	    posY = currentBlock.getUnscaledY() + positionDelta;

	changeDirection(direction);
	squigglyCounter += increment;
    }

    private void checkForLoops()
    {
	for (CachedWaterBlock wb : water)
	{

	    if (wb.looped)
		continue;

	    if ((wb.getUnscaledX() + wb.getUnscaledWidth()) > wb.getUnscaledX())
	    {

		if ((wb.getUnscaledY() + wb.getUnscaledHeight()) > wb.getUnscaledY())
		{

		    if (posX > wb.getUnscaledX() && posX < wb.getUnscaledX() + wb.getUnscaledWidth()
			    && posY > wb.getUnscaledY() && posY < wb.getUnscaledY() + wb.getUnscaledHeight())
		    {
			gameScene.gameUI.loopsManager.loops++;
			wb.looped = true;
		    }

		} else
		{

		    if (posX > wb.getUnscaledX() && posX < wb.getUnscaledX() + wb.getUnscaledWidth()
			    && posY < wb.getUnscaledY() && posY > wb.getUnscaledY() + wb.getUnscaledHeight())
		    {
			gameScene.gameUI.loopsManager.loops++;
			wb.looped = true;
		    }
		}

	    } else
	    {

		if ((wb.getUnscaledY() + wb.getUnscaledHeight()) > wb.getUnscaledY())
		{

		    if (posX < wb.getUnscaledX() && posX > wb.getUnscaledX() + wb.getUnscaledWidth()
			    && posY > wb.getUnscaledY() && posY < wb.getUnscaledY() + wb.getUnscaledHeight())
		    {
			gameScene.gameUI.loopsManager.loops++;
			wb.looped = true;

		    } else
		    {

			if (posX < wb.getUnscaledX() && posX > wb.getUnscaledX() + wb.getUnscaledWidth()
				&& posY < wb.getUnscaledY() && posY > wb.getUnscaledY() + wb.getUnscaledHeight())
			{
			    gameScene.gameUI.loopsManager.loops++;
			    wb.looped = true;
			}
		    }
		}
	    }
	}
    }

    private WaterDirection invertWaterDirection(WaterDirection direction)
    {
	WaterDirection invertedWaterDirection;

	switch (direction)
	{
	case DOWN:
	    invertedWaterDirection = WaterDirection.UP;

	case LEFT:
	    invertedWaterDirection = WaterDirection.RIGHT;

	case RIGHT:
	    invertedWaterDirection = WaterDirection.LEFT;

	case UP:
	    invertedWaterDirection = WaterDirection.DOWN;

	default:
	    invertedWaterDirection = null;
	    break;
	}

	return invertedWaterDirection;
    }

    public void reset()
    {

	if (gameScene.emitter.typeIndex == 0)
	{

	    direction = WaterDirection.LEFT;

	    this.setX(gameScene.emitter.getUnscaledX() + 0.25f);
	    this.setWidth(-0.125f);
	    positiveDirection = false;

	} else
	{

	    direction = WaterDirection.RIGHT;

	    this.setX(gameScene.emitter.getUnscaledX() + 0.75f);
	    this.setWidth(0.125f);
	    positiveDirection = true;
	}

	this.setY(gameScene.emitter.getUnscaledY() + 0.5f - 3f / 32f);
	this.setHeight(6f / 32f);

	squigglyCounter = 0;

	newBlock = false;
	running = false;

	water.clear();
    }

    @Override
    public void act(float delta)
    {

	if (gameScene.gameState != GameState.RUNNING)
	    return;

	posX = (this.getUnscaledX() + this.getUnscaledWidth());
	posY = (this.getUnscaledY() + this.getUnscaledHeight());

	if (posX >= 2 && posX <= 17 && posY >= 2 && posY <= 14)
	{

	    if (gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)] != null && currentBlock != null)
		if (!gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)].equals(currentBlock))
		{
		    newBlock = true;
		    squigglyCounter = 0;

		} else
		{

		    newBlock = false;
		}

	    currentBlock = gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)];

	} else
	{

	    currentBlock = null;
	}

	if (currentBlock == null || currentBlock.beingReplaced)
	{
	    running = false;
	    gameScene.setLevelEnded(true);
	}

	if (!running)
	    return;

	/***************************************************************/

	if (newBlock)
	    switch (direction)
	    {
	    case DOWN:
	    case LEFT:
		positiveDirection = false;
		break;

	    case RIGHT:
	    case UP:
		positiveDirection = true;
		break;
	    }

	switch (direction)
	{
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
	}

	if (currentBlock.type != null)
	    switch (currentBlock.type)
	    {
	    case HSQUIGGLY:
		
		// TODO WORKING, EXTEND TO OTHER BLOCKS
		
		if (squigglyCounter == currentBlock.negativeToPositiveTurningPoints.size())
		    break;
		
		if (positiveDirection)
		{
		    if (currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).x > 0)
		    {
			if(direction == WaterDirection.RIGHT || direction == WaterDirection.UP)
			{
			if (posX >= currentBlock.getUnscaledX() + currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).x)
			    setPositionDeltaAndChangeDirection(currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).x, true, 1, currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
			else
			{
			    if (posX <= currentBlock.getUnscaledX() + currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).x)
				    setPositionDeltaAndChangeDirection(currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).x, true, 1, currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
		    } else
		    {
			if(direction == WaterDirection.RIGHT || direction == WaterDirection.UP)
			{
			if (posY >= currentBlock.getUnscaledY() + currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).y)
			    setPositionDeltaAndChangeDirection(currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).y, false, 1, currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
			else
			{
			    if (posY <= currentBlock.getUnscaledY() + currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).y)
				    setPositionDeltaAndChangeDirection(currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).y, false, 1, currentBlock.negativeToPositiveTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
		    }

		} else
		{
		    if (currentBlock.reverseTurningPoints.get(squigglyCounter).x > 0)
		    {
			if(direction == WaterDirection.RIGHT || direction == WaterDirection.UP)
			{
			if (posX >= currentBlock.getUnscaledX() + currentBlock.reverseTurningPoints.get(squigglyCounter).x)
			    setPositionDeltaAndChangeDirection(currentBlock.reverseTurningPoints.get(squigglyCounter).x, true, 1, currentBlock.reverseTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
			else
			{
			    if (posX <= currentBlock.getUnscaledX() + currentBlock.reverseTurningPoints.get(squigglyCounter).x)
				    setPositionDeltaAndChangeDirection(currentBlock.reverseTurningPoints.get(squigglyCounter).x, true, 1, currentBlock.reverseTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
		    } else
		    {
			if(direction == WaterDirection.RIGHT || direction == WaterDirection.UP)
			{
			if (posY >= currentBlock.getUnscaledY() + currentBlock.reverseTurningPoints.get(squigglyCounter).y)
			    setPositionDeltaAndChangeDirection(currentBlock.reverseTurningPoints.get(squigglyCounter).y, false, 1, currentBlock.reverseTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
			else
			{
			    if (posY <= currentBlock.getUnscaledY() + currentBlock.reverseTurningPoints.get(squigglyCounter).y)
				    setPositionDeltaAndChangeDirection(currentBlock.reverseTurningPoints.get(squigglyCounter).y, false, 1, currentBlock.reverseTurningPoints.get(squigglyCounter).targetWaterDirection);
			}
		    }
		}
		break;

	    case VSQUIGGLY:
		break;

	    default:
		break;
	    }

	/***************************************************************/

	/*
	 * switch (direction) {
	 * 
	 * case UP:
	 * 
	 * this.setHeight(getUnscaledHeight() + delta * speed);
	 * 
	 * if (newBlock) if (!currentBlock.hasDownEntrance) {
	 * 
	 * running = false; gameScene.setLevelEnded(true);
	 * 
	 * } else {
	 * 
	 * currentBlock.watered = true; }
	 * 
	 * if (currentBlock.type != null) switch (currentBlock.type) {
	 * 
	 * case HSQUIGGLY:
	 * 
	 * if (squigglyCounter == 1 && posY >= currentBlock.getUnscaledY() + 27f / 32f)
	 * setPositionDeltaAndChangeDirection(27f / 32f, false, 1,
	 * WaterDirection.RIGHT); else if (squigglyCounter == 3 && posY >=
	 * currentBlock.getUnscaledY() + 27f / 32f)
	 * setPositionDeltaAndChangeDirection(27f / 32f, false, 1, WaterDirection.LEFT);
	 * else if (squigglyCounter == 5 && posY >= currentBlock.getUnscaledY() + 19f /
	 * 32f) setPositionDeltaAndChangeDirection(19f / 32f, false, 1,
	 * WaterDirection.RIGHT);
	 * 
	 * break;
	 * 
	 * case VSQUIGGLY:
	 * 
	 * if (squigglyCounter == 0 && posY >= currentBlock.getUnscaledY() + 14f / 32f)
	 * setPositionDeltaAndChangeDirection(14f / 32f, false, 1, WaterDirection.LEFT);
	 * else if (squigglyCounter == 2 && posY >= currentBlock.getUnscaledY() + 24f /
	 * 32f) setPositionDeltaAndChangeDirection(24f / 32f, false, 1,
	 * WaterDirection.RIGHT);
	 * 
	 * break;
	 * 
	 * default:
	 * 
	 * if (posY >= currentBlock.getUnscaledY() + 0.5f + 3f / 32f) {
	 * 
	 * posY = currentBlock.getUnscaledY() + 0.5f + 3f / 32f;
	 * 
	 * if (!currentBlock.hasUpExit) if (currentBlock.hasLeftExit)
	 * changeDirection(WaterDirection.LEFT); else
	 * changeDirection(WaterDirection.RIGHT); }
	 * 
	 * break; }
	 * 
	 * break;
	 * 
	 * case DOWN:
	 * 
	 * this.setHeight(getUnscaledHeight() - delta * speed);
	 * 
	 * if (newBlock) if (!currentBlock.hasUpEntrance) {
	 * 
	 * running = false; gameScene.setLevelEnded(true);
	 * 
	 * } else {
	 * 
	 * currentBlock.watered = true; }
	 * 
	 * if (currentBlock.type != null) switch (currentBlock.type) {
	 * 
	 * case HSQUIGGLY:
	 * 
	 * if (squigglyCounter == 1 && posY <= currentBlock.getUnscaledY() + 5f / 32f)
	 * setPositionDeltaAndChangeDirection(5f / 32f, false, 1, WaterDirection.LEFT);
	 * else if (squigglyCounter == 3 && posY <= currentBlock.getUnscaledY() + 5f /
	 * 32f) setPositionDeltaAndChangeDirection(5f / 32f, false, 1,
	 * WaterDirection.RIGHT); else if (squigglyCounter == 5 && posY <=
	 * currentBlock.getUnscaledY() + 13f / 32f)
	 * setPositionDeltaAndChangeDirection(13f / 32f, false, 1, WaterDirection.LEFT);
	 * 
	 * break;
	 * 
	 * case VSQUIGGLY:
	 * 
	 * if (squigglyCounter == 0 && posY <= currentBlock.getUnscaledY() + 18f / 32f)
	 * setPositionDeltaAndChangeDirection(18f / 32f, false, -1,
	 * WaterDirection.LEFT); else if (squigglyCounter == -2 && posY <=
	 * currentBlock.getUnscaledY() + 8f / 32f) setPositionDeltaAndChangeDirection(8f
	 * / 32f, false, -1, WaterDirection.RIGHT);
	 * 
	 * break;
	 * 
	 * default:
	 * 
	 * if (posY <= currentBlock.getUnscaledY() + 0.5f - 3f / 32f) {
	 * 
	 * posY = currentBlock.getUnscaledY() + 0.5f - 3f / 32f;
	 * 
	 * if (!currentBlock.hasDownExit) if (currentBlock.hasLeftExit)
	 * changeDirection(WaterDirection.LEFT); else
	 * changeDirection(WaterDirection.RIGHT); }
	 * 
	 * break; }
	 * 
	 * break;
	 * 
	 * case LEFT:
	 * 
	 * this.setWidth(getUnscaledWidth() - delta * speed);
	 * 
	 * if (newBlock) if (!currentBlock.hasRightEntrance) {
	 * 
	 * running = false; gameScene.setLevelEnded(true);
	 * 
	 * } else {
	 * 
	 * currentBlock.watered = true; }
	 * 
	 * if (currentBlock.type != null) switch (currentBlock.type) {
	 * 
	 * case HSQUIGGLY:
	 * 
	 * if (squigglyCounter == 0 && posX <= currentBlock.getUnscaledX() + 21f / 32f)
	 * setPositionDeltaAndChangeDirection(21f / 32f, true, 1, WaterDirection.DOWN);
	 * else if (squigglyCounter == 2 && posX <= currentBlock.getUnscaledX() + 13f /
	 * 32f) setPositionDeltaAndChangeDirection(13f / 32f, true, 1,
	 * WaterDirection.UP); else if (squigglyCounter == 4 && posX <=
	 * currentBlock.getUnscaledX() + 5f / 32f) setPositionDeltaAndChangeDirection(5f
	 * / 32f, true, 1, WaterDirection.DOWN);
	 * 
	 * break;
	 * 
	 * case VSQUIGGLY:
	 * 
	 * if (squigglyCounter == 1 && posX <= currentBlock.getUnscaledX() + 4f / 32f)
	 * setPositionDeltaAndChangeDirection(4f / 32f, true, 1, WaterDirection.UP);
	 * else if (squigglyCounter == -1 && posX <= currentBlock.getUnscaledX() + 4f /
	 * 32f) setPositionDeltaAndChangeDirection(4f / 32f, true, -1,
	 * WaterDirection.DOWN);
	 * 
	 * break;
	 * 
	 * default:
	 * 
	 * if (posX <= currentBlock.getUnscaledX() + 0.5f - 3f / 32f) {
	 * 
	 * posX = currentBlock.getUnscaledX() + 0.5f - 3f / 32f;
	 * 
	 * if (!currentBlock.hasLeftExit) if (currentBlock.hasUpExit)
	 * changeDirection(WaterDirection.UP); else
	 * changeDirection(WaterDirection.DOWN); }
	 * 
	 * break; }
	 * 
	 * break;
	 * 
	 * case RIGHT:
	 * 
	 * this.setWidth(getUnscaledWidth() + delta * speed);
	 * 
	 * if (newBlock) if (!currentBlock.hasLeftEntrance) {
	 * 
	 * running = false; gameScene.setLevelEnded(true);
	 * 
	 * } else {
	 * 
	 * currentBlock.watered = true; }
	 * 
	 * if (currentBlock.type != null) switch (currentBlock.type) {
	 * 
	 * case HSQUIGGLY:
	 * 
	 * if (squigglyCounter == 0 && posX >= currentBlock.getUnscaledX() + 11f / 32f)
	 * setPositionDeltaAndChangeDirection(11f / 32f, true, 1, WaterDirection.UP);
	 * else if (squigglyCounter == 2 && posX >= currentBlock.getUnscaledX() + 19f /
	 * 32f) setPositionDeltaAndChangeDirection(19f / 32f, true, 1,
	 * WaterDirection.DOWN); else if (squigglyCounter == 4 && posX >=
	 * currentBlock.getUnscaledX() + 27f / 32f)
	 * setPositionDeltaAndChangeDirection(27f / 32f, true, 1, WaterDirection.UP);
	 * 
	 * break;
	 * 
	 * case VSQUIGGLY:
	 * 
	 * if (squigglyCounter == 3 && posX >= currentBlock.getUnscaledX() + 19f / 32f)
	 * setPositionDeltaAndChangeDirection(19f / 32f, true, 1, WaterDirection.UP);
	 * else if (squigglyCounter == -3 && posX >= currentBlock.getUnscaledX() + 19f /
	 * 32f) setPositionDeltaAndChangeDirection(19f / 32f, true, -1,
	 * WaterDirection.DOWN);
	 * 
	 * break;
	 * 
	 * default:
	 * 
	 * if (posX >= currentBlock.getUnscaledX() + 0.5f + 3f / 32f) {
	 * 
	 * posX = currentBlock.getUnscaledX() + 0.5f + 3f / 32f;
	 * 
	 * if (!currentBlock.hasRightExit) if (currentBlock.hasUpExit)
	 * changeDirection(WaterDirection.UP); else
	 * changeDirection(WaterDirection.DOWN); }
	 * 
	 * break; }
	 * 
	 * break;
	 * 
	 * default: break; }
	 */

	checkForLoops();
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	for (CachedWaterBlock wb : water)
	    batch.draw(wb.getSprite("water"), wb.getX(), wb.getY(), wb.getWidth(), wb.getHeight());

	batch.draw(getSprite("water"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public enum WaterDirection
    {

	UP, DOWN, LEFT, RIGHT
    }

    private class CachedWaterBlock extends Entity
    {

	public boolean looped;

	public CachedWaterBlock(float x, float y, float sizeX, float sizeY)
	{
	    super(x, y, sizeX, sizeY);
	    addSprite("Water.png", "water");
	    looped = false;
	}
    }
}
