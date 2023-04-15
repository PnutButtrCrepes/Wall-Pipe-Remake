package com.crepes.butter.peanut.blocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.Water.WaterDirection;

public class BuildingBlock extends Entity
{

    public BuildingBlockType type;

    public boolean oneWay;

    public boolean watered;
    public boolean looped;
    
    public boolean beingReplaced;
    
    public ArrayList<TurningPoint> turningPoints;

    public boolean visible;

    public BuildingBlock(BuildingBlockType type)
    {

	super(0f, 0f, 1f, 1f);

	turningPoints = new ArrayList<BuildingBlock.TurningPoint>();
	
	watered = false;
	looped = false;
	
	beingReplaced = false;

	this.type = type;
	this.oneWay = false;

	if (type != null)
	    switch (this.type)
	    {

	    case CROSS:

		addSprite("Cross.png", "block");

		break;

	    case DLELBOW:

		addSprite("DLElbow.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.RIGHT, WaterDirection.DOWN));

		break;

	    case DRELBOW:

		addSprite("DRElbow.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.LEFT, WaterDirection.DOWN));

		break;

	    case HSQUIGGLY:

		// TODO WORKING, EXTEND TO OTHER BLOCKS
		addSprite("HSquiggly.png", "block");
		
		turningPoints.add(new TurningPoint(8f / 32f, 16f / 32f, WaterDirection.RIGHT, WaterDirection.UP));
		turningPoints.add(new TurningPoint(8f / 32f, 24f / 32f, WaterDirection.UP, WaterDirection.RIGHT));
		turningPoints.add(new TurningPoint(16f / 32f, 24f / 32f, WaterDirection.RIGHT, WaterDirection.DOWN));
		turningPoints.add(new TurningPoint(16f / 32f, 8f / 32f, WaterDirection.DOWN, WaterDirection.RIGHT));
		turningPoints.add(new TurningPoint(24f / 32f, 8f / 32f, WaterDirection.RIGHT, WaterDirection.UP));
		turningPoints.add(new TurningPoint(24f / 32f, 16f / 32f, WaterDirection.UP, WaterDirection.RIGHT));

		break;

	    case HSTRAIGHT:

		addSprite("HStraight.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.RIGHT, WaterDirection.RIGHT));

		break;

	    case ULELBOW:

		addSprite("ULElbow.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.RIGHT, WaterDirection.UP));

		break;

	    case URELBOW:

		addSprite("URElbow.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.LEFT, WaterDirection.UP));

		break;

	    case VSQUIGGLY:

		addSprite("VSquiggly.png", "block");
		
		turningPoints.add(new TurningPoint(16f / 32f, 11f / 32f, WaterDirection.UP, WaterDirection.LEFT));
		turningPoints.add(new TurningPoint(7f / 32f, 11f / 32f, WaterDirection.LEFT, WaterDirection.UP));
		turningPoints.add(new TurningPoint(7f / 32f, 21f / 32f, WaterDirection.UP, WaterDirection.RIGHT));
		turningPoints.add(new TurningPoint(16f / 32f, 21f / 32f, WaterDirection.RIGHT, WaterDirection.UP));

		break;

	    case VSTRAIGHT:

		addSprite("VStraight.png", "block");
		
		turningPoints.add(new TurningPoint(0.5f, 0.5f, WaterDirection.UP, WaterDirection.UP));

		break;

	    default:
		break;
	    }

	visible = true;
    }
    
    private WaterDirection invertWaterDirection(WaterDirection direction)
    {
	WaterDirection invertedWaterDirection;

	switch (direction)
	{
	case DOWN:
	    invertedWaterDirection = WaterDirection.UP;
	    break;

	case LEFT:
	    invertedWaterDirection = WaterDirection.RIGHT;
	    break;

	case RIGHT:
	    invertedWaterDirection = WaterDirection.LEFT;
	    break;

	case UP:
	    invertedWaterDirection = WaterDirection.DOWN;
	    break;

	default:
	    invertedWaterDirection = null;
	    break;
	}

	return invertedWaterDirection;
    }
    
    public boolean hasDirectionalEntrace(WaterDirection direction)
    {
	if (type == BuildingBlockType.CROSS)
	    return true;
	
	if (direction == turningPoints.get(0).previousWaterDirection)
	{
	    return true;
	}
	else if (direction == invertWaterDirection(turningPoints.get(turningPoints.size() - 1).targetWaterDirection))
	{
	    if (!oneWay)
		return true;
	    else
		return false;
	}
	
	return false;
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
	public TurningPoint(float x, float y, WaterDirection previousWaterDirection, WaterDirection targetWaterDirection)
	{
	    this.x = x;
	    this.y = y;
	    this.previousWaterDirection = previousWaterDirection;
	    this.targetWaterDirection = targetWaterDirection;
	}
	
	public float x;
	public float y;
	public WaterDirection previousWaterDirection;
	public WaterDirection targetWaterDirection;
    }
}
