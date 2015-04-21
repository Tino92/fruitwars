package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends Token {
	protected int damage = 50; // Hard-coded for testing purposes
	protected boolean destroy = false;
	protected float positionX, positionY;
	
	public float getPositionY() {
		return positionY;
	}
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	public float getPositionX() {
		return positionX;
	}
	public void setPositionX(float f) {
		this.positionX = f;
	}
	
	public Weapon(Texture tex) {
		super(tex);
	}
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
	public boolean getDestroy() {
		return this.destroy;
	}
	
	
}
