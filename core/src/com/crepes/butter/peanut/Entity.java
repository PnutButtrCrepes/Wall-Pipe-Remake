package com.crepes.butter.peanut;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Entity extends Actor
{
    private final float DISPLAY_SCALE = 32;
    
    private HashMap<String, Sprite> sprites;
    
    protected float unscaledX;
    protected float unscaledY;
    
    protected float unscaledWidth;
    protected float unscaledHeight;

    public Entity(float x, float y, float sizeX, float sizeY)
    {
	sprites = new HashMap<String, Sprite>();
	
	unscaledX = x;
	unscaledY = y;
	
	unscaledWidth = sizeX;
	unscaledHeight = sizeY;
	
	this.setPosition(x * DISPLAY_SCALE, y * DISPLAY_SCALE);
	this.setSize(sizeX * DISPLAY_SCALE, sizeY * DISPLAY_SCALE);
    }
    
    protected void addSprite(String fileName, String alias)
    {
	Texture texture = new Texture(fileName);
	Sprite sprite = new Sprite();
	sprite.setRegion(texture);
	
	sprites.put(alias, sprite);
    }
    
    protected Sprite getSprite(String alias)
    {
	return sprites.get(alias);
    }
    
    public void init(float x, float y, float sizeX, float sizeY)
    {
	unscaledX = x;
	unscaledY = y;
	
	unscaledWidth = sizeX;
	unscaledHeight = sizeY;
	
	this.setPosition(x * DISPLAY_SCALE, y * DISPLAY_SCALE);
	this.setSize(sizeX * DISPLAY_SCALE, sizeY * DISPLAY_SCALE);
    }
    
    public float getUnscaledX()
    {
	return unscaledX;
    }
    
    public float getUnscaledY()
    {
	return unscaledY;
    }
    
    public float getUnscaledWidth()
    {
	return unscaledWidth;
    }
    
    public float getUnscaledHeight()
    {
	return unscaledHeight;
    }
    
    @Override
    public void setX(float x)
    {
	unscaledX = x;
	super.setX(x * DISPLAY_SCALE);
    }
    
    @Override
    public void setY(float y)
    {
	unscaledY = y;
	super.setY(y * DISPLAY_SCALE);
    }
    
    @Override
    public void setWidth(float width)
    {
	unscaledWidth = width;
	super.setWidth(width * DISPLAY_SCALE);
    }
    
    @Override
    public void setHeight(float height)
    {
	unscaledHeight = height;
	super.setHeight(height * DISPLAY_SCALE);
    }
}