package com.crepes.butter.peanut;

import java.text.SimpleDateFormat;
import java.sql.*;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.scenes.GameScene;

public class LevelTransitionManager extends Entity
{

    public GameScene gameScene;

    float timer;
    boolean timerRunning;

    int deleteBlockX;
    int deleteBlockY;

    public boolean hasSelectedInitials;
    boolean scoreAdded;
    public boolean hasViewedLeaderboard;

    public Character[] initials;

    public LevelTransitionManager(GameScene gameScene)
    {

	super(5.75f, 7.5f, 6.25f, 2f);

	this.gameScene = gameScene;

	addSprite("Black.png", "black");
	addSprite("White.png", "white");
	addSprite("Yellow.png", "yellow");
	addSprite("Water.png", "water");
	addSprite("DeleteOutline.png", "delete");
	addSprite("Green.png", "green");

	timer = 0;
	timerRunning = false;

	deleteBlockX = -1;
	deleteBlockY = -1;

	hasSelectedInitials = false;
	scoreAdded = false;
	hasViewedLeaderboard = false;

	initials = new Character[3];
    }

    public void reset()
    {

	timer = 0;
	timerRunning = false;

	deleteBlockX = -1;
	deleteBlockY = -1;

	hasSelectedInitials = false;
	scoreAdded = false;
	hasViewedLeaderboard = false;

	initials = new Character[3];
    }

    @Override
    public void act(float delta)
    {

	if (timerRunning)
	{

	    timer += delta;
	}
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();

	batch.enableBlending();
	batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

	batch.begin();

	switch (gameScene.gameState)
	{
	case NOT_STARTED:
	    batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	    batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	    batch.draw(getSprite("yellow"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
		    this.getHeight() - 5);

	    WallPipe.font.getData().setScale(0.7f, 0.7f);
	    WallPipe.font.setColor(0f, 0f, 0f, 1);
	    WallPipe.font.draw(batch, "CLICK MOUSE TO BEGIN", this.getX() + 20, this.getY() + 55);

	    WallPipe.font.getData().setScale(0.6f, 0.6f);
	    WallPipe.font.setColor(0.5f, 0f, 0f, 1);
	    WallPipe.font.draw(batch, "LEVEL: " + gameScene.levelCount, this.getX() + 60, this.getY() + 25);

	    WallPipe.font.setColor(1f, 1f, 1f, 1);
	    break;

	case RUNNING:
	    break;

	case PAUSED:
	    batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	    // batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(),
	    // this.getHeight());
	    batch.draw(getSprite("yellow"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

	    WallPipe.font.getData().setScale(0.9f, 0.9f);
	    WallPipe.font.setColor(0.5f, 0f, 0f, 1);
	    WallPipe.font.draw(batch, "GAME PAUSED...", this.getX() + 40, this.getY() + 55);

	    WallPipe.font.getData().setScale(0.6f, 0.6f);
	    WallPipe.font.setColor(0f, 0f, 0f, 1);
	    WallPipe.font.draw(batch, "Click to Continue", this.getX() + 50, this.getY() + 20);

	    WallPipe.font.setColor(1f, 1f, 1f, 1);
	    break;

	case OPTIONS_DIALOG:
	    break;

	case QUIT_DIALOG:
	    batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
	    // batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(),
	    // this.getHeight());
	    batch.draw(getSprite("green"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

	    WallPipe.font.getData().setScale(0.6f, 0.6f);
	    WallPipe.font.setColor(1f, 1f, 1f, 1);
	    WallPipe.font.draw(batch, "QUIT WALL PIPE GAME ?", this.getX() + 30, this.getY() + 55);

	    WallPipe.font.getData().setScale(0.6f, 0.6f);
	    WallPipe.font.setColor(0.8f, 0.8f, 0f, 1);
	    WallPipe.font.draw(batch, "(Y/N)", this.getX() + 80, this.getY() + 30);

	    WallPipe.font.setColor(1f, 1f, 1f, 1);
	    break;

	case LEVEL_ENDED:
	    if (timer < 3)
	    {

		timerRunning = true;

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(getSprite("yellow"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
			this.getHeight() - 5);

		WallPipe.font.getData().setScale(0.9f, 0.9f);
		WallPipe.font.setColor(0.5f, 0f, 0f, 1);
		WallPipe.font.draw(batch, "LEVEL " + String.valueOf((int) gameScene.levelCount) + " OVER",
			this.getX() + 40, this.getY() + 45);

		WallPipe.font.setColor(1f, 1f, 1f, 1);

	    } else if (timer > 3 && timer < 6)
	    {

		if (gameScene.gameUI.loopsManager.loops > 3)
		{

		    batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(),
			    this.getHeight());
		    batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		    batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
			    this.getHeight() - 5);

		    WallPipe.font.getData().setScale(0.7f, 0.7f);
		    WallPipe.font.setColor(1f, 1f, 1f, 1);
		    WallPipe.font.draw(batch,
			    "BONUS: " + String.valueOf((int) gameScene.gameUI.loopsManager.loops)
				    + " LOOPS MADE\nADDING "
				    + String.valueOf((int) gameScene.gameUI.loopsManager.getBonus()) + " TO SCORE",
			    this.getX() + 20, this.getY() + 55);

		    WallPipe.font.setColor(1f, 1f, 1f, 1);

		} else
		{

		    timer = 6;
		}

	    } else if (timer > 6 && timer < 7)
	    {

		if (timer > 6.25)
		    gameScene.gameUI.loopsManager.addBonus();

	    } else if (timer > 7 && timer < 10)
	    {

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
			this.getHeight() - 5);

		WallPipe.font.getData().setScale(0.7f, 0.7f);
		WallPipe.font.setColor(1f, 1f, 1f, 1);
		WallPipe.font.draw(batch, "REMOVING EMPTY PIPES", this.getX() + 18, this.getY() + 40);
		WallPipe.font.setColor(1f, 1f, 1f, 1);

	    } else if (timer > 10 && timer < 11)
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

	    } else if (timer > 11 && timer < 12)
	    {

		if (deleteBlockX > -1 && deleteBlockY > -1)
		{

		    gameScene.bfManager.blockField[deleteBlockX][deleteBlockY] = null;

		    deleteBlockX = -1;
		    deleteBlockY = -1;

		    gameScene.gameUI.scoreManager.score -= 20;

		    if (gameScene.gameUI.scoreManager.score < 0)
		    {

			gameScene.gameUI.scoreManager.score = 0;
		    }
		}

		Delete: for (int i = 0; i < 15; i++)
		{
		    for (int j = 0; j < 12; j++)
		    {

			if (gameScene.bfManager.blockField[i][11 - j] != null)
			{

			    if (!gameScene.bfManager.blockField[i][11 - j].watered)
			    {

				deleteBlockX = i;
				deleteBlockY = 11 - j;

				timer = 10.6f;
				break Delete;
			    }
			}
		    }
		}

	    } else if (timer > 12
		    && gameScene.gameUI.scoreManager.score >= gameScene.gameUI.scoreNeededManager.scoreNeeded)
	    {

		gameScene.levelInit();

	    } else if (timer > 12 && timer < 15)
	    {

		WallPipe.font.getData().setScale(1.1f, 1.1f);
		WallPipe.font.setColor(1f, 1f, 1f, 1);
		WallPipe.font.draw(batch, "GAME OVER", this.getX() + 50, this.getY() + 5);
		WallPipe.font.setColor(1f, 1f, 1f, 1);

	    } else if (timer > 15 && timer < 18
		    && (((int) (gameScene.gameUI.scoreManager.score) < gameScene.leaderboardManager.leaderboardEntries[9].score)
			    || ((int) (gameScene.gameUI.scoreManager.score) == gameScene.leaderboardManager.leaderboardEntries[9].score
				    && gameScene.levelCount <= gameScene.leaderboardManager.leaderboardEntries[9].level)))
	    {

		timer = 19;
		hasSelectedInitials = true;
		hasViewedLeaderboard = false;
		scoreAdded = true;

		gameScene.leaderboardManager.sortScores();
		gameScene.leaderboardManager.compileScores();

	    } else if (timer > 15 && timer < 18)
	    {

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(),
			this.getHeight() + 40);
		batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);

		WallPipe.font.getData().setScale(0.7f, 0.7f);
		WallPipe.font.setColor(0f, 0f, 0f, 1);
		WallPipe.font.draw(batch,
			"YOUR SCORE OF " + (int) (gameScene.gameUI.scoreManager.score) + "\nIS IN THE TOP TEN!",
			this.getX() + 10, this.getY() + 55);
		WallPipe.font.setColor(1f, 1f, 1f, 1);

	    } else if (timer > 18 && !hasSelectedInitials)
	    {

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(),
			this.getHeight() + 40);
		batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);

		WallPipe.font.getData().setScale(0.7f, 0.7f);
		WallPipe.font.setColor(0f, 0f, 0f, 1);
		WallPipe.font.draw(batch,
			"YOUR SCORE OF " + (int) (gameScene.gameUI.scoreManager.score) + "\nIS IN THE TOP TEN!",
			this.getX() + 10, this.getY() + 55);

		if (timer > 18.5)
		{

		    if (initials[0] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: ", this.getX() + 10, this.getY() - 15);

		    } else if (initials[1] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + "", this.getX() + 10,
				this.getY() - 15);

		    } else if (initials[2] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "", this.getX() + 10,
				this.getY() - 15);

		    } else
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2],
				this.getX() + 10, this.getY() - 15);
		    }

		} else
		{

		    if (initials[0] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: _", this.getX() + 10, this.getY() - 15);

		    } else if (initials[1] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + "_", this.getX() + 10,
				this.getY() - 15);

		    } else if (initials[2] == null)
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "_",
				this.getX() + 10, this.getY() - 15);

		    } else
		    {

			WallPipe.font.getData().setScale(0.7f, 0.7f);
			WallPipe.font.setColor(0f, 0f, 0f, 1);
			WallPipe.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2],
				this.getX() + 10, this.getY() - 15);
		    }
		}

		if (timer > 19)
		    timer = 18;

		WallPipe.font.setColor(1f, 1f, 1f, 1);

	    } else if (timer > 19 && hasSelectedInitials && !hasViewedLeaderboard)
	    {

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
			    (int) gameScene.gameUI.scoreManager.score, gameScene.levelCount, formatter.format(date)));
		    scoreAdded = true;
		}

		gameScene.leaderboardManager.setVisible(true);

		if (timer > 20)
		    timer = 19;

	    } else if (timer > 19 && timer < 20 && hasSelectedInitials && hasViewedLeaderboard)
	    {

		gameScene.leaderboardManager.setVisible(false);

	    } else if (timer > 20 && hasSelectedInitials && hasViewedLeaderboard)
	    {

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(),
			this.getHeight() + 40);
		batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
		batch.draw(getSprite("green"), this.getX() + 2.5f, this.getY() - 37.5f, this.getWidth() - 5,
			this.getHeight() + 35);

		WallPipe.font.getData().setScale(0.7f, 0.7f);
		WallPipe.font.draw(batch, "PLAY ANOTHER GAME?\n\n        Y/N", this.getX() + 30, this.getY() + 50);
		WallPipe.font.setColor(1f, 1f, 1f, 1);
	    }
	    break;

	default:
	    break;
	}

    }
}
