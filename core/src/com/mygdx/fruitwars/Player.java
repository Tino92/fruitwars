package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Weapon;

public class Player {
	
	private static String TAG = "Player";
	
	private Array<Minion> minions = new Array<Minion>();
	private Weapon weapon;
	private int score = 0;
	private int playerNumber;
	private int activeMinion = 0;
	private boolean weaponFired = false;
	
	
	public Player(int playerNumber, Array<Minion> minions){
		
		this.playerNumber = playerNumber;
		this.minions = minions;
		
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public Minion getActiveMinion() {
		Gdx.app.debug(TAG, "getActiveMinion: " + activeMinion + " from Player: " + playerNumber);
		return minions.get(activeMinion++);
	}
	
	public Array<Minion> getMinions() {
		return minions;
	}
	
	public void removeMinion(Minion minion) {
		minions.removeValue(minion, true);
	}
	
	
	public void dispose() {
		for (Minion minion : minions) {
			minion.getTexture().dispose();
		}
	}
	
	
}
