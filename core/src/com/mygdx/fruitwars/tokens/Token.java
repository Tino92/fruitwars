package com.mygdx.fruitwars.tokens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

public class Token extends Box2DSprite {
	protected int health = 100; // Hard-coded for testing purposes
	protected Body body;
	
	public Token(Texture tex, Body body) {
		super(tex);
		this.body = body;
	}
	
	public Token(Texture tex) {
		super(tex);
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
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	public void draw(Batch batch, Body body) {
		if(body.getPosition().y<0) {
			this.health=0;
		}
		super.draw(batch, body);
	}
}
