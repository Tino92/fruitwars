package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Weapon;

public class Player {
	
	private static String TAG = "Player";
	
	private Array<Minion> minions = new Array<Minion>();
	private Weapon weapon;
	private int playerNumber;
	public int activeMinion = 0;
	public boolean weaponFired = false;
	
	
	public Player(int playerNumber, Array<Minion> minions){
		
		this.playerNumber = playerNumber;
		this.minions = minions;
		
	}
	
	public int getScore() {
		int score=0;
		for(Minion minion: minions)
			score += minion.getHealth();
		return score;
	}
	

	public int getPlayerNumber() {
		return playerNumber+1;
	}
	
	public Minion getActiveMinion() {
		Gdx.app.debug(TAG, "getActiveMinion: " + activeMinion + " from Player: " + playerNumber);
		return minions.get(activeMinion);
	}
	
	public void nextMinion(){
		activeMinion = (activeMinion+1) % minions.size; 
	}
	
	public Array<Minion> getMinions() {
		return minions;
	}
	
	public void removeMinion(Minion minion) {
		minions.removeValue(minion, true);
	}
	
	
}