package com.crepes.butter.peanut;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.scenes.GameScene;

public class Cutscene extends Entity
{
    public GameScene gameScene;
    public CutsceneType cutsceneType;
    public float cutsceneTimer;
    public boolean cutsceneTimerStarted;
    public ArrayList<Float> cutsceneTransitionLengths;
    public int cutsceneIndex;
    
    public Character[] initials;
    private int deleteBlockX;
    private int deleteBlockY;
    private boolean scoreAdded;
    
    public Cutscene(GameScene gameScene, CutsceneType cutsceneType)
    {
	super(0, 0, 0, 0);
	this.gameScene = gameScene;
	this.cutsceneType = cutsceneType;
	cutsceneTimer = 0;
	cutsceneTransitionLengths = new ArrayList<Float>();
	cutsceneIndex = 0;
	
	initials = new Character[3];
	deleteBlockX = -1;
	deleteBlockY = -1;
	scoreAdded = false;
	
	switch (this.cutsceneType)
	{
	case LEVEL_END:
	    cutsceneTransitionLengths.add(3f);
	    cutsceneTransitionLengths.add(3f);
	    break;
	    
	case MAIN_MENU:
	    break;
	    
	default:
	    break;
	}
    }
    
    @Override
    public void act(float delta)
    {
	if (cutsceneTransitionLengths.get(cutsceneIndex) == 0)
	    return;
	
	if (cutsceneTimerStarted)
	    cutsceneTimer += delta;
	
	if (cutsceneTimer >= cutsceneTransitionLengths.get(cutsceneIndex))
	{
	    cutsceneIndex++;
	    cutsceneTimer = 0;
	}
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
	switch (cutsceneType)
	{
	case LEVEL_END:
	    switch (cutsceneIndex)
	    {
	    case 0:
		displayLevelOverDialogue(batch);
		break;
		
	    case 1:
		if (gameScene.gameUI.loopsManager.loops > 3)
		    displayLoopBonusDialogue(batch);
		else
		    cutsceneIndex++;
		break;
		
	    case 2:
		if (cutsceneTimer > 0.25)
		    gameScene.gameUI.loopsManager.addBonus();
		break;
		
	    case 3:
		displayRemovingPipesDialogue(batch);
		break;
		
	    case 4:
		if (cutsceneTimer < 1)
		{

			drawDeleteBlockOutline(batch);

		}
		else if (cutsceneTimer > 1 && cutsceneTimer < 2)
		{

			deleteSelectedBlock(batch);

			selectBlockToBeDeleted(batch);

		}
		break;
		
	    case 5:
		if (gameScene.gameUI.scoreManager.score >= gameScene.gameUI.scoreNeededManager.scoreNeeded)
		    gameScene.levelInit();
		else
		    cutsceneIndex++;
		break;
		
	    case 6:
		drawText(batch, "GAME OVER", Color.WHITE, this.getX() + 50, this.getY() + 5, 1.1f);
		break;
		
	    case 7:
		if (((int) (gameScene.gameUI.scoreManager.score) < gameScene.leaderboardManager.leaderboardEntries[9].score)
			    || ((int) (gameScene.gameUI.scoreManager.score) == gameScene.leaderboardManager.leaderboardEntries[9].score
				    && gameScene.gameUI.levelCount.levelCount <= gameScene.leaderboardManager.leaderboardEntries[9].level))
		    cutsceneIndex++;
		
		displayTopTenDialogue(batch);
		
		if (cutsceneTimer > 3.5f)
		{

		    if (initials[0] == null)

			drawText(batch, "ENTER INITIALS: ", Color.BLACK, this.getX() + 10, this.getY() - 15, 0.7f);
		    else if (initials[1] == null)
			drawText(batch, "ENTER INITIALS: " + initials[0] + "", Color.BLACK, this.getX() + 10,
				this.getY() - 15, 0.7f);
		    else if (initials[2] == null)
			drawText(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "", Color.BLACK,
				this.getX() + 10, this.getY() - 15, 0.7f);
		    else
			drawText(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2], Color.BLACK,
				this.getX() + 10, this.getY() - 15, 0.7f);

		} else
		{
		    if (initials[0] == null)
			drawText(batch, "ENTER INITIALS: _", Color.BLACK, this.getX() + 10, this.getY() - 15, 0.7f);
		    else if (initials[1] == null)
			drawText(batch, "ENTER INITIALS: " + initials[0] + "_", Color.BLACK, this.getX() + 10,
				this.getY() - 15, 0.7f);
		    else if (initials[2] == null)
			drawText(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "_", Color.BLACK,
				this.getX() + 10, this.getY() - 15, 0.7f);
		    else
			drawText(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2], Color.BLACK,
				this.getX() + 10, this.getY() - 15, 0.7f);
		}
		    
		break;
		
	    case 8:
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

		long millis = System.currentTimeMillis();
		Date date = new Date(millis);

		if (!scoreAdded)
		{

		    for (int i = 0; i < 3; i++)
		    {

			if (initials[i] == null)
			    initials[i] = ' ';
		    }

		    gameScene.leaderboardManager.addScore(new LeaderboardEntry(
			    Character.toString(initials[0]) + Character.toString(initials[1])
				    + Character.toString(initials[2]),
			    (int) gameScene.gameUI.scoreManager.score, gameScene.gameUI.levelCount.levelCount,
			    formatter.format(date)));
		    scoreAdded = true;
		}

		gameScene.leaderboardManager.setVisible(true);
		
		break;
		
	    case 9:
		gameScene.leaderboardManager.setVisible(false);
		
		if (cutsceneTimer > 1)
			displayPlayAnotherGameDialogue(batch);
		break;
	    }
	    break;
	    
	case MAIN_MENU:
	    switch (cutsceneIndex)
	    {
	    
	    }
	    break;
	}
    }
    
    public enum CutsceneType
    {
	MAIN_MENU,
	LEVEL_END
    }
    
    public enum MainCutsceneStage
    {
	NATHAN_KEENAN,
	WALLPIPE,
	GAME_START
    }
    
    public enum LevelCutsceneStage
    {
	LEVEL_OVER,
	LOOP_BONUS_DISPLAY,
	LOOP_BONUS_ADD,
	REMOVING_PIPES_DISPLAY,
	REMOVING_PIPES_SELECT,
	REMOVING_PIPES_OUTLINE,
	REMOVING_PIPES_DELETE,
	ADVANCE_TO_NEXT_LEVEL,
	GAME_OVER,
	LEADERBOARD_ENTRY
    }
    
    private void displayLevelOverDialogue(Batch batch)
    {
	cutsceneTimerStarted = true;

	batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	batch.draw(getSprite("yellow"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
		this.getHeight() - 5);

	drawText(batch, "LEVEL " + String.valueOf((int) gameScene.gameUI.levelCount.levelCount) + " OVER",
		new Color(0.5f, 0f, 0f, 1), this.getX() + 40, this.getY() + 45, 0.9f);
    }
    
    private void displayLoopBonusDialogue(Batch batch)
    {
	batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5, this.getHeight() - 5);

	drawText(batch,
		    "BONUS: " + String.valueOf((int) gameScene.gameUI.loopsManager.loops)
			    + " LOOPS MADE\nADDING "
			    + String.valueOf((int) gameScene.gameUI.loopsManager.getBonus()) + " TO SCORE",
		    Color.WHITE, this.getX() + 20, this.getY() + 55, 0.7f);
    }
    
    private void displayRemovingPipesDialogue(Batch batch)
    {
	batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
		this.getHeight() - 5);

	drawText(batch, "REMOVING EMPTY PIPES", Color.WHITE, this.getX() + 18, this.getY() + 40, 0.7f);
    }
    
    private void selectBlockToBeDeleted(Batch batch)
    {
	Delete: for (int i = 0; i < 15; i++)
	    for (int j = 0; j < 12; j++)
		if (gameScene.bfManager.blockField[i][11 - j] != null
			&& !gameScene.bfManager.blockField[i][11 - j].watered)
		{

		    deleteBlockX = i;
		    deleteBlockY = 11 - j;

		    cutsceneTimer = 10.6f;
		    break Delete;
		}
    }
    
    private void deleteSelectedBlock(Batch batch)
    {
	if (deleteBlockX > -1 && deleteBlockY > -1)
	{

	    gameScene.bfManager.blockField[deleteBlockX][deleteBlockY] = null;

	    deleteBlockX = -1;
	    deleteBlockY = -1;

	    gameScene.gameUI.scoreManager.score -= 20;

	    if (gameScene.gameUI.scoreManager.score < 0)
		gameScene.gameUI.scoreManager.score = 0;
	}
    }
    
    private void drawDeleteBlockOutline(Batch batch)
    {
	if (deleteBlockX > -1 && deleteBlockY > -1)
	{

	    float x = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getX();
	    float y = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getY();
	    float sizeX = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getWidth();
	    float sizeY = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getHeight();

	    batch.draw(getSprite("delete"), x, y, sizeX, 1);
	    batch.draw(getSprite("delete"), x, y, 1, sizeY);
	    batch.draw(getSprite("delete"), x, y + sizeY, sizeX, 1);
	    batch.draw(getSprite("delete"), x + sizeX, y, 1, sizeY + 1);
	}
    }
    
    private void displayTopTenDialogue(Batch batch)
    {
	batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(),
		this.getHeight() + 40);
	batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);

	drawText(batch, "YOUR SCORE OF " + (int) (gameScene.gameUI.scoreManager.score) + "\nIS IN THE TOP TEN!",
		Color.BLACK, this.getX() + 10, this.getY() + 55, 0.7f);
    }
    
    private void displayPlayAnotherGameDialogue(Batch batch)
    {
	batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(),
		this.getHeight() + 40);
	batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
	batch.draw(getSprite("green"), this.getX() + 2.5f, this.getY() - 37.5f, this.getWidth() - 5,
		this.getHeight() + 35);

	drawText(batch, "PLAY ANOTHER GAME?\n\n        Y/N", Color.WHITE, this.getX() + 30, this.getY() + 50,
		0.7f);
    }
    
    private void drawText(Batch batch, String text, Color color, float x, float y, float scale)
    {
	WallPipe.font.getData().setScale(scale, scale);
	WallPipe.font.setColor(color);
	WallPipe.font.draw(batch, text, x, y);
	WallPipe.font.setColor(1, 1, 1, 1);
    }
}
