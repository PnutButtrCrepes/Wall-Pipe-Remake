package com.crepes.butter.peanut.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.crepes.butter.peanut.BlockField;
import com.crepes.butter.peanut.LevelTransitionManager;
import com.crepes.butter.peanut.Scene;
import com.crepes.butter.peanut.SoundGenerator;
import com.crepes.butter.peanut.Water;
import com.crepes.butter.peanut.blocks.BuildingBlock;
import com.crepes.butter.peanut.blocks.WaterEmitter;
import com.crepes.butter.peanut.ui.Clock;
import com.crepes.butter.peanut.ui.GameUI;
import com.crepes.butter.peanut.ui.Leaderboard;
import com.crepes.butter.peanut.ui.LevelCount;
import com.crepes.butter.peanut.ui.NextBlocks;
import com.crepes.butter.peanut.ui.Score;
import com.crepes.butter.peanut.ui.TitleInformation;

public class GameScene extends Scene implements InputProcessor
{

    public float screenWidthRatio;
    public float screenHeightRatio;

    public int xIndex;
    public int yIndex;

    public boolean mouseGrabbed;

    private boolean levelStarted;
    private boolean paused;
    private boolean levelEnded;

    private float pausedMouseX;
    private float pausedMouseY;

    public int levelCount;

    public BuildingBlock placingBlock;

    public WaterEmitter emitter;

    public TitleInformation tManager;
    public BlockField bfManager;
    public NextBlocks nbManager;
    public Clock clockManager;
    public LevelCount lcManager;
    public Score scoreManager;
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

	tManager = new TitleInformation(this);
	bfManager = new BlockField(this);
	nbManager = new NextBlocks(this);
	clockManager = new Clock(this);

	emitter = new WaterEmitter(this);

	gameUI = new GameUI(this);
	lcManager = new LevelCount(this);
	scoreManager = new Score(this);
	water = new Water(this);

	levelManager = new LevelTransitionManager(this);

	leaderboardManager = new Leaderboard();

	addActors();

	levelStarted = false;
	paused = false;
	levelEnded = false;

	levelCount = 0;

	mouseGrabbed = false;

	levelCount++;
	gameUI.reset();
    }

    @Override
    public void addActors()
    {

	this.addActor(emitter);

	this.addActor(tManager);
	this.addActor(bfManager);
	this.addActor(nbManager);

	this.addActor(clockManager);
	clockManager.running = true;

	this.addActor(gameUI);
	this.addActor(lcManager);
	this.addActor(scoreManager);

	this.addActor(water);

	this.addActor(levelManager);

	this.addActor(leaderboardManager);
    }

    public void totalReset()
    {

	levelCount = 1;
	scoreManager.score = 0;

	levelStarted = false;
	paused = false;
	levelEnded = false;

	mouseGrabbed = false;

	this.getActors().removeRange(13, this.getActors().size - 1);

	bfManager.reset();
	nbManager.reset();

	emitter.reset();

	clockManager.reset();
	gameUI.reset();

	lcManager.reset();
	scoreManager.reset();

	water.reset();

	levelManager.reset();
    }

    public void levelInit()
    {

	levelCount++;

	levelStarted = false;
	paused = false;
	levelEnded = false;

	mouseGrabbed = false;

	this.getActors().removeRange(12, this.getActors().size - 1);

	bfManager.reset();
	nbManager.reset();

	emitter.reset();

	clockManager.reset();
	gameUI.reset();

	lcManager.reset();
	scoreManager.reset();

	water.reset();

	levelManager.reset();
    }

    public void togglePause()
    {

	if (isLevelStarted() && !isLevelEnded())
	{
	    paused = !paused;

	    pausedMouseX = Gdx.input.getX();
	    pausedMouseY = Gdx.input.getY();

	    if (paused)
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

	return levelStarted;
    }

    public void setLevelStarted(boolean bool)
    {

	levelStarted = bool;
    }

    public boolean isPaused()
    {

	return paused;
    }

    public boolean isLevelEnded()
    {

	return levelEnded;
    }

    public void setLevelEnded(boolean bool)
    {

	levelEnded = bool;
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
	    levelStarted = true;

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
}