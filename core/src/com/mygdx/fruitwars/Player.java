//package com.mygdx.fruitwars;
//
//import java.util.ArrayList;
//
//import com.mygdx.fruitwars.entities.Minion;
//
//public class Player {
//	private final String name;
//	private int score;
//	ArrayList<Minion> minions;
//	Minion activeMinion;
//
//	public Player(String name, int score, ArrayList<Minion> minions) {
//		this.name = name;
//		this.score = score;
//		this.minions = minions;
//	}
//
//	public int getScore() {
//		return score;
//	}
//
//	public void setScore(int score) {
//		this.score = score;
//	}
//
//	public String getName() {
//		return name;
//	}
//	
//	public Minion getActiveMinion() {
//		return activeMinion;
//	}
//	
//	public ArrayList<Minion> getMinions() {
//		return minions;
//	}
//	
//	public void removeMinion(Minion minion) {
//		minions.remove(minion);
//	}
//	
//	public void selectNextMinion() {
//		if (activeMinion == null) {
//			if (!minions.isEmpty()) {
//				activeMinion = minions.get(0);
//			}
//		} else {
//			int idx = getMinionIndex(activeMinion);
//			if (idx+1 >= minions.size()) {
//				activeMinion = minions.get(0);
//			} else {
//				activeMinion = minions.get(idx+1);
//			}
//		}
//	}
//	
//	private int getMinionIndex(Minion minion) {
//		for (int i=0; i<minions.size(); i++) {
//			if (minions.get(i).equals(minion)) {
//				return i;
//			}
//		}
//		return -1;
//	}
//	
//	public void dispose() {
//		for (Minion minion : minions) {
//			minion.getTexture().dispose();
//		}
//	}
//	
//	
//}
