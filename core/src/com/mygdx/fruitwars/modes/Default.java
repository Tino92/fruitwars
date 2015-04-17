package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.utils.Constants;

public class Default implements GameMode{
	
	private GameScreen gameScreen;
	
	public Default(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}

	@Override
	public boolean gameFinished() {
		// If all the minions are killed from any of the players finish the game.
		if (gameScreen.getPlayers().get(Constants.PLAYER1).getMinions().size == 0 
				|| gameScreen.getPlayers().get(Constants.PLAYER2).getMinions().size == 0)
			return true;
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
