package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.GameScene;

public class Loops extends Entity
{

    GameScene gameScene;

    public int loops;
    public boolean bonusAdded;

    public Loops(GameScene gameScene)
    {

	super(0.25f, 2f, 1.5f, 1f);

	this.gameScene = gameScene;

	loops = 0;
	bonusAdded = false;

	addSprite("Black.png", "black");
    }

    public void reset()
    {

	loops = 0;
	bonusAdded = false;
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

	    gameScene.scoreManager.score += getBonus();
	    bonusAdded = true;
	}
    }

    @Override
    public void act(float delta)
    {

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	batch.draw(getSprite("black"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

	WallPipe.font.getData().setScale(0.75f, 0.75f);
	WallPipe.font.draw(batch, "Loops", this.getX() + 4, this.getY() + 56);

	WallPipe.font.getData().setScale(1, 1);
	WallPipe.font.draw(batch, String.valueOf(loops), this.getX() + 18, this.getY() + 28);
    }
}
