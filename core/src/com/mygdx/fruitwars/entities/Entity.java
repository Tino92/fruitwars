package com.mygdx.fruitwars.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity extends Sprite {
	protected Vector2 velocity = new Vector2();
	protected final float gravity = 500;
	protected final float terminalVelocity = 150;
	protected final TiledMapTileLayer collisionLayer;
	protected float tileWidth;
	protected float tileHeight;
	protected final boolean destroyOnCollision;
	protected boolean toBeRemoved;
	
	public Entity(Sprite sprite, TiledMapTileLayer collisionLayer, Vector2 position, Vector2 velocity, boolean destroyOnCollision) {
		super(sprite);
		setPosition(position.x, position.y);
		this.velocity = velocity;
		this.destroyOnCollision = destroyOnCollision;
		this.collisionLayer = collisionLayer;
		tileWidth = collisionLayer.getTileWidth();
		tileHeight = collisionLayer.getTileHeight();
		toBeRemoved = false;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocityX(float x) {
		velocity.x = x;
	}

	public void setVelocityY(float y) {
		velocity.y = y;
	}

	public float getGravity() {
		return gravity;
	}
	
	public float getTerminalVelocity() {
		return terminalVelocity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	
	public boolean isToBeRemoved() {
		return toBeRemoved;
	}
	
	public Vector2 getPosition() {
		return new Vector2(getX(), getY());
	}

	@Override
	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	public abstract void update(float delta);
	
	public void updateEntityPosition(float delta) {
		// Apply gravity
		velocity.y -= gravity * delta;
		
		// clamp gravity
		if (velocity.y < -terminalVelocity) {
			velocity.y = -terminalVelocity;
		}
		
		// Save old position
		float oldX = getX();
		float oldY = getY();
		
		// Move on X axis
		setX(getX() + velocity.x * delta);
		
		boolean collisionX = checkCollisionX();
		
		// Move on Y axis
		setY(getY() + velocity.y * delta);
		
		boolean collisionY = checkCollisionY();
		
		if (this instanceof Player) {
			Player player = (Player) this;
			player.setCanJump(collisionY && velocity.y < 0);
		}
		
		// React to X collision
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		
		// React to Y collision
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		if ((collisionX || collisionY) && destroyOnCollision) {
			toBeRemoved = true;
		}
	}
	
	public boolean checkCollisionX() {
		boolean collisionX = false;
		Cell cellToCheck;
		
		if (velocity.x < 0) {
			// top left
			cellToCheck = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight));
			if (cellToCheck != null) {
				collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
			}
			
			
			// middle left
			if (!collisionX) {
				cellToCheck = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight));
				if (cellToCheck != null) {
					collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
				
			}
			
			// bottom left
			if (!collisionX) {
				cellToCheck = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight));
				if (cellToCheck != null) {
					collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
				
			}
		} else if (velocity.x > 0) {
			// top right
			cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight));
			if (cellToCheck != null) {
				collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
			}
			
			
			// middle right
			if (!collisionX) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight));
				if (cellToCheck != null) {
					collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
				
			}
			
			// bottom right
			if (!collisionX) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight));
				if (cellToCheck != null) {
					collisionX = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
			}
		}
		return collisionX;
	}
	
	public boolean checkCollisionY() {
		boolean collisionY = false;
		Cell cellToCheck;
		
		if (velocity.y < 0) {
			// bottom left
			cellToCheck = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight));
			if (cellToCheck != null) {
				collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
			}
			
			// bottom middle
			if (!collisionY) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeight));
				if (cellToCheck != null) {
					collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
			}
			
			// bottom right
			if (!collisionY) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight));
				if (cellToCheck != null) {
					collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
			}
		} else if (velocity.y > 0) {
			// top left
			cellToCheck = collisionLayer.getCell((int) ((getX()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight));
			if (cellToCheck != null) {
				collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
			}
			
			// top middle
			if (!collisionY) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight));
				if (cellToCheck != null) {
					collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
			}
			
			// top right
			if (!collisionY) {
				cellToCheck = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight));
				if (cellToCheck != null) {
					collisionY = cellToCheck.getTile().getProperties().containsKey("blocked");
				}
			}
		}
		return collisionY;
	}
	
	
}
