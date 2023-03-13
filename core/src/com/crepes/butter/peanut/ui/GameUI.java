package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.GameScene;

public class GameUI extends Entity
{
    public GameScene gameScene;
    
    public HotKeysManager hkManager;
    public LoopsManager loopsManager;
    public ScoreNeededManager scoreNeededManager;
    
    public GameUI(GameScene gameScene)
    {
	super(0f, 0f, 0f, 0f);
	
	this.gameScene = gameScene;
	
	hkManager = new HotKeysManager(gameScene);
	loopsManager = new LoopsManager(gameScene);
	scoreNeededManager = new ScoreNeededManager(gameScene);
    }
    
    public void reset()
    {
	loopsManager.reset();
	scoreNeededManager.reset();
    }
    
    public float getBonus()
    {
	return loopsManager.getBonus();
    }

    public void addBonus()
    {
	loopsManager.addBonus();
    }
    
    @Override
    public void act(float delta)
    {
	hkManager.act(delta);
	loopsManager.act(delta);
	scoreNeededManager.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
	batch.end();
	batch.begin();
	
	// hotkeys
	hkManager.draw(batch, parentAlpha);
	
	// loops
	loopsManager.draw(batch, parentAlpha);
	
	// score
	scoreNeededManager.draw(batch, parentAlpha);
	
	batch.end();
	batch.begin();
    }
}
