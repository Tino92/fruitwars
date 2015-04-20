package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends Token {
	protected int damage = 50; // Hard-coded for testing purposes
	
	public Weapon(Texture tex) {
		super(tex);
	}
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
