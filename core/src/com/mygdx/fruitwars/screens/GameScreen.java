package com.mygdx.fruitwars.screens;

import java.util.Iterator;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.collision.Collision;
import com.mygdx.fruitwars.tokens.Minion;

public class GameScreen extends ScreenAdapter implements InputProcessor {
	private Array<Body> bodies;
	private static float ppt = 0;
	private Vector2 touchDown;
	private TiledMap map;
	private SpriteBatch sb;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Box2DDebugRenderer box2DRenderer;
	private World world;
	private Game game;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0.0f, -0.5f), true);
		world.setContactListener(new Collision());
		
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");
		Gdx.input.setInputProcessor(this);

		box2DRenderer = new Box2DDebugRenderer();
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		buildShapes(map, 1, world);
		sb = new SpriteBatch();
		bodies = new Array<Body>();
	}
	
	private void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	private void box2DRender(float dt) {
		box2DRenderer.render(world, camera.combined);
	}
	
	private void spriteRender(float dt) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		world.getBodies(bodies);
		Box2DSprite sprite;
		for(Body b : bodies) {
			if(b.getUserData()==null) {
				continue;
			}
			
			sprite = (Box2DSprite)b.getUserData();
			sprite.draw(sb, b);
		}
		sb.end();
	}
	
	private void mapRender(float dt) {
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	@Override
	public void render(float dt) {
		dt = Math.max(dt, 0.25f);
		clearScreen();
		mapRender(dt);
		spriteRender(dt);
		box2DRender(dt);
		camera.update();
		removeDeadBodies();
		world.step(dt, 6,  6);	
	}
	
	public void removeDeadBodies() {		
		world.getBodies(bodies);	
		for (Body b : bodies) {
			if(b.getUserData()==null) {
				continue;
			}		
			Minion data = (Minion) b.getUserData();
			
			if (data.getHealth() == 0) {
				world.destroyBody(b);
			}
		}
	}
		   
	public void buildShapes(TiledMap map, float pixels,
			World world) {
		ppt = pixels;
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		Array<Body> bodies = new Array<Body>();
		
		float cellWidth, cellHeight;

		for (int x = 0; x < layer.getWidth(); x++) {
			for (int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if(cell==null) {
					continue;
				}
				cellWidth = cell.getTile().getTextureRegion().getRegionWidth();
				cellHeight = cell.getTile().getTextureRegion().getRegionHeight();
				Shape shape;
				
				PolygonShape polygon = new PolygonShape();
		        Vector2 size = new Vector2((x*cellWidth + cellWidth* 0.5f) / ppt,
		                                   (y*cellHeight + cellHeight * 0.5f ) / ppt);
		        polygon.setAsBox(cellWidth * 0.5f / ppt,
		                         cellHeight * 0.5f / ppt,
		                         size,
		                         0.0f);
		        shape = polygon;
				BodyDef bd = new BodyDef();
				bd.type = BodyType.StaticBody;
				Body body = world.createBody(bd);
				body.createFixture(shape, 1);

				bodies.add(body);

				shape.dispose();
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.touchDown = new Vector2(screenX, camera.viewportHeight-screenY);
		Minion.createMinion(world, touchDown, new Vector2(32, 32));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchDown = null; 
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
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
