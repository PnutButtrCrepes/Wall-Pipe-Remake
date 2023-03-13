package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BuildingBlock extends Entity
{

    BuildingBlockType type;

    public boolean hasUpEntrance;
    public boolean hasDownEntrance;
    public boolean hasLeftEntrance;
    public boolean hasRightEntrance;

    public boolean hasUpExit;
    public boolean hasDownExit;
    public boolean hasLeftExit;
    public boolean hasRightExit;

    public boolean watered;
    public boolean beingReplaced;

    boolean visible;

    public BuildingBlock(BuildingBlockType type)
    {

	super(0f, 0f, 1f, 1f);

	watered = false;
	beingReplaced = false;

	this.type = type;

	if (type != null)
	    switch (this.type)
	    {

	    case CROSS:

		addSprite("Cross.png", "block");

		hasUpEntrance = true;
		hasDownEntrance = true;
		hasLeftEntrance = true;
		hasRightEntrance = true;

		hasUpExit = true;
		hasDownExit = true;
		hasLeftExit = true;
		hasRightExit = true;

		break;

	    case DLELBOW:

		addSprite("DLElbow.png", "block");

		hasUpEntrance = false;
		hasDownEntrance = true;
		hasLeftEntrance = true;
		hasRightEntrance = false;

		hasUpExit = false;
		hasDownExit = true;
		hasLeftExit = true;
		hasRightExit = false;

		break;

	    case DRELBOW:

		addSprite("DRElbow.png", "block");

		hasUpEntrance = false;
		hasDownEntrance = true;
		hasLeftEntrance = false;
		hasRightEntrance = true;

		hasUpExit = false;
		hasDownExit = true;
		hasLeftExit = false;
		hasRightExit = true;

		break;

	    case HSQUIGGLY:

		addSprite("HSquiggly.png", "block");

		hasUpEntrance = false;
		hasDownEntrance = false;
		hasLeftEntrance = true;
		hasRightEntrance = true;

		hasUpExit = false;
		hasDownExit = false;
		hasLeftExit = true;
		hasRightExit = true;

		break;

	    case HSTRAIGHT:

		addSprite("HStraight.png", "block");

		hasUpEntrance = false;
		hasDownEntrance = false;
		hasLeftEntrance = true;
		hasRightEntrance = true;

		hasUpExit = false;
		hasDownExit = false;
		hasLeftExit = true;
		hasRightExit = true;

		break;

	    case ULELBOW:

		addSprite("ULElbow.png", "block");

		hasUpEntrance = true;
		hasDownEntrance = false;
		hasLeftEntrance = true;
		hasRightEntrance = false;

		hasUpExit = true;
		hasDownExit = false;
		hasLeftExit = true;
		hasRightExit = false;

		break;

	    case URELBOW:

		addSprite("URElbow.png", "block");

		hasUpEntrance = true;
		hasDownEntrance = false;
		hasLeftEntrance = false;
		hasRightEntrance = true;

		hasUpExit = true;
		hasDownExit = false;
		hasLeftExit = false;
		hasRightExit = true;

		break;

	    case VSQUIGGLY:

		addSprite("VSquiggly.png", "block");

		hasUpEntrance = true;
		hasDownEntrance = true;
		hasLeftEntrance = false;
		hasRightEntrance = false;

		hasUpExit = true;
		hasDownExit = true;
		hasLeftExit = false;
		hasRightExit = false;

		break;

	    case VSTRAIGHT:

		addSprite("VStraight.png", "block");

		hasUpEntrance = true;
		hasDownEntrance = true;
		hasLeftEntrance = false;
		hasRightEntrance = false;

		hasUpExit = true;
		hasDownExit = true;
		hasLeftExit = false;
		hasRightExit = false;

		break;

	    default:
		break;
	    }

	visible = true;
    }

    @Override
    public void act(float delta)
    {

    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {

	this.toFront();

	batch.end();
	batch.begin();

	if (visible)
	    batch.draw(getSprite("block"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
}
