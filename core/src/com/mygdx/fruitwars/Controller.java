package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.tokens.Crosshairs;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Projectile;
import com.mygdx.fruitwars.tokens.ProjectileCostume;
import com.mygdx.fruitwars.tokens.SpriteCostume;
import com.mygdx.fruitwars.tokens.WeaponCostume;
import com.mygdx.fruitwars.tokens.WeaponSprite;

public class Controller {
	private World world;
	private GameScreen gameScreen;
	private long lastFire = System.currentTimeMillis();
	private boolean fireMode;
	private boolean aiming;
	private final float MAX_FIRE_VELOCITY = 100;
	private WeaponSprite weapon;
	private double weaponAngle;
	private int power = 1;
	private int charging = 1;
	private Vector2 bulletVelocity = new Vector2(50, 50);
	private Crosshairs crosshairs;
	
	
	boolean bulletFired = false;
	
	public Controller(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	
	public void render(float dt) {
		Minion activeMinion = gameScreen.getActiveMinion();
		Body activeBody = gameScreen.getActiveBody();
		// Check if movement buttons are pressed
		world = gameScreen.getWorld();
		if (gameScreen.getMoveRightBtn().isPressed()) {
//			System.out.println("Move right btn pressed");
			activeMinion.move_right();
			
			// INSERT: move active minion right
			// INSERT: flip sprite if facing the other way
		} else if (gameScreen.getMoveLeftBtn().isPressed()) {
//			System.out.println("Move left btn pressed");
			activeMinion.move_left();
//			activeBody.applyForceToCenter(new Vector2(-20f, 20f), true);
			// INSERT: move active minion left
			// INSERT: flip sprite if facing the other way
		}
		
		// INSERT if (minion is on ground && gameScreen.getJumpBtn().isPressed())
		//            INSERT set y velocity to jump speed 
		
		
		if(activeBody.isAwake()) {
			return;	
		}
		
		if (gameScreen.getFireBtn().isPressed()) {
			System.out.println("Fire btn pressed");
			//Vector2 position = new Vector2(activeBody.getPosition().x, activeBody.getPosition().y);
			if (weapon == null) {
		//		weapon = new WeaponSprite(world, activeBody.getWorldCenter(), new Vector2(32, 16), WeaponCostume.BAZOOKA);
			}
			toggleFireMode(true);
		}
		
		if (gameScreen.getAbortFireBtn().isPressed()) {
			System.out.println("Abort fire btn pressed");
			toggleFireMode(false);
		}
		
		if (fireMode && Gdx.input.isTouched() && !gameScreen.getFireBtn().isPressed()) {
			aiming = true;
		}
		
		if (weapon != null) {
		//	weapon.setPositionX(activeBody.getPosition().x * 30);
		//	weapon.setPositionY(activeBody.getPosition().y * 30);
			float dist_x = weapon.getPositionX() - Gdx.input.getX();
			float dist_y = weapon.getPositionY() - Gdx.input.getY();
			weaponAngle = Math.atan2(- dist_y,- dist_x);
		//	weapon.setPosition(dist_x, dist_y);
			weapon.setRotation((float) (weaponAngle*57.2957795));
			
			//weapon.rotate((float) (weaponAngle));
		}
		
		if (aiming && System.currentTimeMillis() - lastFire >= 50) {
			if (Gdx.input.isTouched()) { // Still aiming		
				aim();
				System.out.println("input is touched");
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
		System.out.println("aiming");
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
		
		
		if ((touchPosition.x > activeMinion.getPosition().x/* && activeMinion.isFacingRight()*/) || (touchPosition.x < activeMinion.getPosition().x/* && !activeMinion.isFacingRight()*/)) {
	//		activeMinion.flipSprite();
		}
		crosshairsPos.x += bulletVelocity.x / 3;
		crosshairsPos.y += bulletVelocity.y / 3;
		
		if (crosshairs == null) {
			crosshairs = new Crosshairs(world, crosshairsPos, SpriteCostume.CROSSHAIR);
			crosshairs.setOriginCenter();
			System.out.println("crosshair");
		} else {
//			crosshairs.setPosition(crosshairsPos.x, crosshairsPos.y);
			crosshairs.getBody().setTransform(crosshairsPos.x, crosshairsPos.y, 0);
			
		}		
		
		
	}
	
	private void fireBullet() {
		System.out.println("last fire" + lastFire + "----current_time" + System.currentTimeMillis());
		Body activeMinion = gameScreen.getActiveBody();
		Minion minion = gameScreen.getActiveMinion();
		if (System.currentTimeMillis() - lastFire >= 350) {
			System.out.println("Fire bullet");
			float velocityX = 200;	
			Vector2 bulletPosition = new Vector2(0, 0);
		//	bulletPosition.x = crosshairs.getBody().getPosition().x + activeMinion.getPosition().x;
			//bulletPosition.y = crosshairs.getBody().getPosition().y + activeMinion.getPosition().y;
			
			bulletPosition.x = activeMinion.getPosition().x + 32;
			bulletPosition.y = activeMinion.getPosition().y + 32;
		
			/*	if (!activeMinion.isFacingRight()) {
				velocityX = -velocityX; 
			}*/
			//Bullet bullet = new Bullet(new Sprite(new Texture("bullet.png")), collisionLayer, new Vector2(activeMinion.getX(), activeMinion.getY()), bulletVelocity, true);
			//activeMinion.setActive(false);
			minion.setRecently_fired(true);
			Projectile projectile = new Projectile(world, bulletPosition, bulletVelocity, ProjectileCostume.UZI);
			System.out.println("new projectile created");
			//addEntity(bullet);
			lastFire = System.currentTimeMillis();
		}
		//activeMinion.setActive(true);
		nextPlayer();
	}
	
	
	private void toggleFireMode(boolean value) {
		fireMode = value;
		gameScreen.getFireBtn().setVisible(!value);
		gameScreen.getAbortFireBtn().setVisible(value);
	}

}
