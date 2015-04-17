package com.mygdx.fruitwars.modes;

public interface GameMode {
	
	public int getTurnTime();
	
	public int getMinionsHealth();

	public boolean gameFinished();
}
