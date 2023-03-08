package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class GameUI extends Entity
{
    public GameScene gameScene;
    
    public int loops;
    public boolean bonusAdded;
    
    public float score;
    
    Sprite sprite2;
    Texture texture2;
    
    float hkx;
    float hky;
    
    float hksizeX;
    float hksizeY;
    
    float loopsx;
    float loopsy;
    
    float loopssizeX;
    float loopssizeY;
    
    float scorex;
    float scorey;
    
    float scoresizeX;
    float scoresizeY;
    
    public GameUI(GameScene gameScene)
    {
	this.gameScene = gameScene;
	
	// hk
	hkx = 2f;
	hky = 0.25f;

	hksizeX = 14f;
	hksizeY = 0.5f;
	
	// loops
	loopsx = 0.25f;
	loopsy = 2f;

	loopssizeX = 1.5f;
	loopssizeY = 1f;
	
	// score
	scorex = 11f;
	scorey = 1.25f;

	scoresizeX = 5f;
	scoresizeY = 0.5f;
	
	this.sprite = new Sprite();
	this.texture = new Texture("Background.png");
	sprite.setTexture(texture);
	
	sprite2 = new Sprite();
	texture2 = new Texture("Black.png");
	sprite2.setTexture(texture2);
    }
    
    public void reset()
    {

	loops = 0;
	bonusAdded = false;
	
	score = (int) score;
    }
    
    public float getBonus()
    {

	if (loops <= 3)
	    return 0;
	else if (loops > 3 && loops <= 7)
	    return 400;
	else if (loops > 7 && loops <= 11)
	    return 800;
	else
	    return 1500;
    }

    public void addBonus()
    {

	if (!bonusAdded)
	{

	    score += getBonus();
	    bonusAdded = true;
	}
    }
    
    @Override
    public void act(float delta)
    {
	if (!gameScene.isPaused() && gameScene.isLevelStarted())
	{
	    if (gameScene.water.running)
		score += delta * 8;
	}
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
	// hotkeys
	batch.end();
	batch.begin();

	batch.draw(sprite, hkx, hky, hksizeX, hksizeY);

	FontHelper.font.getData().setScale(0.6f, 0.6f);
	FontHelper.font.draw(batch, "<D>irections", hkx + 30, hky + 14);
	FontHelper.font.draw(batch, "<P>ause", hkx + 150, hky + 14);
	FontHelper.font.draw(batch, "<O>ptions", hkx + 255, hky + 14);
	FontHelper.font.draw(batch, "<Q>uit", hkx + 360, hky + 14);
	
	// loops
	batch.end();
	batch.begin();

	batch.draw(sprite2, loopsx, loopsy, loopssizeX, loopssizeY);

	FontHelper.font.getData().setScale(0.75f, 0.75f);
	FontHelper.font.draw(batch, "Loops", loopsx + 4, loopsy + 56);

	FontHelper.font.getData().setScale(1, 1);
	FontHelper.font.draw(batch, String.valueOf(loops), loopsx + 18, loopsy + 28);
	
	// score
	batch.end();
	batch.begin();

	batch.draw(sprite2, scorex, scorey, scoresizeX, scoresizeY);

	FontHelper.font.getData().setScale(0.6f, 0.6f);
	FontHelper.font.draw(batch, "SCORE:", scorex + 4, scorey + 14);

	if ((int) (score) % 2 == 0)
	{

	    FontHelper.font.getData().setScale(0.6f, 0.6f);
	    FontHelper.font.draw(batch, String.valueOf((int) score), scorex + 48, scorey + 14);

	} else
	{

	    FontHelper.font.getData().setScale(0.6f, 0.6f);
	    FontHelper.font.draw(batch, String.valueOf((int) (score - 1)), scorex + 48, scorey + 14);
	}
    }
}
