package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

import com.crepes.butter.peanut.*;

public class ClockManager extends Entity
{

    public GameScene gameScene;

    public boolean running;
    public float time;

    public ClockManager(GameScene gameScene)
    {

	super(0.25f, 4f, 1.5f, 1f);

	this.gameScene = gameScene;

	time = 40;

	addSprite("Black.png", "black");
    }

    public void reset()
    {

	time = 40;
	running = true;
    }

    @Override
    public void act(float delta)
    {

	if (!gameScene.isPaused() && gameScene.isLevelStarted())
	{

	    if (running)
	    {

		time -= delta;

		if (time <= 0)
		{

		    time = 0;

		    if (!gameScene.water.running)
			gameScene.water.running = true;

		    running = false;
		}
	    }
	}
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

	FontHelper.font.getData().setScale(0.75f, 0.75f);
	FontHelper.font.draw(batch, "Clock", this.getX() + 4, this.getY() + 56);

	FontHelper.font.getData().setScale(1, 1);
	FontHelper.font.draw(batch, String.valueOf((int) time), this.getX() + 14, this.getY() + 28);
    }
}
