package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.GameScene;

public class HotKeys extends Entity
{

    public float score;

    public GameScene gameScene;

    public HotKeys(GameScene gameScene)
    {

	super(2f, 0.25f, 14f, 0.5f);

	score = 0;

	addSprite("Background.png", "background");

	this.gameScene = gameScene;
    }

    @Override
    public void act(float delta)
    {

	if (gameScene.water.running)
	    score += delta * 8;
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	batch.end();
	batch.begin();

	batch.draw(getSprite("background"), this.getX(), this.getY(), this.getWidth(), this.getHeight());

	WallPipe.font.getData().setScale(0.6f, 0.6f);
	WallPipe.font.draw(batch, "<D>irections", this.getX() + 30, this.getY() + 14);
	WallPipe.font.draw(batch, "<P>ause", this.getX() + 150, this.getY() + 14);
	WallPipe.font.draw(batch, "<O>ptions", this.getX() + 255, this.getY() + 14);
	WallPipe.font.draw(batch, "<Q>uit", this.getX() + 360, this.getY() + 14);
    }
}
