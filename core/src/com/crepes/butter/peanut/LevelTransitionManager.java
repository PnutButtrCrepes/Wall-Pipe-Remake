package com.crepes.butter.peanut;

import java.text.SimpleDateFormat;
import java.sql.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.HazardManager.HazardType;
import com.crepes.butter.peanut.scenes.GameScene;

public class LevelTransitionManager extends Entity {
	private static final Color DARK_RED = new Color(0.5f, 0, 0, 1);

	public GameScene gameScene;

	private float timer;
	private boolean timerRunning;

	private int deleteBlockX;
	private int deleteBlockY;

	public boolean hasSelectedInitials;
	boolean scoreAdded;
	public boolean hasViewedLeaderboard;

	public Character[] initials;

	public LevelTransitionManager(GameScene gameScene) {
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

	public void reset() {

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
	public void act(float delta) {

		if (timerRunning)
			timer += delta;
	}

	private void drawText(Batch batch, String text, Color color, float x, float y, float scale) {
		WallPipe.font.getData().setScale(scale, scale);
		WallPipe.font.setColor(color);
		WallPipe.font.draw(batch, text, x, y);
		WallPipe.font.setColor(1, 1, 1, 1);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.end();

		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		batch.begin();

		switch (gameScene.gameState) {
		case NOT_STARTED:
			batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
			batch.draw(getSprite("yellow"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
					this.getHeight() - 5);

			drawText(batch, "CLICK MOUSE TO BEGIN", Color.BLACK, this.getX() + 20, this.getY() + 55, 0.7f);
			drawText(batch, "LEVEL: " + gameScene.gameUI.levelCount.levelCount, DARK_RED, this.getX() + 60,
					this.getY() + 25, 0.6f);
			break;

		case RUNNING:
			break;

		case PAUSED:
			batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			// batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(),
			// this.getHeight());
			batch.draw(getSprite("yellow"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

			drawText(batch, "GAME PAUSED...", DARK_RED, this.getX() + 40, this.getY() + 55, 0.9f);
			drawText(batch, "Click to Continue", Color.BLACK, this.getX() + 50, this.getY() + 20, 0.6f);
			break;

		case OPTIONS_DIALOG:
			break;

		case QUIT_DIALOG:
			batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			// batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(),
			// this.getHeight());
			batch.draw(getSprite("green"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

			drawText(batch, "QUIT WALL PIPE GAME ?", Color.WHITE, this.getX() + 30, this.getY() + 55, 0.6f);
			drawText(batch, "(Y/N)", new Color(0.8f, 0.8f, 0f, 1), this.getX() + 80, this.getY() + 30, 0.6f);
			break;
//	TODO
		case LEVEL_ENDED:
			if (timer < 3) {

				displayLevelOverDialogue(batch);

			} else if (timer > 3 && timer < 6) {

				if (gameScene.gameUI.loopsManager.loops > 3) {

					displayLoopBonusDialogue(batch);

				} else {

					timer = 6;
				}

			} else if (timer > 6 && timer < 7) {

				if (timer > 6.25)
					gameScene.gameUI.loopsManager.addBonus();

			} else if (timer > 7 && timer < 10) {

				displayRemovingPipesDialogue(batch);

			} else if (timer > 10 && timer < 11) {

				drawDeleteBlockOutline(batch);

			} else if (timer > 11 && timer < 12) {

				deleteSelectedBlock(batch);

				selectBlockToBeDeleted(batch);

			} else if (timer > 12
					&& gameScene.gameUI.scoreManager.score >= gameScene.gameUI.scoreNeededManager.scoreNeeded
					&& (!gameScene.hazardManager.hasHazardOfType(HazardType.SINK) ||
					(gameScene.hazardManager.hasHazardOfType(HazardType.SINK) &&
					gameScene.hazardManager.getHazardOfType(HazardType.SINK).componentBlocks.get(0).watered == true))) {

				gameScene.levelInit();

			} else if (timer > 12 && timer < 15) {

				drawText(batch, "GAME OVER", Color.WHITE, this.getX() + 50, this.getY() + 5, 1.1f);

			} else if (timer > 15 && timer < 18
					&& (((int) (gameScene.gameUI.scoreManager.score) < gameScene.leaderboardManager.leaderboardEntries[9].score)
							|| ((int) (gameScene.gameUI.scoreManager.score) == gameScene.leaderboardManager.leaderboardEntries[9].score
									&& gameScene.gameUI.levelCount.levelCount <= gameScene.leaderboardManager.leaderboardEntries[9].level))) {

				timer = 19;
				hasSelectedInitials = true;
				hasViewedLeaderboard = false;
				scoreAdded = true;

				gameScene.leaderboardManager.sortScores();
				gameScene.leaderboardManager.compileScores();

			} else if (timer > 15 && timer < 18) {

				displayTopTenDialogue(batch);

			} else if (timer > 18 && !hasSelectedInitials) {

				displayTopTenDialogue(batch);

				if (timer > 18.5) {

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

				} else {
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

				if (timer > 19)
					timer = 18;

			} else if (timer > 19 && hasSelectedInitials && !hasViewedLeaderboard) {

				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

				long millis = System.currentTimeMillis();
				Date date = new Date(millis);

				if (!scoreAdded) {

					for (int i = 0; i < 3; i++) {

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

				if (timer > 20)
					timer = 19;

			} else if (timer > 19 && timer < 20 && hasSelectedInitials && hasViewedLeaderboard) {

				gameScene.leaderboardManager.setVisible(false);

			} else if (timer > 20 && hasSelectedInitials && hasViewedLeaderboard) {

				displayPlayAnotherGameDialogue(batch);
			}
			break;

		default:
			break;
		}

	}

	private void displayLevelOverDialogue(Batch batch) {
		timerRunning = true;

		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(getSprite("yellow"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
				this.getHeight() - 5);

		drawText(batch, "LEVEL " + String.valueOf((int) gameScene.gameUI.levelCount.levelCount) + " OVER",
				new Color(0.5f, 0f, 0f, 1), this.getX() + 40, this.getY() + 45, 0.9f);
	}

	private void displayLoopBonusDialogue(Batch batch) {
		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
				this.getHeight() - 5);

		drawText(batch,
				"BONUS: " + String.valueOf((int) gameScene.gameUI.loopsManager.loops) + " LOOPS MADE\nADDING "
						+ String.valueOf((int) gameScene.gameUI.loopsManager.getBonus()) + " TO SCORE",
				Color.WHITE, this.getX() + 20, this.getY() + 55, 0.7f);
	}

	private void displayRemovingPipesDialogue(Batch batch) {
		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		batch.draw(getSprite("white"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(getSprite("water"), this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5,
				this.getHeight() - 5);

		drawText(batch, "REMOVING EMPTY PIPES", Color.WHITE, this.getX() + 18, this.getY() + 40, 0.7f);
	}

	private void selectBlockToBeDeleted(Batch batch) {
		Delete: for (int i = 0; i < 15; i++)
			for (int j = 0; j < 12; j++)
				if (gameScene.bfManager.blockField[i][11 - j] != null
						&& !gameScene.bfManager.blockField[i][11 - j].watered
						&& gameScene.bfManager.blockField[i][11 - j].replaceable) {

					deleteBlockX = i;
					deleteBlockY = 11 - j;

					timer = 10.6f;
					break Delete;
				}
	}

	private void deleteSelectedBlock(Batch batch) {
		if (deleteBlockX > -1 && deleteBlockY > -1) {

			gameScene.bfManager.blockField[deleteBlockX][deleteBlockY] = null;

			deleteBlockX = -1;
			deleteBlockY = -1;

			gameScene.gameUI.scoreManager.score -= 20;

			if (gameScene.gameUI.scoreManager.score < 0)
				gameScene.gameUI.scoreManager.score = 0;
		}
	}

	private void drawDeleteBlockOutline(Batch batch) {
		if (deleteBlockX > -1 && deleteBlockY > -1) {

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

	private void displayTopTenDialogue(Batch batch) {
		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(), this.getHeight() + 40);
		batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);

		drawText(batch, "YOUR SCORE OF " + (int) (gameScene.gameUI.scoreManager.score) + "\nIS IN THE TOP TEN!",
				Color.BLACK, this.getX() + 10, this.getY() + 55, 0.7f);
	}

	private void displayPlayAnotherGameDialogue(Batch batch) {
		batch.draw(getSprite("black"), this.getX() - 10, this.getY() - 50, this.getWidth(), this.getHeight() + 40);
		batch.draw(getSprite("white"), this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
		batch.draw(getSprite("green"), this.getX() + 2.5f, this.getY() - 37.5f, this.getWidth() - 5,
				this.getHeight() + 35);

		drawText(batch, "PLAY ANOTHER GAME?\n\n        Y/N", Color.WHITE, this.getX() + 30, this.getY() + 50, 0.7f);
	}
}
