package com.mygdx.fruitwars.screens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.Controller;
import com.mygdx.fruitwars.tokens.Minion;

public class GameScreen extends ScreenAdapter {
	private Array<Body> bodies;
	private static float ppt = 0;
	private TiledMap map;
	private SpriteBatch sb;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Box2DDebugRenderer box2DRenderer;
	private World world;
	private Game game;
	private Minion activeMinion;
	private Body activeBody;
	private Controller controller;
	private Stage stage;
	private TextButton moveLeftBtn;
	private TextButton moveRightBtn;
	private TextButton jumpBtn;
	private TextButton fireBtn;
	private TextButton abortFireBtn;
	
	public GameScreen(Game game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0.0f, -0.5f), true);
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		controller = new Controller(this);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		
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

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");

		box2DRenderer = new Box2DDebugRenderer();
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		buildShapes(map, 1, world);
		sb = new SpriteBatch();
		bodies = new Array<Body>();
		
		Body body;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		body = world.createBody(bd);
		activeMinion = new Minion(body);
		activeBody = Minion.createMinion(world, new Vector2(400, 200), new Vector2(32, 32));
		
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
		controller.render(dt);
		spriteRender(dt);
		box2DRender(dt);
		camera.update();
		world.step(dt, 6,  6);
		stage.draw();
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
	
	public TextButton getMoveLeftBtn() {
		return moveLeftBtn;
	}

	public TextButton getMoveRightBtn() {
		return moveRightBtn;
	}

	public TextButton getJumpBtn() {
		return jumpBtn;
	}


	public TextButton getFireBtn() {
		return fireBtn;
	}

	public TextButton getAbortFireBtn() {
		return abortFireBtn;
	}

	public Body getActiveBody() {
		return activeBody;
	}

	public Minion getActiveMinion() {
		return activeMinion;
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}

}
