package com.mygdx.fruitwars.screens;

import java.util.ArrayList;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.fruitwars.entities.Bullet;
import com.mygdx.fruitwars.entities.Entity;
import com.mygdx.fruitwars.entities.Player;

public class GameScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private TiledMapTileLayer collisionLayer;
	private Player player;
	private Stage stage;
	private ArrayList<Entity> entities;
	private TextButton moveLeftBtn;
	private TextButton moveRightBtn;
	private TextButton jumpBtn;
	private TextButton fireBtn;
	private TextButton abortFireBtn;
	private long lastFire;
	private boolean fireMode;
	private boolean aiming;
	private Vector2 bulletVelocity;
	
	@Override
	public void show() {
		stage = new Stage();
		
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1);
		camera = new OrthographicCamera();
		
		collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		float startPosX = 15 * collisionLayer.getTileWidth();
		float startPosY = (collisionLayer.getHeight() - 6) * collisionLayer.getTileHeight();
		
		player = new Player(new Sprite(new Texture("worm.png")), collisionLayer, new Vector2(startPosX, startPosY), new Vector2(0, 0), false);
		
		entities = new ArrayList<Entity>();
		entities.add(player);
		
//		Gdx.input.setInputProcessor(player);
		Gdx.input.setInputProcessor(stage);
		
		
		Skin skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
		
		moveLeftBtn = new TextButton("<", skin);
		moveLeftBtn.setPosition(60, 30);
		moveLeftBtn.setSize(60, 60);
		
		moveRightBtn = new TextButton(">", skin);
		moveRightBtn.setPosition(130, 30);
		moveRightBtn.setSize(60, 60);

		jumpBtn = new TextButton("^", skin);
		jumpBtn.setPosition(Gdx.graphics.getWidth() - 150, 30);
		jumpBtn.setSize(60, 60);
		
		fireBtn = new TextButton("x", skin);
		fireBtn.setPosition(Gdx.graphics.getWidth() - 220, 30);
		fireBtn.setSize(60, 60);
		
		abortFireBtn = new TextButton("Abort attack", skin);
		abortFireBtn.setPosition(Gdx.graphics.getWidth() - 170, Gdx.graphics.getHeight() - 80);
		abortFireBtn.setSize(150, 60);
		abortFireBtn.setVisible(false);
		
		stage.addActor(moveRightBtn);
		stage.addActor(moveLeftBtn);
		stage.addActor(jumpBtn);
		stage.addActor(fireBtn);
		stage.addActor(abortFireBtn);
		
		fireMode = false;
		bulletVelocity = new Vector2(0,0);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		
		checkForUserInteraction(delta);
		
		
		Batch batch = renderer.getBatch();
		batch.begin();
		
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isToBeRemoved()) {
				entities.remove(i);
				// add explosion animation
			} else {
				entities.get(i).draw(batch);
			}
		}
		
		
		batch.end();
		
		camera.update();
		renderer.setView(camera);
		renderer.render();
		
		stage.draw();
	}
	
	public void checkForUserInteraction(float delta) {
		// Check if movement buttons are pressed
		if (moveRightBtn.isPressed()) {
			player.getVelocity().x = player.getMovementSpeed();
			if (!player.isFacingRight()) player.flipSprite();
		} else if (moveLeftBtn.isPressed()) {
			player.getVelocity().x = -player.getMovementSpeed();
			if (player.isFacingRight()) player.flipSprite();
		} else {
			player.getVelocity().x = 0;
		}
		
		if (player.canJump() && jumpBtn.isPressed()) {
			player.getVelocity().y = player.getJumpSpeed();
			player.setCanJump(false);
		}
		
		if (fireBtn.isPressed()) {
			toggleFireMode(true);
		}
		
		if (abortFireBtn.isPressed()) {
			toggleFireMode(false);
		}
		
		if (fireMode && Gdx.input.isTouched() && !fireBtn.isPressed()) {
			aiming = true;
		}
		
		if (aiming) {
			if (Gdx.input.isTouched()) { // Still aiming
				aim();
			} else { // Release bullet
				fireBullet();
				aiming = false;
				toggleFireMode(false);
			}
		}
	}
	
	private void aim() {
		Vector3 touchPosYDown = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		Vector3 unprojected = camera.unproject(touchPosYDown);
		Vector2 touchPosition = new Vector2(unprojected.x, unprojected.y);
		Vector2 playerPosition = new Vector2(player.getX(), player.getY());
		
		bulletVelocity.x = -(touchPosition.x - playerPosition.x)*3;
		bulletVelocity.y = -(touchPosition.y - playerPosition.y)*3;
	}
	
	private void fireBullet() {
		if (System.currentTimeMillis() - lastFire >= 350) {
			float velocityX = 200;
			if (!player.isFacingRight()) {
				velocityX = -velocityX; 
			}
			System.out.println("X: " + bulletVelocity.x + ", Y: " + bulletVelocity.y);
			Bullet bullet = new Bullet(new Sprite(new Texture("bullet.png")), collisionLayer, new Vector2(player.getX(), player.getY()), bulletVelocity, true);
			addEntity(bullet);
			lastFire = System.currentTimeMillis();
		}
	}

	@Override
	public void resize(int width, int height) {
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
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	private void toggleFireMode(boolean value) {
		fireMode = value;
		fireBtn.setVisible(!value);
		abortFireBtn.setVisible(value);
	}

}
