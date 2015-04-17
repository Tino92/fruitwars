package com.mygdx.fruitwars.modes;

public interface GameMode {
	
	public int getTimeLimit();
	
	public int getMinionsHealth();

	public boolean gameFinished();
}
