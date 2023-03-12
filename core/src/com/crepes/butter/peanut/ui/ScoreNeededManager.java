package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class ScoreNeededManager extends Entity
{

    public float scoreNeeded;

    public GameScene gameScene;

    public ScoreNeededManager(GameScene gameScene)
    {

	super(2f, 1.25f, 4.5f, 0.5f);

	scoreNeeded = 0;

	this.sprite = new Sprite();
	this.texture = new Texture("Black.png");
	sprite.setTexture(texture);

	this.gameScene = gameScene;
    }

    public void reset()
    {

	scoreNeeded = (int) (gameScene.scoreManager.score) + 200 + (gameScene.levelCount * 10);
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

	batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());

	FontHelper.font.getData().setScale(0.6f, 0.6f);
	FontHelper.font.draw(batch, "SCORE NEEDED:", this.getX() + 4, this.getY() + 14);

	FontHelper.font.getData().setScale(0.6f, 0.6f);
	FontHelper.font.draw(batch, String.valueOf((int) scoreNeeded), this.getX() + 95, this.getY() + 14);
    }
}
