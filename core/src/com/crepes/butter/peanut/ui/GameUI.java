package com.crepes.butter.peanut.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.crepes.butter.peanut.Entity;
import com.crepes.butter.peanut.scenes.GameScene;

public class GameUI extends Entity
{
    public GameScene gameScene;
    
    public Clock clock;
    public HotKeys hkManager;
    public LevelCount levelCount;
    public Loops loopsManager;
    public Score scoreManager;
    public ScoreNeeded scoreNeededManager;
    public TitleInformation titleInformation;
    
    public GameUI(GameScene gameScene)
    {
	super(0f, 0f, 0f, 0f);
	
	this.gameScene = gameScene;
	
	clock = new Clock(gameScene);
	hkManager = new HotKeys();
	levelCount = new LevelCount();
	loopsManager = new Loops(gameScene);
	scoreManager = new Score(gameScene);
	scoreNeededManager = new ScoreNeeded(gameScene);
	titleInformation = new TitleInformation();
    }
    
    public void reset()
    {
	clock.reset();
	levelCount.reset();
	loopsManager.reset();
	scoreManager.reset();
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
	clock.act(delta);
	hkManager.act(delta);
	levelCount.act(delta);
	loopsManager.act(delta);
	scoreManager.act(delta);
	scoreNeededManager.act(delta);
	titleInformation.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
	batch.end();
	batch.begin();
	
	clock.draw(batch, parentAlpha);
	hkManager.draw(batch, parentAlpha);
	levelCount.draw(batch, parentAlpha);
	loopsManager.draw(batch, parentAlpha);
	scoreManager.draw(batch, parentAlpha);
	scoreNeededManager.draw(batch, parentAlpha);
	titleInformation.draw(batch, parentAlpha);
	
	batch.end();
	batch.begin();
    }
}
