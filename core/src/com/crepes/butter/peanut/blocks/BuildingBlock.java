package com.crepes.butter.peanut.blocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.Water;
import com.crepes.butter.peanut.Water.WaterDirection;

public class BuildingBlock extends Entity
{

    public BuildingBlockType type;

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
    
    public ArrayList<TurningPoint> negativeToPositiveTurningPoints;
    public ArrayList<TurningPoint> reverseTurningPoints;

    public boolean visible;

    public BuildingBlock(BuildingBlockType type)
    {

	super(0f, 0f, 1f, 1f);

	negativeToPositiveTurningPoints = new ArrayList<BuildingBlock.TurningPoint>();
	reverseTurningPoints = new ArrayList<BuildingBlock.TurningPoint>();
	
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

		// TODO WORKING, EXTEND TO OTHER BLOCKS
		addSprite("HSquiggly.png", "block");

		hasUpEntrance = false;
		hasDownEntrance = false;
		hasLeftEntrance = true;
		hasRightEntrance = true;

		hasUpExit = false;
		hasDownExit = false;
		hasLeftExit = true;
		hasRightExit = true;
		
		negativeToPositiveTurningPoints.add(new TurningPoint(11f / 32f, 0, WaterDirection.UP));
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 27f / 32f, WaterDirection.RIGHT));
		negativeToPositiveTurningPoints.add(new TurningPoint(19f / 32f, 0, WaterDirection.DOWN));
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 5f / 32f, WaterDirection.RIGHT));
		negativeToPositiveTurningPoints.add(new TurningPoint(27f / 32f, 0, WaterDirection.UP));
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 19f / 32f, WaterDirection.RIGHT));
		
		reverseTurningPoints.add(new TurningPoint(21f / 32f, 0, WaterDirection.DOWN));
		reverseTurningPoints.add(new TurningPoint(0, 5f / 32f, WaterDirection.LEFT));
		reverseTurningPoints.add(new TurningPoint(13f / 32f, 0, WaterDirection.UP));
		reverseTurningPoints.add(new TurningPoint(0, 27f / 32f, WaterDirection.LEFT));
		reverseTurningPoints.add(new TurningPoint(5f / 32f, 0, WaterDirection.DOWN));
		reverseTurningPoints.add(new TurningPoint(0, 13f / 32f, WaterDirection.LEFT));

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
		
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 14f / 32f, WaterDirection.LEFT));
		negativeToPositiveTurningPoints.add(new TurningPoint(4f / 32f, 0, WaterDirection.UP));
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 24f / 32f, WaterDirection.RIGHT));
		negativeToPositiveTurningPoints.add(new TurningPoint(19f / 32f, 0, WaterDirection.UP));
		
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 18f / 32f, WaterDirection.LEFT));
		negativeToPositiveTurningPoints.add(new TurningPoint(4f / 32f, 0, WaterDirection.DOWN));
		negativeToPositiveTurningPoints.add(new TurningPoint(0, 8f / 32f, WaterDirection.RIGHT));
		negativeToPositiveTurningPoints.add(new TurningPoint(19f / 32f, 0, WaterDirection.DOWN));

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

    public enum BuildingBlockType
    {

	CROSS, ULELBOW, URELBOW, DLELBOW, DRELBOW, HSTRAIGHT, VSTRAIGHT, HSQUIGGLY, VSQUIGGLY,

	L_BATHTUB, R_BATHTUB,

	DARK_ULELBOW, DARK_URELBOW, DARK_DLELBOW, DARK_DRELBOW, U_VSTRAIGHT, D_VSTRAIGHT,

	TELEPORT_CROSS,

	DLELBOW_50, DRELBOW_50,

	SINK
    }
    
    public class TurningPoint
    {
	public TurningPoint(float x, float y, WaterDirection targetWaterDirection)
	{
	    this.x = x;
	    this.y = y;
	    this.targetWaterDirection = targetWaterDirection;
	}
	
	public float x;
	public float y;
	public WaterDirection targetWaterDirection;
    }
}
