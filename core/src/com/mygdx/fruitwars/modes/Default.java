package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.utils.Constants;

public class Default implements GameMode{

	@Override
	public boolean gameFinished() {
		
		return false;
	}

	@Override
	public int getTimeLimit() {
		return Constants.DEFAULT_TIMELIMIT;
	}

	@Override
	public int getMinionsHealth() {
		return Constants.DEFAULT_MINION_HEALTH;
	}

}
