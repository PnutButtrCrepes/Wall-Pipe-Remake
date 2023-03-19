package com.crepes.butter.peanut.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crepes.butter.peanut.BlockField;
import com.crepes.butter.peanut.Leaderboard;
import com.crepes.butter.peanut.LevelTransitionManager;
import com.crepes.butter.peanut.NextBlocks;
import com.crepes.butter.peanut.Scene;
import com.crepes.butter.peanut.SoundGenerator;
import com.crepes.butter.peanut.Water;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.blocks.WaterEmitter;
import com.crepes.butter.peanut.ui.GameUI;
import com.crepes.butter.peanut.ui.LevelCount;

public class GameScene extends Scene implements InputProcessor
{

    public GameState gameState;
    
    public float screenWidthRatio;
    public float screenHeightRatio;

    public int xIndex;
    public int yIndex;

    public boolean mouseGrabbed;

    public boolean directions;
    public boolean quitDialog;

    private float pausedMouseX;
    private float pausedMouseY;

    public int levelCount;

    public BuildingBlock placingBlock;

    public WaterEmitter emitter;

    public BlockField bfManager;
    public NextBlocks nbManager;
    public LevelCount lcManager;
    public GameUI gameUI;

    public LevelTransitionManager levelManager;

    public Leaderboard leaderboardManager;

    public Water water;

    public GameScene(Viewport viewport)
    {

	super(viewport);

	this.viewport = viewport;

	screenWidthRatio = viewport.getScreenWidth() / viewport.getWorldWidth();
	screenHeightRatio = viewport.getScreenHeight() / viewport.getWorldHeight();

	this.backgroundName = "GameBackground";

	// TODO Emitter goes here, remember to move all this into an init function.

	bfManager = new BlockField(this);
	nbManager = new NextBlocks(this);

	emitter = new WaterEmitter(this);

	gameUI = new GameUI(this);
	lcManager = new LevelCount(this);
	water = new Water(this);

	levelManager = new LevelTransitionManager(this);

	leaderboardManager = new Leaderboard();

	addActors();

	gameState = GameState.NOT_STARTED;

	levelCount = 0;

	mouseGrabbed = false;

	levelCount++;
	gameUI.reset();
    }

    @Override
    public void addActors()
    {

	this.addActor(emitter);

	this.addActor(bfManager);
	this.addActor(nbManager);

	gameUI.clock.running = true;

	this.addActor(gameUI);
	this.addActor(lcManager);

	this.addActor(water);

	this.addActor(levelManager);

	this.addActor(leaderboardManager);
    }

    public void totalReset()
    {

	levelCount = 1;
	gameUI.scoreManager.score = 0;

	gameState = GameState.NOT_STARTED;

	mouseGrabbed = false;

	this.getActors().removeRange(13, this.getActors().size - 1);

	bfManager.reset();
	nbManager.reset();

	emitter.reset();

	gameUI.clock.reset();
	gameUI.reset();

	lcManager.reset();

	water.reset();

	levelManager.reset();
    }

    public void levelInit()
    {

	levelCount++;

	gameState = GameState.NOT_STARTED;

	mouseGrabbed = false;

	this.getActors().removeRange(12, this.getActors().size - 1);

	bfManager.reset();
	nbManager.reset();

	emitter.reset();

	gameUI.clock.reset();
	gameUI.reset();

	lcManager.reset();
	gameUI.scoreManager.reset();

	water.reset();

	levelManager.reset();
    }

    public void togglePause()
    {

	if (isLevelStarted() && !isLevelEnded())
	{
	    if(gameState == GameState.RUNNING)
		gameState = GameState.PAUSED;
	    else if (gameState == GameState.PAUSED)
		gameState = GameState.RUNNING;

	    pausedMouseX = Gdx.input.getX();
	    pausedMouseY = Gdx.input.getY();

	    if (gameState == GameState.PAUSED)
	    {

		Gdx.input.setCursorCatched(true);

	    } else
	    {

		Gdx.input.setCursorCatched(false);
	    }
	}
    }

    public boolean isLevelStarted()
    {

	if (gameState == GameState.NOT_STARTED)
	    return false;
	else
	    return true;
    }

    public boolean isPaused()
    {

	if (gameState == GameState.PAUSED)
	    return true;
	else
	    return false;
    }

    public boolean isLevelEnded()
    {

	if (gameState == GameState.LEVEL_ENDED)
	    return true;
	else
	    return false;
    }

    public void setLevelEnded(boolean bool)
    {

	gameState = GameState.LEVEL_ENDED;
    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character)
    {

	if (!isLevelEnded())
	{
	    if (character == 'p')
		togglePause();
	}
	else if (isLevelEnded() && !levelManager.hasSelectedInitials)
	{

	    if (character == '\r')
	    {
		levelManager.hasSelectedInitials = true;
	    }
	    else if (character == '\b')
	    {
		if (levelManager.initials[2] != null)
		    levelManager.initials[2] = null;
		else if (levelManager.initials[1] != null)
		    levelManager.initials[1] = null;
		else if (levelManager.initials[0] != null)
		    levelManager.initials[0] = null;
	    }
	    else if (Character.isAlphabetic(character))
	    {
		if (levelManager.initials[0] == null)
		    levelManager.initials[0] = Character.toUpperCase(character);
		else if (levelManager.initials[1] == null)
		    levelManager.initials[1] = Character.toUpperCase(character);
		else if (levelManager.initials[2] == null)
		    levelManager.initials[2] = Character.toUpperCase(character);
	    }

	} else if (isLevelEnded() && levelManager.hasSelectedInitials && !levelManager.hasViewedLeaderboard)
	{

	    if (character == '\r')
	    {

		levelManager.hasViewedLeaderboard = true;
	    }

	} else if (isLevelEnded() && levelManager.hasSelectedInitials && levelManager.hasViewedLeaderboard)
	{

	    if (character == 'y')
	    {

		totalReset();

	    } else if (character == 'n')
	    {

		System.exit(0);
	    }
	}

	return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

	if (!isLevelStarted())
	{

	    mouseGrabbed = true;
	    gameState = GameState.RUNNING;

	    resetMousePosition();

	} else if (isPaused())
	{

	    Gdx.input.setCursorPosition((int) pausedMouseX, (int) pausedMouseY);
	    togglePause();

	} else if (isLevelEnded())
	{

	} else
	{

	    xIndex = (int) (screenX / (screenWidthRatio * 32));
	    yIndex = (int) ((viewport.getScreenHeight() - screenY) / (screenHeightRatio * 32));

	    if (!bfManager.replacing)
	    {

		if (mouseGrabbed)
		{
		    SoundGenerator.playWave(SoundGenerator.constructPulse(200, 0.5f, 100, 0.25)); // this is the Wall Pipe placement sound
		    
		    placingBlock = nbManager.blockQueue[0];
		    nbManager.blockQueue[0].visible = false;
		    mouseGrabbed = false;

		} else
		{

		    if (xIndex > 1 && xIndex < 16 && yIndex > 1 && yIndex < 13)
		    {
			if (bfManager.blockField[xIndex - 2][yIndex - 2] == null)
			{
			    bfManager.addBlock(placingBlock, xIndex, yIndex);
			    nbManager.shiftBlocks();
			    mouseGrabbed = true;

			    Gdx.input.setCursorPosition((int) ((nbManager.getX() + 24) * screenWidthRatio),
				    (int) (viewport.getScreenHeight() - ((nbManager.getY() + 24) * screenHeightRatio)));

			} else if (!bfManager.blockField[xIndex - 2][yIndex - 2].watered)
			{

			    bfManager.replace(placingBlock, xIndex, yIndex);
			}
		    }
		}
	    }
	}

	return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {

	if (mouseGrabbed)
	    resetMousePosition();

	return false;
    }

    public void resetMousePosition()
    {

	Gdx.input.setCursorPosition((int) ((nbManager.getX() + 24) * screenWidthRatio),
		(int) (viewport.getScreenHeight() - ((nbManager.getY() + 24) * screenHeightRatio)));
    }
    
    public enum GameState
    {
	NOT_STARTED,
	RUNNING,
	PAUSED,
	LEVEL_ENDED
    }
}