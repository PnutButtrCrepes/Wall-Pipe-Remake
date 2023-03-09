package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class LoopsManager extends Entity
{

    GameScene gameScene;

    public int loops;
    public boolean bonusAdded;

    public LoopsManager(GameScene gameScene)
    {

	super(0.25f, 2f, 1.5f, 1f);

	this.gameScene = gameScene;

	loops = 0;
	bonusAdded = false;

	this.sprite = new Sprite();
	this.texture = new Texture("Black.png");
	sprite.setTexture(texture);
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

	float temp = getX();
	batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());

	FontHelper.font.getData().setScale(0.75f, 0.75f);
	FontHelper.font.draw(batch, "Loops", this.getX() + 4, this.getY() + 56);

	FontHelper.font.getData().setScale(1, 1);
	FontHelper.font.draw(batch, String.valueOf(loops), this.getX() + 18, this.getY() + 28);
    }
}
