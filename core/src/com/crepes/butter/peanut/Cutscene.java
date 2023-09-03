package com.crepes.butter.peanut;

import com.badlogic.gdx.graphics.g2d.Batch;

public class Cutscene extends Entity
{
    public CutsceneType cutsceneType;
    
    public Cutscene(CutsceneType cutsceneType)
    {
	super(0, 0, 0, 0);
	this.cutsceneType = cutsceneType;
    }
    
    public void advanceCutscene()
    {
	switch(cutsceneType)
	{
	case LEVEL_END:
	    
	    break;
	    
	case MAIN_MENU:
	    
	    break;
	}
    }
    
    @Override
    public void act(float delta)
    {
	
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
	
    }
    
    public enum CutsceneType
    {
	MAIN_MENU,
	LEVEL_END
    }
    
    public enum MainCutsceneStage
    {
	NATHAN_KEENAN,
	WALLPIPE,
	GAME_START
    }
    
    public enum LevelCutsceneStage
    {
	LEVEL_OVER,
	LOOP_BONUS_DISPLAY,
	LOOP_BONUS_ADD,
	REMOVING_PIPES_DISPLAY,
	REMOVING_PIPES_SELECT,
	REMOVING_PIPES_OUTLINE,
	REMOVING_PIPES_DELETE,
	ADVANCE_TO_NEXT_LEVEL,
	GAME_OVER,
	LEADERBOARD_ENTRY
    }
}
