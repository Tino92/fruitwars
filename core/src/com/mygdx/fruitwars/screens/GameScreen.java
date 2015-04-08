package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.fruitwars.FruitWarsMain;

public class GameScreen implements Screen{

	final FruitWarsMain game;
	
	OrthographicCamera camera;
	
	Texture bananaImage;
	
	Rectangle banana;
	
	public GameScreen(final FruitWarsMain fruitWars) {
		this.game = fruitWars;
		
		// Load images
		bananaImage = new Texture(Gdx.files.internal("banana.gif"));
		
		// Create camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Create rectangle to logically represent the banana
		banana = new Rectangle();
		banana.x = 800 / 2 - 64 / 2; // Center horizontally
		banana.y = 20;
		
		banana.width = 32;
		banana.height = 32;
	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.update();
        
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        game.batch.draw(bananaImage, banana.x, banana.y);
        game.batch.end();
        
        
        // Handle user input
        
        if (Gdx.input.isKeyPressed(Keys.LEFT))
        	banana.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
        	banana.x += 200 * Gdx.graphics.getDeltaTime();
        
        if (Gdx.input.isKeyPressed(Keys.UP))
        	banana.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Keys.DOWN))
        	banana.y -= 200 * Gdx.graphics.getDeltaTime();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
