package com.mygdx.fruitwars;

import com.badlogic.gdx.Gdx;
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

public class Controller implements InputProcessor{
	private World world;
	private GameScreen gameScreen;

	private long lastFire = System.currentTimeMillis();
	private boolean aiming;
	private final float MAX_FIRE_VELOCITY = 100;
	private Vector2 bulletVelocity = new Vector2(50, 50);
	private Crosshairs crosshairs;
	
	
	private Vector2 lastTouch = new Vector2();
	
	boolean bulletFired = false;
	
	public Controller(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	

	private void aim() {
		System.out.println("aim function");
		Body activeMinion = gameScreen.getCurrentPlayer().getActiveMinion().getBody();
		Vector3 touchPosYDown = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 unprojected = gameScreen.camera.unproject(touchPosYDown);
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
			System.out.println("crosshair");
		} else {
			crosshairs.getBody().setTransform(crosshairsPos.x, crosshairsPos.y, 0);
			
		}		
		
		
	}
	
	private void fireBullet() {
		System.out.println("last fire" + lastFire + "----current_time" + System.currentTimeMillis());
		Minion minion = gameScreen.getCurrentPlayer().getActiveMinion();
		Body activeMinion = minion.getBody();
		if (System.currentTimeMillis() - lastFire >= 350) {
			System.out.println("Fire bullet");
			float velocityX = 200;
			Vector2 bulletPosition = new Vector2(0, 0);

			
			bulletPosition.x = activeMinion.getPosition().x + 32;
			bulletPosition.y = activeMinion.getPosition().y + 32;
		

			minion.setRecently_fired(true);
			Projectile projectile = new Projectile(world, bulletPosition, bulletVelocity, ProjectileCostume.UZI);
			System.out.println("new projectile created");
			//addEntity(bullet);
			lastFire = System.currentTimeMillis();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (gameScreen.isPaused()){
			gameScreen.resume();
			return true;
		}
		
		lastTouch.set(screenX, screenY);
		
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
			
			
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if (!aiming && crosshairs != null) {
			crosshairs.setSetToRemove(true);
			crosshairs = null;
		}
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 newTouch = new Vector2(screenX, screenY);
	    Vector2 delta = newTouch.cpy().sub(lastTouch);
	    
	    if (delta.x>0)
	    	gameScreen.moveCamera(-5);
	    else
	    	gameScreen.moveCamera(5);
	    
	    lastTouch = newTouch;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
