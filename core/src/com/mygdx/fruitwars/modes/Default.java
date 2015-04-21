package com.mygdx.fruitwars.modes;

import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.utils.Constants;

public class Default implements GameMode{
	
	private static GameScreen gameScreen;
	
	private static Default instance = null;
	
	private Default() { }
	 
    public static synchronized Default getInstance(GameScreen screen) {
    	
    	gameScreen = screen;
    	
        if (instance == null) {
            instance = new Default();
        }
 
        return instance;
    }
    
	@Override
	public boolean gameFinished() {
		// (If all the minions are killed from any of the players finish the game.
		for(int i=0; i< Constants.NUM_PLAYERS ; i++){
			if (gameScreen.getPlayers().get(i).getMinions().size == 0)
				return true;
		}
		return false;
	}

	@Override
	public int getTurnTime() {
		return 60*Constants.DEFAULT_TURNTIME;
	}

	@Override
	public int getMinionsHealth() {
		return Constants.DEFAULT_MINION_HEALTH;
	}

}
