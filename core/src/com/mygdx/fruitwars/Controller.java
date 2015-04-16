package com.mygdx.fruitwars;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.fruitwars.screens.GameScreen;

public class Controller implements InputProcessor{
	
	private GameScreen gameScreen;
	
	private Vector2 touchDown;
	
	
	public Controller(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}
	

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT)
			gameScreen.camera.translate(-32, 0);
		if (keycode == Input.Keys.RIGHT)
			gameScreen.camera.translate(32, 0);
		if (keycode == Input.Keys.UP)
			gameScreen.camera.translate(0, -32);
		if (keycode == Input.Keys.DOWN)
			gameScreen.camera.translate(0, 32);
		if (keycode == Input.Keys.NUM_1)
			gameScreen.map.getLayers().get(0)
					.setVisible(!gameScreen.map.getLayers().get(0).isVisible());
		if (keycode == Input.Keys.NUM_2)
			gameScreen.map.getLayers().get(1)
					.setVisible(!gameScreen.map.getLayers().get(1).isVisible());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchDown = new Vector2(screenX, gameScreen.camera.viewportHeight-screenY);
//		this.minions.add(new Minion(world, touchDown, new Vector2(32, 32)));
		
		/*Vector2 bulletVelocity = new Vector2(500, 100);
		if (weapon == null) {
			Texture texture = new Texture("sprites/bullet.png");
			weapon = new Weapon(world, touchDown, bulletVelocity, texture);
		} else {
			weapon.setPosition(touchDown);
			weapon.getBody().applyForceToCenter(bulletVelocity.x, bulletVelocity.y, true);
		}*/
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchDown = null; 
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
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
