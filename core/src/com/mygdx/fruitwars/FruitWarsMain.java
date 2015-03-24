package com.mygdx.fruitwars;

import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.math.Vector2;
import com.mygdx.fruitwars.screens.GameScreen;

public class FruitWarsMain extends Game {
//	SpriteBatch batch;
//	BitmapFont font;
//	OrthographicCamera camera;
//	TextureRegion textureRegion;
//	Vector2 position;
//	float moveSpeed;
//	boolean movingRight;
//	float delta;
	
	@Override
	public void create () {
		setScreen(new GameScreen());
		
//		batch = new SpriteBatch();
//		font = new BitmapFont(true);
//		textureRegion = new TextureRegion(new Texture(Gdx.files.internal("test.png")));
//		textureRegion.flip(true, true);
//		position = new Vector2(100,100);
//		moveSpeed = 50;
//		movingRight = true;
//		camera = new OrthographicCamera();
//		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
//		batch.dispose();
//		font.dispose();
//		textureRegion.getTexture().dispose();
	}

	@Override
	public void render () {
		super.render();
//		delta = Gdx.graphics.getDeltaTime();
//		
//		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//			if (!movingRight) {
//				changeDirection();
//			}
//			position.x += moveSpeed * delta;
//		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//			if (movingRight) {
//				changeDirection();
//			}
//			position.x -= moveSpeed * delta;
//		}
//		
//		Gdx.gl.glClearColor(0, 0, 0.2f, 1.0f);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		
//		camera.update();
//		batch.setProjectionMatrix(camera.combined);
//		
//		batch.begin();
//		font.draw(batch, "Testing: " + movingRight, 20, 20);
//		batch.draw(textureRegion, position.x, position.y);
//		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
//		camera.setToOrtho(true, width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	
//	private void changeDirection() {
//		movingRight = !movingRight;
//		textureRegion.flip(true, false);
//	}
}
