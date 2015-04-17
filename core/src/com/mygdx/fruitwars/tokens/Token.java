package com.mygdx.fruitwars.tokens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.graphics.Texture;

public class Token extends Box2DSprite {
	protected int health;
	
	public Token(Texture tex) {
		super(tex);
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
}