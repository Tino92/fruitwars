package com.mygdx.fruitwars.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {
	
	private boolean canJump = false;
	private boolean facingRight = false;
	private final float movementSpeed = 120;
	private final float jumpSpeed = 200;

	public Player(Sprite sprite, final TiledMapTileLayer collisionLayer, Vector2 position, Vector2 velocity, boolean destroyOnCollision) {
		super(sprite, collisionLayer, position, velocity, destroyOnCollision);
	}
	
	public boolean canJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public float getJumpSpeed() {
		return jumpSpeed;
	}
	
	public void update(float delta) {
		updateEntityPosition(delta);
	}
	
	public void flipSprite() {
		this.flip(true, false);
		facingRight = !facingRight;
	}
}
