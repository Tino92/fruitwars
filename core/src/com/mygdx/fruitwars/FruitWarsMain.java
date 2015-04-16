package com.mygdx.fruitwars;

import static com.mygdx.fruitwars.utils.Constants.NORMAL;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.fruitwars.modes.GameMode;
import com.mygdx.fruitwars.screens.IntroScreen;

public class FruitWarsMain extends Game{
	
	
	public SpriteBatch batch;
	
	public int difficultyConfig = 0;
	
	//State pattern
	public GameMode gameMode;
	
	//Comment
	@Override
	public void create () {
		
		Preferences prefs = Gdx.app.getPreferences("fruitwars");
		prefs.putInteger("difficulty",NORMAL);
		
		batch = new SpriteBatch();
		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
	}
	
	public void setGameMode(GameMode gameMode){
		this.gameMode = gameMode;
	}
	
	public GameMode getGameMode(){
		return gameMode;
	}
}
