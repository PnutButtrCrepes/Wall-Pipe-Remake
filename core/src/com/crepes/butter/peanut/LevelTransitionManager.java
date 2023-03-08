package com.crepes.butter.peanut;

import java.text.SimpleDateFormat;
import java.sql.*;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class LevelTransitionManager extends Entity {

	public GameScene gameScene;
	
	Sprite sprite2;
	Texture texture2;
	
	Sprite sprite3;
	Texture texture3;
	
	Sprite sprite4;
	Texture texture4;
	
	Sprite sprite5;
	Texture texture5;
	
	Sprite sprite6;
	Texture texture6;
	
	float timer;
	boolean timerRunning;
	
	int deleteBlockX;
	int deleteBlockY;
	
	boolean hasSelectedInitials;
	boolean scoreAdded;
	boolean hasViewedLeaderboard;
	
	Character[] initials;
	
	public LevelTransitionManager(GameScene gameScene) {
		
		this.gameScene = gameScene;
		
		this.x = 5.75f;
		this.y = 7.5f;
	
		this.sizeX = 6.25f;
		this.sizeY = 2f;
		
		this.sprite = new Sprite();
		this.texture = new Texture("Black.png");
		sprite.setTexture(texture);
		
		this.sprite2 = new Sprite();
		this.texture2 = new Texture("White.png");
		sprite2.setTexture(texture2);
		
		this.sprite3 = new Sprite();
		this.texture3 = new Texture("Yellow.png");
		sprite3.setTexture(texture3);
		
		this.sprite4 = new Sprite();
		this.texture4 = new Texture("Water.png");
		sprite4.setTexture(texture4);
		
		this.sprite5 = new Sprite();
		this.texture5 = new Texture("DeleteOutline.png");
		sprite5.setTexture(texture5);
		
		this.sprite6 = new Sprite();
		this.texture6 = new Texture("Green.png");
		sprite6.setTexture(texture6);
		
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
		
		if(timerRunning) {
			
			timer += delta;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.end();
		
		batch.enableBlending();
		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		batch.begin();
		
		if(!gameScene.isLevelStarted()) {
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			batch.draw(sprite2, this.getX(), this.getY(), this.getWidth(), this.getHeight());
			batch.draw(sprite3, this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5, this.getHeight() - 5);
			
			FontHelper.font.getData().setScale(0.7f, 0.7f);
			FontHelper.font.setColor(0f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "CLICK MOUSE TO BEGIN", this.getX() + 20, this.getY() + 55);
			
			FontHelper.font.getData().setScale(0.6f, 0.6f);
			FontHelper.font.setColor(0.5f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "LEVEL: " + gameScene.levelCount, this.getX() + 60, this.getY() + 25);
			
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isPaused()) {
		
		batch.draw(sprite, this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
		//batch.draw(sprite2, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		batch.draw(sprite3, this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		FontHelper.font.getData().setScale(0.9f, 0.9f);
		FontHelper.font.setColor(0.5f, 0f, 0f, 1);
		FontHelper.font.draw(batch, "GAME PAUSED...", this.getX() + 40, this.getY() + 55);
		
		FontHelper.font.getData().setScale(0.6f, 0.6f);
		FontHelper.font.setColor(0f, 0f, 0f, 1);
		FontHelper.font.draw(batch, "Click to Continue", this.getX() + 50, this.getY() + 20);
		
		FontHelper.font.setColor(1f, 1f, 1f, 1);
		
		} else if(gameScene.isLevelEnded() && timer < 3) {
			
			timerRunning = true;
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			batch.draw(sprite2, this.getX(), this.getY(), this.getWidth(), this.getHeight());
			batch.draw(sprite3, this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5, this.getHeight() - 5);
			
			FontHelper.font.getData().setScale(0.9f, 0.9f);
			FontHelper.font.setColor(0.5f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "LEVEL " + String.valueOf((int) gameScene.levelCount) + " OVER", this.getX() + 40, this.getY() + 45);
			
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isLevelEnded() && timer > 3 && timer < 6) {
			
			if(gameScene.loopsManager.loops > 3) {
				
				batch.draw(sprite, this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
				batch.draw(sprite2, this.getX(), this.getY(), this.getWidth(), this.getHeight());
				batch.draw(sprite4, this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5, this.getHeight() - 5);
			
				FontHelper.font.getData().setScale(0.7f, 0.7f);
				FontHelper.font.setColor(1f, 1f, 1f, 1);
				FontHelper.font.draw(batch, "BONUS: " + String.valueOf((int) gameScene.loopsManager.loops) + " LOOPS MADE\nADDING " +
						String.valueOf((int) gameScene.loopsManager.getBonus()) + " TO SCORE", this.getX() + 20, this.getY() + 55);
			
				FontHelper.font.setColor(1f, 1f, 1f, 1);
				
			} else {
				
				timer = 6;
			}
			
		} else if(gameScene.isLevelEnded() && timer > 6 && timer < 7) {
			
			if(timer > 6.25)
				gameScene.loopsManager.addBonus();
			
		} else if(gameScene.isLevelEnded() && timer > 7 && timer < 10) {
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 10, this.getWidth(), this.getHeight());
			batch.draw(sprite2, this.getX(), this.getY(), this.getWidth(), this.getHeight());
			batch.draw(sprite4, this.getX() + 2.5f, this.getY() + 2.5f, this.getWidth() - 5, this.getHeight() - 5);
		
			FontHelper.font.getData().setScale(0.7f, 0.7f);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			FontHelper.font.draw(batch, "REMOVING EMPTY PIPES", this.getX() + 18, this.getY() + 40);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isLevelEnded() && timer > 10 && timer < 11) {
			
			if(deleteBlockX > -1 && deleteBlockY > -1) {
				
				float x = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getX();
				float y = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getY();
				float sizeX = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getWidth();
				float sizeY = gameScene.bfManager.blockField[deleteBlockX][deleteBlockY].getHeight();
				
				batch.draw(sprite5, x, y, sizeX, 1);
				batch.draw(sprite5, x, y, 1, sizeY);
				batch.draw(sprite5, x, y + sizeY, sizeX, 1);
				batch.draw(sprite5, x + sizeX, y, 1, sizeY + 1);
			}
			
		} else if(gameScene.isLevelEnded() && timer > 11 && timer < 12) {
			
			if(deleteBlockX > -1 && deleteBlockY > -1) {
				
				gameScene.bfManager.blockField[deleteBlockX][deleteBlockY] = null;
				
				deleteBlockX = -1;
				deleteBlockY = -1;
				
				gameScene.scoreManager.score -= 20;
				
				if(gameScene.scoreManager.score < 0) {
					
					gameScene.scoreManager.score = 0;
				}
			}
			
			Delete:
				for(int i = 0; i < 15; i++) {
					for(int j = 0; j < 12; j++) {
					
						if(gameScene.bfManager.blockField[i][11 - j] != null) {
						
							if(!gameScene.bfManager.blockField[i][11 - j].watered) {
							
								deleteBlockX = i;
								deleteBlockY = 11 - j;
								
								timer = 10.6f;
								break Delete;
						}
					}
				}
			}
			
		} else if(gameScene.isLevelEnded() && timer > 12 &&
				gameScene.scoreManager.score >= gameScene.scoreNeededManager.scoreNeeded) {
			
			gameScene.levelInit();
			
		} else if(gameScene.isLevelEnded() && timer > 12 && timer < 15) {
			
			FontHelper.font.getData().setScale(1.1f, 1.1f);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			FontHelper.font.draw(batch, "GAME OVER", this.getX() + 50, this.getY() + 5);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isLevelEnded() && timer > 15 && timer < 18 &&
				(((int) (gameScene.scoreManager.score) < gameScene.leaderboardManager.leaderboardEntries[9].score)
						|| ((int) (gameScene.scoreManager.score) == gameScene.leaderboardManager.leaderboardEntries[9].score &&
						gameScene.levelCount <= gameScene.leaderboardManager.leaderboardEntries[9].level))) {
			
			timer = 19;
			hasSelectedInitials = true;
			hasViewedLeaderboard = false;
			scoreAdded = true;
			
			gameScene.leaderboardManager.sortScores();
			gameScene.leaderboardManager.compileScores();
			
		} else if(gameScene.isLevelEnded() && timer > 15 && timer < 18) {
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 50, this.getWidth(), this.getHeight() + 40);
			batch.draw(sprite2, this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
			
			FontHelper.font.getData().setScale(0.7f, 0.7f);
			FontHelper.font.setColor(0f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "YOUR SCORE OF " + (int) (gameScene.scoreManager.score) + "\nIS IN THE TOP TEN!", this.getX() + 10, this.getY() + 55);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isLevelEnded() && timer > 18 && !hasSelectedInitials) {
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 50, this.getWidth(), this.getHeight() + 40);
			batch.draw(sprite2, this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
			
			FontHelper.font.getData().setScale(0.7f, 0.7f);
			FontHelper.font.setColor(0f, 0f, 0f, 1);
			FontHelper.font.draw(batch, "YOUR SCORE OF " + (int) (gameScene.scoreManager.score) + "\nIS IN THE TOP TEN!", this.getX() + 10, this.getY() + 55);
			
			if(timer > 18.5) {
				
				if(initials[0] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: ", this.getX() + 10, this.getY() - 15);
					
				} else if(initials[1] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + "", this.getX() + 10, this.getY() - 15);
					
				} else if(initials[2] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "", this.getX() + 10, this.getY() - 15);
					
				} else {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2], this.getX() + 10, this.getY() - 15);
				}
				
			} else {
				
				if(initials[0] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: _", this.getX() + 10, this.getY() - 15);
					
				} else if(initials[1] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + "_", this.getX() + 10, this.getY() - 15);
					
				} else if(initials[2] == null) {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + "_", this.getX() + 10, this.getY() - 15);
					
				} else {
					
					FontHelper.font.getData().setScale(0.7f, 0.7f);
					FontHelper.font.setColor(0f, 0f, 0f, 1);
					FontHelper.font.draw(batch, "ENTER INITIALS: " + initials[0] + initials[1] + initials[2], this.getX() + 10, this.getY() - 15);
				}
			}
			
			if(timer > 19)
				timer = 18;
			
			FontHelper.font.setColor(1f, 1f, 1f, 1);
			
		} else if(gameScene.isLevelEnded() && timer > 19 && hasSelectedInitials && !hasViewedLeaderboard) {
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			
			long millis = System.currentTimeMillis();  
			Date date = new Date(millis);  
			
			if(!scoreAdded) {
			
				for(int i = 0; i < 3; i++) {
					
					if(initials[i] == null)
						initials[i] = ' ';
				}
				
				gameScene.leaderboardManager.addScore(new LeaderboardEntry(Character.toString(initials[0])
						+ Character.toString(initials[1])
						+ Character.toString(initials[2]), (int) gameScene.scoreManager.score, gameScene.levelCount,
						formatter.format(date)));
				scoreAdded = true;
			}
			
			gameScene.leaderboardManager.setVisible(true);
			
			if(timer > 20)
				timer = 19;
			
		} else if(gameScene.isLevelEnded() && timer > 19 && timer < 20 && hasSelectedInitials && hasViewedLeaderboard) {
			
			gameScene.leaderboardManager.setVisible(false);
			
		} else if(gameScene.isLevelEnded() && timer > 20 && hasSelectedInitials && hasViewedLeaderboard) {
			
			batch.draw(sprite, this.getX() - 10, this.getY() - 50, this.getWidth(), this.getHeight() + 40);
			batch.draw(sprite2, this.getX(), this.getY() - 40, this.getWidth(), this.getHeight() + 40);
			batch.draw(sprite6, this.getX() + 2.5f, this.getY() - 37.5f, this.getWidth() - 5, this.getHeight() + 35);
			
			FontHelper.font.getData().setScale(0.7f, 0.7f);
			FontHelper.font.draw(batch, "PLAY ANOTHER GAME?\n\n        Y/N", this.getX() + 30, this.getY() + 50);
			FontHelper.font.setColor(1f, 1f, 1f, 1);
		}
	}
}
