package com.mygdx.fruitwars.tokens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;

public class Token extends Box2DSprite {
	protected int health = 100; // Hard-coded for testing purposes
	protected Body body;
	
	public Token(Texture tex, Body body) {
		super(tex);
		this.body = body;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public Body getBody() {
		return this.body;
	}
}
