package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.tokens.Crosshairs;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Projectile;
import com.mygdx.fruitwars.tokens.ProjectileCostume;
import com.mygdx.fruitwars.tokens.SpriteCostume;
import com.mygdx.fruitwars.utils.Constants;

public class Controller {
	private World world;
	private GameScreen gameScreen;
	private boolean fireMode;
	private boolean aiming;
	private final float MAX_FIRE_VELOCITY = 100;
	private Vector2 bulletVelocity = new Vector2(0, 0);
	private Crosshairs crosshairs;
	
	
	boolean bulletFired = false;
	
	public Controller(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public void render(float dt) {
		Minion activeMinion = gameScreen.getActiveMinion();
		Body activeBody = gameScreen.getActiveBody();
		world = gameScreen.getWorld();
		if (gameScreen.getMoveRightBtn().isPressed()) {
			activeMinion.move_right();
		} else if (gameScreen.getMoveLeftBtn().isPressed()) {
			activeMinion.move_left();
		}

		if(activeBody.isAwake()) {
			return;	
		}
		
		if (gameScreen.getFireBtn().isPressed()) {
			toggleFireMode(true);
		}
		
		if (gameScreen.getAbortFireBtn().isPressed()) {
			toggleFireMode(false);
		}
		
		if (fireMode && Gdx.input.isTouched() && !gameScreen.getFireBtn().isPressed()) {
			aiming = true;
		}
		
		
		if (aiming) {
			if (Gdx.input.isTouched()) { // Still aiming		
				aim();
			} 
			else {
			// Release bullet
				fireBullet();
				
			}

		}

		if (!aiming && crosshairs != null) {
			crosshairs.setSetToRemove(true);
			crosshairs = null;
		}
	}
	
	private void nextPlayer() {
		gameScreen.getFireBtn().setVisible(false);
		aiming = false;
		
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
			
			bulletVelocity.x = (float) -(Math.cos(angle)*MAX_FIRE_VELOCITY) * 6;
			bulletVelocity.y = (float) -(Math.sin(angle)*MAX_FIRE_VELOCITY) * 6;
		} else {
			bulletVelocity.x = -(touchPosition.x - playerPosition.x)*6;
			bulletVelocity.y = -(touchPosition.y - playerPosition.y)*6;
		}
			
		crosshairsPos.x += bulletVelocity.x / 3;
		crosshairsPos.y += bulletVelocity.y / 3;
		
		if (crosshairs == null) {
			crosshairs = new Crosshairs(world, crosshairsPos, SpriteCostume.CROSSHAIR);
			crosshairs.setOriginCenter();
		} else {
			crosshairs.getBody().setTransform(crosshairsPos.x, crosshairsPos.y, 0);
			
		}		
			
	}
	
	private void fireBullet() {
		Body activeBody = gameScreen.getActiveBody();
		System.out.println("Fire bullet");

		Vector2 bulletVector = new Vector2(crosshairs.getBody().getPosition().sub(activeBody.getPosition())); 

		float vectorLength = Constants.TILE_SIZE * 1.5f;
		//(float) Math.sqrt(2f * Math.pow(Constants.TILE_SIZE/2f, 2f)) + Constants.TILE_SIZE/1.5f; 
		bulletVector.scl(1/bulletVector.len()).scl(vectorLength);

		bulletVector.add(activeBody.getPosition());

		Projectile projectile = new Projectile(world, bulletVector, bulletVelocity, ProjectileCostume.UZI);

		System.out.println("new projectile created");
		nextPlayer();
	}

	
	private void toggleFireMode(boolean value) {
		fireMode = value;
		gameScreen.getFireBtn().setVisible(!value);
		gameScreen.getAbortFireBtn().setVisible(value);
	}

}
