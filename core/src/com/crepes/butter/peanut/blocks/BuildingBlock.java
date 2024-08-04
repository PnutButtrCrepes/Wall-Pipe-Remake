package com.crepes.butter.peanut.blocks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.Water;
import com.crepes.butter.peanut.Water.WaterDirection;

public class BuildingBlock extends Entity {
	public BuildingBlockType type;

	public boolean oneWay;

	public boolean replaceable;
	public boolean watered;
	public boolean looped;

	public boolean beingReplaced;

	public ArrayList<TurningPoint> turningPoints;
	public TeleportPoint teleportPoint;

	public boolean visible;

	public BuildingBlock(BuildingBlockType type, float x, float y, float sizeX, float sizeY) {
		super(x, y, sizeX, sizeY);

		turningPoints = new ArrayList<BuildingBlock.TurningPoint>();

		replaceable = true;
		watered = false;
		looped = false;

		beingReplaced = false;

		this.type = type;
		this.oneWay = false;

		if (type != null)
			switch (this.type) {

			case BLANK:
				addSprite("NormalBrick.png", "block");
				replaceable = false;
				break;

			case CROSS:
				addSprite("Cross.png", "block");
				break;

			case DLELBOW:
				addSprite("DLElbow.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.DOWN));
				break;
				
			case DARK_DLELBOW:
				addSprite("DLElbowDark.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.DOWN));
				replaceable = false;
				break;

			case DRELBOW:
				addSprite("DRElbow.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.LEFT, WaterDirection.DOWN));
				break;
				
			case DARK_DRELBOW:
				addSprite("DRElbowDark.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.LEFT, WaterDirection.DOWN));
				replaceable = false;
				break;

			case HSQUIGGLY:
				addSprite("HSquiggly.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.UP, 8f / 32f, 16f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.RIGHT, 8f / 32f, 24f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.DOWN, 16f / 32f, 24f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.DOWN, WaterDirection.RIGHT, 16f / 32f, 8f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.UP, 24f / 32f, 8f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.RIGHT, 24f / 32f, 16f / 32f));
				break;

			case HSTRAIGHT:
				addSprite("HStraight.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.RIGHT));
				break;
				
			case L_BATHTUB:
				addSprite("TubL.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.RIGHT));
				replaceable = false;
				break;
				
			case R_BATHTUB:
				addSprite("TubR.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.RIGHT));
				replaceable = false;
				break;

			case ULELBOW:
				addSprite("ULElbow.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.UP));
				break;
				
			case DARK_ULELBOW:
				addSprite("ULElbowDark.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.UP));
				replaceable = false;
				break;

			case URELBOW:
				addSprite("URElbow.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.LEFT, WaterDirection.UP));
				break;
				
			case DARK_URELBOW:
				addSprite("URElbowDark.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.LEFT, WaterDirection.UP));
				replaceable = false;
				break;

			case VSQUIGGLY:
				addSprite("VSquiggly.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.LEFT, 16f / 32f, 11f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.LEFT, WaterDirection.UP, 7f / 32f, 11f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.RIGHT, 7f / 32f, 21f / 32f));
				turningPoints.add(new TurningPoint(WaterDirection.RIGHT, WaterDirection.UP, 16f / 32f, 21f / 32f));
				break;

			case VSTRAIGHT:
				addSprite("VStraight.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.UP));
				break;
				
			case U_VSTRAIGHT:
				addSprite("VStraightU.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.UP, WaterDirection.UP));
				this.oneWay = true;
				break;
				
			case D_VSTRAIGHT:
				addSprite("VStraightD.png", "block");

				turningPoints.add(new TurningPoint(WaterDirection.DOWN, WaterDirection.DOWN));
				this.oneWay = true;
				break;

			case TELEPORT_CROSS:
				addSprite("Teleporter.png", "block");
				replaceable = false;
				break;

			default:
				break;
			}

		visible = true;
	}

	public BuildingBlock(BuildingBlockType type) {
		this(type, 0, 0, 1, 1);
	}

	public void addTeleportPoint(float teleportX, float teleportY) {
		teleportPoint = new TeleportPoint(0.5f, 0.5f, teleportX, teleportY);
	}

	public boolean hasDirectionalEntrace(WaterDirection direction) {
		
		if (type == null)
			return false;
		
		if (type == BuildingBlockType.CROSS || type == BuildingBlockType.TELEPORT_CROSS)
			return true;
		
		if (type == BuildingBlockType.BLANK)
			return false;

		if (direction == turningPoints.get(0).previousWaterDirection) {
			return true;
		} else if (direction == Water
				.invertWaterDirection(turningPoints.get(turningPoints.size() - 1).targetWaterDirection)) {
			if (!oneWay)
				return true;
			else
				return false;
		}

		return false;
	}

	public boolean isReplaceable() {
		return (replaceable && !watered);
	}

	@Override
	public void act(float delta) {

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		this.toFront();

		batch.end();
		batch.begin();

		if (visible)
			batch.draw(getSprite("block"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public enum BuildingBlockType {
		BLANK,

		CROSS, ULELBOW, URELBOW, DLELBOW, DRELBOW, HSTRAIGHT, VSTRAIGHT, HSQUIGGLY, VSQUIGGLY,

		L_BATHTUB, R_BATHTUB,

		DARK_ULELBOW, DARK_URELBOW, DARK_DLELBOW, DARK_DRELBOW, U_VSTRAIGHT, D_VSTRAIGHT,

		TELEPORT_CROSS,

		DLELBOW_50, DRELBOW_50,

		SINK
	}

	public class TurningPoint {
		public WaterDirection previousWaterDirection;
		public WaterDirection targetWaterDirection;

		public float x;
		public float y;

		public TurningPoint(WaterDirection previousWaterDirection, WaterDirection targetWaterDirection, float x,
				float y) {
			this.previousWaterDirection = previousWaterDirection;
			this.targetWaterDirection = targetWaterDirection;

			this.x = x;
			this.y = y;
		}

		public TurningPoint(WaterDirection previousWaterDirection, WaterDirection targetWaterDirection) {
			this(previousWaterDirection, targetWaterDirection, 0.5f, 0.5f);
		}
	}

	public class TeleportPoint {
		public float x;
		public float y;

		public float teleportX;
		public float teleportY;

		public TeleportPoint(float x, float y, float teleportX, float teleportY) {
			this.x = x;
			this.y = y;

			this.teleportX = teleportX;
			this.teleportY = teleportY;
		}
	}
}
