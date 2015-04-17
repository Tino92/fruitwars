package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.utils.Constants;

public class OneShot extends Default implements GameMode {

	public OneShot(GameScreen gameScreen) {
		super(gameScreen);
	}

	@Override
	public int getTurnTime() {
		return Constants.DEFAULT_TURNTIME;
	}

	@Override
	public int getMinionsHealth() {
		return Constants.ONE_SHOT_MINION_HEALTH;
	}

}
