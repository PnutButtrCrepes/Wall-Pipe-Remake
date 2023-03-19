package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.WallPipe;
import com.crepes.butter.peanut.scenes.GameScene;

public class TitleInformation extends Entity
{
    public TitleInformation()
    {

	super(2f, 13.25f, 14f, 0.5f);
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

	WallPipe.font.getData().setScale(0.7f, 0.7f);
	WallPipe.font.draw(batch, "W A L L  P I P E  G A M E               by Nathan Keenan", this.getX() + 30,
		this.getY() + 16);
    }
}