package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.fruitwars.screens.GameScreen;

public class Controller {
	
	private GameScreen gameScreen;
	private long lastFire;
	private boolean fireMode;
	private boolean aiming;
	private final float MAX_FIRE_VELOCITY = 100;
	
	public Controller(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public void render(float dt) {
		
		// Check if movement buttons are pressed
		if (gameScreen.getMoveRightBtn().isPressed()) {
			System.out.println("Move right btn pressed");
			// INSERT: move active minion right
			// INSERT: flip sprite if facing the other way
		} else if (gameScreen.getMoveLeftBtn().isPressed()) {
			System.out.println("Move left btn pressed");
			// INSERT: move active minion left
			// INSERT: flip sprite if facing the other way
		}
		
		// INSERT if (minion is on ground && gameScreen.getJumpBtn().isPressed())
		//            INSERT set y velocity to jump speed 
		
		if (gameScreen.getFireBtn().isPressed()) {
			System.out.println("Fire btn pressed");
			toggleFireMode(true);
		}
		
		if (gameScreen.getAbortFireBtn().isPressed()) {
			System.out.println("Abort fire btn pressed");
			toggleFireMode(false);
		}
		
		if (fireMode && Gdx.input.isTouched() && !gameScreen.getFireBtn().isPressed()) {
			aiming = true;
		}
		
		if (aiming) {
			if (Gdx.input.isTouched()) { // Still aiming
				aim();
			} else { // Release bullet
				fireBullet();
				aiming = false;
				toggleFireMode(false);
			}
		}
		
//		if (!aiming && crosshairs != null) {
//			crosshairs.setToBeRemoved(true);
//			crosshairs = null;
//		}
	}
	
	
	
	private void aim() {
		Body activeMinion = gameScreen.getActiveBody();
		Vector3 touchPosYDown = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 unprojected = gameScreen.getCamera().unproject(touchPosYDown);
		Vector2 touchPosition = new Vector2(unprojected.x, unprojected.y);
		Vector2 playerPosition = new Vector2(activeMinion.getPosition().x, activeMinion.getPosition().y);
		
		float distance = touchPosition.dst(playerPosition);
		Vector2 crosshairsPos = playerPosition;
		
		if (distance > MAX_FIRE_VELOCITY) {
			Vector2 angleCalculationVector = new Vector2(touchPosition.x - playerPosition.x, touchPosition.y - playerPosition.y);
			float angle = angleCalculationVector.angleRad();
			
//			bulletVelocity.x = (float) -(Math.cos(angle)*MAX_FIRE_VELOCITY) * 6;
//			bulletVelocity.y = (float) -(Math.sin(angle)*MAX_FIRE_VELOCITY) * 6;
		} else {
//			bulletVelocity.x = -(touchPosition.x - playerPosition.x)*6;
//			bulletVelocity.y = -(touchPosition.y - playerPosition.y)*6;
		}
//		
//		
		if ((touchPosition.x > activeMinion.getPosition().x/* && activeMinion.isFacingRight()*/) || (touchPosition.x < activeMinion.getPosition().x/* && !activeMinion.isFacingRight()*/)) {
//			activeMinion.flipSprite();
		}
		
//		crosshairsPos.x += bulletVelocity.x / 3;
//		crosshairsPos.y += bulletVelocity.y / 3;
//		
//		if (crosshairs == null) {
//			crosshairs = new Crosshairs(new Sprite(new Texture("crosshairs.png")), collisionLayer, crosshairsPos, new Vector2(0,0), false);
//			crosshairs.setOriginCenter();
//			addEntity(crosshairs);
//		} else {
//			crosshairs.setPosition(crosshairsPos.x, crosshairsPos.y);
//		}
	}
	
	private void fireBullet() {
		if (System.currentTimeMillis() - lastFire >= 350) {
			float velocityX = 200;
//			if (!activeMinion.isFacingRight()) {
//				velocityX = -velocityX; 
//			}
//			Bullet bullet = new Bullet(new Sprite(new Texture("bullet.png")), collisionLayer, new Vector2(activeMinion.getX(), activeMinion.getY()), bulletVelocity, true);
//			addEntity(bullet);
			lastFire = System.currentTimeMillis();
		}
//		nextPlayer();
	}
	
	
	private void toggleFireMode(boolean value) {
		fireMode = value;
		gameScreen.getFireBtn().setVisible(!value);
		gameScreen.getAbortFireBtn().setVisible(value);
	}

}
