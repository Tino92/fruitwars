package com.mygdx.fruitwars;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.fruitwars.screens.IntroScreen;
import com.mygdx.fruitwars.utils.Constants;

public class FruitWarsMain extends Game{
	
	
	public SpriteBatch batch;
	
	public int gameMode = Constants.DEFAULT_GAME_MODE;
	
	//Comment
	@Override
	public void create () {
		
		Preferences prefs = Gdx.app.getPreferences("fruitwars");
		prefs.putInteger("gameMode",Constants.DEFAULT_GAME_MODE);
		
		batch = new SpriteBatch();
		this.setScreen(new IntroScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
	}
}
