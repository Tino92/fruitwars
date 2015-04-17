package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.utils.Constants;

public class Juggernaut extends Default implements GameMode {

	public Juggernaut(GameScreen gameScreen) {
		super(gameScreen);
	}


	@Override
	public int getTurnTime() {
		return Constants.DEFAULT_TURNTIME;
	}

	@Override
	public int getMinionsHealth() {
		// TODO Auto-generated method stub
		return Constants.JUGGERNAUT_HEALTH;
	}

}
