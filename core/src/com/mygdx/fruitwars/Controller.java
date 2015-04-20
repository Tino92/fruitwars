package com.mygdx.fruitwars;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.fruitwars.screens.GameScreen;

public class Controller implements InputProcessor{
	
	private GameScreen gameScreen;
	
	private Vector2 lastTouch = new Vector2();
	
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
			
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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
