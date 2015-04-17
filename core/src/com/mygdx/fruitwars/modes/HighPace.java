package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.utils.Constants;

public class HighPace extends Default implements GameMode{

	public HighPace(GameScreen gameScreen) {
		super(gameScreen);
	}
	
	@Override
	public int getTurnTime() {
		return Constants.FAST_TURNTIME;
	}

	@Override
	public int getMinionsHealth() {
		return Constants.DEFAULT_MINION_HEALTH;
	}
	

}
