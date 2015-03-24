package com.mygdx.fruitwars.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.fruitwars.entities.Player;

public class GameScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	
	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1);
		camera = new OrthographicCamera();
		
		player = new Player(new Sprite(new Texture("worm.png")), (TiledMapTileLayer) map.getLayers().get(0));
		player.setPosition(15 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 6) * player.getCollisionLayer().getTileHeight());
		
		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched(0)) {
	        System.out.println("Touch position: " + Gdx.input.getX() + ", " + Gdx.input.getY());
	        System.out.println("Player position: " + player.getX() + ", " + player.getY() + "\n");
		}
		
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		
		Batch batch = renderer.getBatch();
		batch.begin();
		player.draw(batch);
		batch.end();
		
		camera.update();
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
//		camera.viewportWidth = width;
//		camera.viewportHeight = height;
//		camera.update();
		camera.setToOrtho(false, width, height);
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}

}
