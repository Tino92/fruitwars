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
	    // delta will now hold the difference between the last and the current touch positions
	    // delta.x > 0 means the touch moved to the right, delta.x < 0 means a move to the left
	    Vector2 delta = newTouch.cpy().sub(lastTouch);
	    
	    if (delta.x>0)
	    	gameScreen.camera.translate(10, 0);
	    else
	    	gameScreen.camera.translate(-10,0);
	    
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
