package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.scenes.GameScene;

public class ScoreNeeded extends Entity
{

    public float scoreNeeded;

    public GameScene gameScene;

    public ScoreNeeded(GameScene gameScene)
    {

	super(2f, 1.25f, 4.5f, 0.5f);

	scoreNeeded = 0;

	addSprite("Black.png", "black");

	this.gameScene = gameScene;
    }

    public void reset()
    {

	scoreNeeded = (int) (gameScene.gameUI.scoreManager.score) + 200 + (gameScene.levelCount * 10);
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

	WallPipe.font.getData().setScale(0.6f, 0.6f);
	WallPipe.font.draw(batch, "SCORE NEEDED:", this.getX() + 4, this.getY() + 14);

	WallPipe.font.getData().setScale(0.6f, 0.6f);
	WallPipe.font.draw(batch, String.valueOf((int) scoreNeeded), this.getX() + 95, this.getY() + 14);
    }
}
