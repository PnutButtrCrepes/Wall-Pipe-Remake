package com.crepes.butter.peanut;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

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

    public ArrayList<CachedWaterBlock> water;
    public CachedWaterBlock placeHolder;

    public Water(GameScene gameScene)
    {

	super(0f, 0f, 0f, 0f);

	this.gameScene = gameScene;

	if (gameScene.emitter.typeIndex == 0)
	{

	    direction = WaterDirection.LEFT;

	    this.setX(gameScene.emitter.getX() + 0.25f);
	    this.setWidth(-0.125f);

	} else
	{

	    direction = WaterDirection.RIGHT;

	    this.setX(gameScene.emitter.getX() + 0.75f);
	    this.setWidth(0.125f);
	}
	
	this.setY(gameScene.emitter.getY() + 0.5f - 3f / 32f);
	this.setHeight(6f / 32f);

	squigglyCounter = 0;

	newBlock = false;

	speed = 0.5f;

	this.sprite = new Sprite();
	this.texture = new Texture("Water.png");
	this.sprite.setTexture(texture);

	water = new ArrayList<CachedWaterBlock>();
    }

    public void reset()
    {

	if (gameScene.emitter.typeIndex == 0)
	{

	    direction = WaterDirection.LEFT;

	    this.setX(gameScene.emitter.getX() + 0.25f);
	    this.setWidth(-0.125f);

	} else
	{

	    direction = WaterDirection.RIGHT;

	    this.setX(gameScene.emitter.getX() + 0.75f);
	    this.setWidth(0.125f);
	}

	this.setY(gameScene.emitter.getY() + 0.5f - 3f / 32f);
	this.setHeight(6f / 32f);

	squigglyCounter = 0;

	newBlock = false;
	running = false;

	water.clear();
    }

    public void cacheWaterBlock()
    {

	placeHolder = new CachedWaterBlock(getX(), getY(), getWidth(), getHeight());
	gameScene.addActor(placeHolder);
	water.add(placeHolder);
    }

    public void changeDirection(WaterDirection direction)
    {

	posX = (posX) + (int) ((posX - (int) posX) * 32) / 32;
	posY = (posY) + (int) ((posY - (int) posY) * 32) / 32;

	setWidth(posX - getX());
	setHeight(posY - getY());

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

    @Override
    public void act(float delta)
    {

	if (!gameScene.isPaused() && gameScene.isLevelStarted())
	{

	    posX = this.getX() + this.getWidth();
	    posY = this.getY() + this.getHeight();

	    if (posX >= 2 && posX <= 17 && posY >= 2 && posY <= 14)
	    {

		if (gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)] != null && currentBlock != null)
		    if (!gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY - 2)].equals(currentBlock))
		    {

			// && !gameScene.bfManager.blockField[(int) (posX - 2)][(int) (posY -
			// 2)].equals(gameScene.emitter) TODO Why was this in here?

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

	    if (currentBlock == null)
	    {

		running = false;
		gameScene.setLevelEnded(true);

	    } else if (currentBlock.beingReplaced)
	    {

		running = false;
		gameScene.setLevelEnded(true);
	    }

	    if (running)
	    {

		switch (direction)
		{

		case UP:

		    this.setHeight(getHeight() + delta * speed);

		    if (newBlock)
			if (!currentBlock.hasDownEntrance)
			{

			    running = false;
			    gameScene.setLevelEnded(true);

			} else
			{

			    currentBlock.watered = true;
			}

		    if (currentBlock.type != null)
			switch (currentBlock.type)
			{

			case HSQUIGGLY:

			    if (squigglyCounter == 1 && posY >= currentBlock.getY() + 27f / 32f)
			    {

				posY = currentBlock.getY() + 27f / 32f;

				changeDirection(WaterDirection.RIGHT);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 3 && posY >= currentBlock.getY() + 27f / 32f)
			    {

				posY = currentBlock.getY() + 27f / 32f;

				changeDirection(WaterDirection.LEFT);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 5 && posY >= currentBlock.getY() + 19f / 32f)
			    {

				posY = currentBlock.getY() + 19f / 32f;

				changeDirection(WaterDirection.RIGHT);
				squigglyCounter++;
			    }

			    break;

			case VSQUIGGLY:

			    if (squigglyCounter == 0 && posY >= currentBlock.getY() + 14f / 32f)
			    {

				posY = currentBlock.getY() + 14f / 32f;

				changeDirection(WaterDirection.LEFT);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 2 && posY >= currentBlock.getY() + 24f / 32f)
			    {

				posY = currentBlock.getY() + 24f / 32f;

				changeDirection(WaterDirection.RIGHT);
				squigglyCounter++;
			    }

			    break;

			default:

			    if (posY >= currentBlock.getY() + 0.5f + 3f / 32f)
			    {

				posY = currentBlock.getY() + 0.5f + 3f / 32f;

				if (!currentBlock.hasUpExit)
				{

				    if (currentBlock.hasLeftExit)
					changeDirection(WaterDirection.LEFT);
				    else
					changeDirection(WaterDirection.RIGHT);
				}
			    }

			    break;
			}

		    break;

		case DOWN:

		    this.setHeight(getHeight() - delta * speed);

		    if (newBlock)
			if (!currentBlock.hasUpEntrance)
			{

			    running = false;
			    gameScene.setLevelEnded(true);

			} else
			{

			    currentBlock.watered = true;
			}

		    if (currentBlock.type != null)
			switch (currentBlock.type)
			{

			case HSQUIGGLY:

			    if (squigglyCounter == 1 && posY <= currentBlock.getY() + 5f / 32f)
			    {

				posY = currentBlock.getY() + 5f / 32f;

				changeDirection(WaterDirection.LEFT);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 3 && posY <= currentBlock.getY() + 5f / 32f)
			    {

				posY = currentBlock.getY() + 5f / 32f;

				changeDirection(WaterDirection.RIGHT);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 5 && posY <= currentBlock.getY() + 13f / 32f)
			    {

				posY = currentBlock.getY() + 13f / 32f;

				changeDirection(WaterDirection.LEFT);
				squigglyCounter++;
			    }

			    break;

			case VSQUIGGLY:

			    if (squigglyCounter == 0 && posY <= currentBlock.getY() + 18f / 32f)
			    {

				posY = currentBlock.getY() + 18f / 32f;

				changeDirection(WaterDirection.LEFT);
				squigglyCounter--;
			    }

			    if (squigglyCounter == -2 && posY <= currentBlock.getY() + 8f / 32f)
			    {

				posY = currentBlock.getY() + 8f / 32f;

				changeDirection(WaterDirection.RIGHT);
				squigglyCounter--;
			    }

			    break;

			default:

			    if (posY <= currentBlock.getY() + 0.5f - 3f / 32f)
			    {

				posY = currentBlock.getY() + 0.5f - 3f / 32f;

				if (!currentBlock.hasDownExit)
				{

				    if (currentBlock.hasLeftExit)
					changeDirection(WaterDirection.LEFT);
				    else
					changeDirection(WaterDirection.RIGHT);
				}
			    }

			    break;
			}

		    break;

		case LEFT:

		    this.setWidth(getWidth() - delta * speed);

		    if (newBlock)
			if (!currentBlock.hasRightEntrance)
			{

			    running = false;
			    gameScene.setLevelEnded(true);

			} else
			{

			    currentBlock.watered = true;
			}

		    if (currentBlock.type != null)
			switch (currentBlock.type)
			{

			case HSQUIGGLY:

			    if (squigglyCounter == 0 && posX <= currentBlock.getX() + 21f / 32f)
			    {

				posX = currentBlock.getX() + 21f / 32f;

				changeDirection(WaterDirection.DOWN);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 2 && posX <= currentBlock.getX() + 13f / 32f)
			    {

				posX = currentBlock.getX() + 13f / 32f;

				changeDirection(WaterDirection.UP);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 4 && posX <= currentBlock.getX() + 5f / 32f)
			    {

				posX = currentBlock.getX() + 5f / 32f;

				changeDirection(WaterDirection.DOWN);
				squigglyCounter++;
			    }

			    break;

			case VSQUIGGLY:

			    if (squigglyCounter == 1 && posX <= currentBlock.getX() + 4f / 32f)
			    {

				posX = currentBlock.getX() + 4f / 32f;

				changeDirection(WaterDirection.UP);
				squigglyCounter++;
			    }

			    if (squigglyCounter == -1 && posX <= currentBlock.getX() + 4f / 32f)
			    {

				posX = currentBlock.getX() + 4f / 32f;

				changeDirection(WaterDirection.DOWN);
				squigglyCounter--;
			    }

			    break;

			default:

			    if (posX <= currentBlock.getX() + 0.5f - 3f / 32f)
			    {

				posX = currentBlock.getX() + 0.5f - 3f / 32f;

				if (!currentBlock.hasLeftExit)
				{

				    if (currentBlock.hasUpExit)
					changeDirection(WaterDirection.UP);
				    else
					changeDirection(WaterDirection.DOWN);
				}
			    }

			    break;
			}

		    break;

		case RIGHT:

		    this.setWidth(getWidth() + delta * speed);

		    if (newBlock)
			if (!currentBlock.hasLeftEntrance)
			{

			    running = false;
			    gameScene.setLevelEnded(true);

			} else
			{

			    currentBlock.watered = true;
			}

		    if (currentBlock.type != null)
			switch (currentBlock.type)
			{

			case HSQUIGGLY:

			    if (squigglyCounter == 0 && posX >= currentBlock.getX() + 11f / 32f)
			    {

				posX = currentBlock.getX() + 11f / 32f;

				changeDirection(WaterDirection.UP);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 2 && posX >= currentBlock.getX() + 19f / 32f)
			    {

				posX = currentBlock.getX() + 19f / 32f;

				changeDirection(WaterDirection.DOWN);
				squigglyCounter++;
			    }

			    if (squigglyCounter == 4 && posX >= currentBlock.getX() + 27f / 32f)
			    {

				posX = currentBlock.getX() + 27f / 32f;

				changeDirection(WaterDirection.UP);
				squigglyCounter++;
			    }

			    break;

			case VSQUIGGLY:

			    if (squigglyCounter == 3 && posX >= currentBlock.getX() + 19f / 32f)
			    {

				posX = currentBlock.getX() + 19f / 32f;

				changeDirection(WaterDirection.UP);
				squigglyCounter++;
			    }

			    if (squigglyCounter == -3 && posX >= currentBlock.getX() + 19f / 32f)
			    {

				posX = currentBlock.getX() + 19f / 32f;

				changeDirection(WaterDirection.DOWN);
				squigglyCounter--;
			    }
			    break;

			default:

			    if (posX >= currentBlock.getX() + 0.5f + 3f / 32f)
			    {

				posX = currentBlock.getX() + 0.5f + 3f / 32f;

				if (!currentBlock.hasRightExit)
				{

				    if (currentBlock.hasUpExit)
					changeDirection(WaterDirection.UP);
				    else
					changeDirection(WaterDirection.DOWN);
				}
			    }

			    break;
			}

		    break;

		default:
		    break;
		}

		for (CachedWaterBlock wb : water)
		{

		    if (!wb.looped)
		    {

			if ((wb.getX() + wb.getWidth()) > wb.getX())
			{

			    if ((wb.getY() + wb.getHeight()) > wb.getY())
			    {

				if (posX > wb.getX() && posX < wb.getX() + wb.getWidth() && posY > wb.getY() && posY < wb.getY() + wb.getHeight())
				{
				    gameScene.gameUI.loopsManager.loops++;
				    wb.looped = true;
				}

			    } else
			    {

				if (posX > wb.getX() && posX < wb.getX() + wb.getWidth() && posY < wb.getY() && posY > wb.getY() + wb.getHeight())
				{
				    gameScene.gameUI.loopsManager.loops++;
				    wb.looped = true;
				}
			    }

			} else
			{

			    if ((wb.getY() + wb.getHeight()) > wb.getY())
			    {

				if (posX < wb.getX() && posX > wb.getX() + wb.getWidth() && posY > wb.getY() && posY < wb.getY() + wb.getHeight())
				{
				    gameScene.gameUI.loopsManager.loops++;
				    wb.looped = true;

				} else
				{

				    if (posX < wb.getX() && posX > wb.getX() + wb.getWidth() && posY < wb.getY() && posY > wb.getY() + wb.getHeight())
				    {
					gameScene.gameUI.loopsManager.loops++;
					wb.looped = true;
				    }
				}
			    }
			}
		    }
		}
	    }
	}
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	for (CachedWaterBlock wb : water)
	    batch.draw(wb.sprite, wb.getX(), wb.getY(), wb.getWidth(), wb.getHeight());

	batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
