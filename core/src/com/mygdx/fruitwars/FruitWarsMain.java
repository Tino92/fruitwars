package com.mygdx.fruitwars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class FruitWarsMain extends Game implements InputProcessor {
	private static float ppt = 0;
	private Vector2 touchDown;
	private TiledMap map;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");
		Gdx.input.setInputProcessor(this);

		world = new World(new Vector2(0.0f, -0.5f), true);
		debugRenderer = new Box2DDebugRenderer();
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		Array<Body> bodies = buildShapes(map, 1, world);
	}

	@Override
	public void render() {
		float dt = Math.max(Gdx.graphics.getDeltaTime(), 0.25f);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		debugRenderer.render(world, camera.combined);
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

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.LEFT)
			camera.translate(-32, 0);
		if (keycode == Input.Keys.RIGHT)
			camera.translate(32, 0);
		if (keycode == Input.Keys.UP)
			camera.translate(0, -32);
		if (keycode == Input.Keys.DOWN)
			camera.translate(0, 32);
		if (keycode == Input.Keys.NUM_1)
			map.getLayers().get(0)
					.setVisible(!map.getLayers().get(0).isVisible());
		if (keycode == Input.Keys.NUM_2)
			map.getLayers().get(1)
					.setVisible(!map.getLayers().get(1).isVisible());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.touchDown = new Vector2(screenX, camera.viewportHeight-screenY);
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
