package com.crepes.butter.peanut;

import java.io.Serializable;

public class LeaderboardEntry implements Serializable {

	private static final long serialVersionUID = -7918000496098106098L;

	String name;
	public int score;
	public int level;
	String date;

	public LeaderboardEntry() {
		name = "---";
		score = 0;
		level = 0;
		date = "--/--/----";
	}

	public LeaderboardEntry(String name, int score, int level, String date) {

		this.name = name;
		this.score = score;
		this.level = level;
		this.date = date;
	}
}
