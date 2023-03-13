package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.FontHelper;
import com.crepes.butter.peanut.GameScene;

public class TitleInformationManager extends Entity
{

    public float score;

    public GameScene gameScene;

    public TitleInformationManager(GameScene gameScene)
    {

	super(2f, 13.25f, 14f, 0.5f);

	score = 0;

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

	FontHelper.font.getData().setScale(0.7f, 0.7f);
	FontHelper.font.draw(batch, "W A L L  P I P E  G A M E               by Nathan Keenan", this.getX() + 30,
		this.getY() + 16);
    }
}