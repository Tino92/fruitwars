package com.mygdx.fruitwars.tokens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends Box2DSprite {
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
