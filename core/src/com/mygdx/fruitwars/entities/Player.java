package com.mygdx.fruitwars.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Player extends Entity implements InputProcessor {
	
	private boolean canJump = false;
	private boolean facingRight = false;
	private float speed = 120;
	private float jumpSpeed = 600;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite, collisionLayer);
	}
	
	public void update(float delta) {
		
		// Apply gravity
		velocity.y -= gravity * delta;
		
		// clamp gravity
		if (velocity.y > speed) {
			velocity.y = speed;
		} else if (velocity.y < -speed) {
			velocity.y = -speed;
		}
		
		// Save old position
		float oldX = getX();
		float oldY = getY();
		float tileWidth = collisionLayer.getTileWidth();
		float tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;
		
		Cell cellToCheck;
		
		// Move on X axis
		setX(getX() + velocity.x * delta);
		
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
		
		// React to X collision
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		
		
		// Move on Y axis
		setY(getY() + velocity.y * delta);
		
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
			
			canJump = collisionY;
			
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
		
		// React to Y collision
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if (canJump) {
				velocity.y = jumpSpeed;
				canJump = false;
			}
			break;
		case Keys.A:
			velocity.x = -speed;
			if (facingRight) flipSprite();
			break;
		case Keys.D:
			velocity.x = speed;
			if (!facingRight) flipSprite();
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.A:
		case Keys.D:
			velocity.x = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	private void flipSprite() {
		this.flip(true, false);
		facingRight = !facingRight;
	}
}
