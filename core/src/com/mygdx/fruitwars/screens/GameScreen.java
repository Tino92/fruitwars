package com.mygdx.fruitwars.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import com.mygdx.fruitwars.Controller;
import com.mygdx.fruitwars.FruitWarsMain;
import com.mygdx.fruitwars.Player;
import com.mygdx.fruitwars.tokens.Bullet;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Weapon;

public class GameScreen implements Screen{
	
	final FruitWarsMain game;
	public OrthographicCamera camera;
	public TiledMap map;
	public InputMultiplexer inputMultiplexer;
	
	private static float ppt = 0;
	private Array<Player> players;
	private Player currentPlayer;
	private Array<Bullet> bullets;

	private SpriteBatch spriteBatch;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Weapon weapon;
	private Controller controller;
	private int turnTimeleft;
	

	public GameScreen(final FruitWarsMain game) {
		
		this.game = game;
		
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");
		controller = new Controller(this);
		inputMultiplexer = new InputMultiplexer(controller);
		Gdx.input.setInputProcessor(controller);

		world = new World(new Vector2(0.0f, -0.5f), true);
		debugRenderer = new Box2DDebugRenderer();
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		Array<Body> bodies = buildShapes(map, 1, world);
		
		spriteBatch = new SpriteBatch();
		
		

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float dt = Math.max(Gdx.graphics.getDeltaTime(), 0.25f);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		debugRenderer.render(world, camera.combined);
		spriteBatch.begin();
		for(Minion m : currentPlayer.getMinions()) {
			m.draw(spriteBatch);
		}
		if (weapon != null) {
			weapon.draw(spriteBatch);
		}
		
		spriteBatch.end();
		world.step(dt, 6,  6);
		
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
	
	public Array<Body> buildShapes(TiledMap map, float pixels,
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
		return bodies;
	}

}
