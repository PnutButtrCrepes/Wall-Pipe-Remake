package com.crepes.butter.peanut.ui;

import java.io.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.crepes.butter.peanut.*;

public class LeaderboardManager extends Entity
{

    public LeaderboardEntry[] leaderboardEntries;

    File leaderboard;

    String names;
    String scores;
    String levels;
    String dates;

    public LeaderboardManager()
    {

	super(2f, 2f, 14f, 11f);

	this.sprite = new Sprite();
	this.texture = new Texture("Water.png");
	sprite.setTexture(texture);

	leaderboardEntries = new LeaderboardEntry[10];

	leaderboardEntries[0] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[1] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[2] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[3] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[4] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[5] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[6] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[7] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[8] = new LeaderboardEntry("---", 0, 0, "--/--/----");
	leaderboardEntries[9] = new LeaderboardEntry("---", 0, 0, "--/--/----");

	try
	{

	    leaderboard = Gdx.files.internal("leaderboard.dat").file();
	    leaderboard.createNewFile();

	    readScores();

	} catch (Exception e)
	{

	    e.printStackTrace();
	}

	names = "";
	scores = "";
	levels = "";
	dates = "";

	this.setVisible(false);
    }

    public void reset()
    {

    }

    public void writeScores() throws IOException
    {

	FileOutputStream fo = new FileOutputStream(leaderboard);
	ObjectOutputStream oo = new ObjectOutputStream(fo);

	for (int i = 0; i < leaderboardEntries.length; i++)
	{

	    oo.writeObject(leaderboardEntries[i]);
	}

	oo.close();
	fo.close();
    }

    public void readScores() throws IOException, ClassNotFoundException
    {

	FileInputStream fi = new FileInputStream(leaderboard);
	ObjectInputStream oi;

	try
	{

	    oi = new ObjectInputStream(fi);

	} catch (EOFException e)
	{

	    fi.close();

	    return;
	}

	Read: for (int i = 0; i < leaderboardEntries.length; i++)
	{

	    try
	    {

		leaderboardEntries[i] = (LeaderboardEntry) oi.readObject();

	    } catch (EOFException e)
	    {

		break Read;
	    }
	}

	oi.close();
	fi.close();
    }

    public void addScore(LeaderboardEntry le)
    {

	leaderboardEntries[9] = le;

	sortScores();

	try
	{

	    writeScores();

	} catch (IOException e)
	{

	    e.printStackTrace();
	}

	compileScores();
    }

    public void compileScores()
    {

	names = "";
	scores = "";
	levels = "";
	dates = "";

	for (int i = 0; i < leaderboardEntries.length; i++)
	{

	    names = names + leaderboardEntries[i].name + "\n";
	    scores = scores + leaderboardEntries[i].score + "\n";
	    levels = levels + leaderboardEntries[i].level + "\n";
	    dates = dates + leaderboardEntries[i].date + "\n";
	}
    }

    public void sortScores()
    {

	LeaderboardEntry temp;

	for (int i = 0; i <= leaderboardEntries.length; i++)
	{
	    for (int j = 0; j <= leaderboardEntries.length - 2; j++)
	    {

		if ((leaderboardEntries[j].score < leaderboardEntries[j + 1].score)
			|| (leaderboardEntries[j].score == leaderboardEntries[j + 1].score
				&& leaderboardEntries[j].level < leaderboardEntries[j + 1].level))
		{

		    temp = leaderboardEntries[j];
		    leaderboardEntries[j] = leaderboardEntries[j + 1];
		    leaderboardEntries[j + 1] = temp;
		}
	    }
	}
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

	batch.draw(sprite, this.getX(), this.getY(), this.getWidth(), this.getHeight());

	FontHelper.font.getData().setScale(1.5f, 1.5f);
	FontHelper.font.setColor(1f, 1f, 0f, 1);
	FontHelper.font.draw(batch, "WALLPIPE SCOREBOARD", this.getX() + 60, this.getY() + 335);

	FontHelper.font.getData().setScale(0.7f, 0.7f);
	FontHelper.font.draw(batch, "NAME", this.getX() + 50, this.getY() + 285);

	FontHelper.font.getData().setScale(0.7f, 0.7f);
	FontHelper.font.draw(batch, "SCORE", this.getX() + 150, this.getY() + 285);

	FontHelper.font.getData().setScale(0.7f, 0.7f);
	FontHelper.font.draw(batch, "LEVEL", this.getX() + 250, this.getY() + 285);

	FontHelper.font.getData().setScale(0.7f, 0.7f);
	FontHelper.font.draw(batch, "DATE", this.getX() + 350, this.getY() + 285);

	FontHelper.font.getData().setScale(0.6f, 0.7f);
	FontHelper.font.draw(batch, names, this.getX() + 50, this.getY() + 260);

	FontHelper.font.getData().setScale(0.6f, 0.7f);
	FontHelper.font.draw(batch, scores, this.getX() + 150, this.getY() + 260);

	FontHelper.font.getData().setScale(0.6f, 0.7f);
	FontHelper.font.draw(batch, levels, this.getX() + 250, this.getY() + 260);

	FontHelper.font.getData().setScale(0.6f, 0.7f);
	FontHelper.font.draw(batch, dates, this.getX() + 350, this.getY() + 260);

	FontHelper.font.setColor(1f, 1f, 1f, 1);

    }
}