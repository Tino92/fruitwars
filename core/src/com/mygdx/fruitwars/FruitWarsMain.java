package com.mygdx.fruitwars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.fruitwars.screens.IntroScreen;

public class FruitWarsMain extends Game{
	public SpriteBatch batch;
	public BitmapFont font;
	Texture img;
	
	//Comment
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		this.setScreen(new IntroScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
