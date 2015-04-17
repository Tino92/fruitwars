package com.mygdx.fruitwars.screens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

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
import com.mygdx.fruitwars.Collision;
import com.mygdx.fruitwars.Controller;
import com.mygdx.fruitwars.FruitWarsMain;
import com.mygdx.fruitwars.Player;
import com.mygdx.fruitwars.UserInterface;
import com.mygdx.fruitwars.modes.Default;
import com.mygdx.fruitwars.modes.GameMode;
import com.mygdx.fruitwars.tokens.Bullet;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.utils.Constants;

public class GameScreen implements Screen{
	
	final FruitWarsMain game;
	public OrthographicCamera camera;
	public InputMultiplexer inputMultiplexer;
	
	
	private Array<Player> players;
	private int currentPlayer = Constants.PLAYER1-1;
	private Array<Bullet> bullets;
	private Collision collision;
	private Controller controller;
	private int turnTimeLeft;
	private UserInterface userInterface;
	private GameMode gameMode;
	
	private Array<Body> bodies;
	private static float ppt = 0;
	private TiledMap map;
	private SpriteBatch spriteBatch;
	private OrthogonalTiledMapRenderer mapRenderer;
	private Box2DDebugRenderer box2DRenderer;
	private World world;
	
	
	
	public GameScreen(final FruitWarsMain game) {
		this.game = game;
		
		players = new Array<Player>();
		Array<Minion> minions_p1 = new Array<Minion>();
		Array<Minion> minions_p2 = new Array<Minion>();
		for (int i=0; i< Constants.NUM_MINIONS; i++){
			//minions_p1.add(new AppleMinion());
			//minions_p2.add(new BananaMinion());
			
		}
		
		players.add(new Player(Constants.PLAYER1,minions_p1));
		players.add(new Player(Constants.PLAYER2,minions_p2));
		
		gameMode = new Default(this);
		turnTimeLeft = gameMode.getTurnTime();
		
	}
	
	@Override
	public void show() {
		world = new World(new Vector2(0.0f, -0.5f), true);
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");
		
		// References to the controller
		userInterface = new UserInterface(this);
		controller = new Controller(this);
		inputMultiplexer = new InputMultiplexer(controller);
		Gdx.input.setInputProcessor(inputMultiplexer);

		box2DRenderer = new Box2DDebugRenderer();
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		buildShapes(map, 1, world);
		spriteBatch = new SpriteBatch();
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
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		world.getBodies(bodies);
		Box2DSprite sprite;
		for(Body b : bodies) {
			if(b.getUserData()==null) {
				continue;
			}
			
			sprite = (Box2DSprite)b.getUserData();
			sprite.draw(spriteBatch, b);
		}
		spriteBatch.end();
	}
	
	private void mapRender(float dt) {
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	@Override
	public void render(float dt) {
		
		
		//collision.collisionCheck();
		
		clearScreen();
		mapRender(dt);
		spriteRender(dt);
		box2DRender(dt);
		camera.update();
		world.step(dt, 6,  6);
		
		userInterface.draw(spriteBatch);
		
		//Check if game is finished
		if (gameMode.gameFinished())
			game.setScreen(new GameOverScreen(game,players.get(Constants.PLAYER1).getScore(),players.get(Constants.PLAYER2).getScore()));
		
		//Decrease turn time
		turnTimeLeft-=1;
		if (turnTimeLeft==0){
			turnTimeLeft = gameMode.getTurnTime();
			//Next player
			currentPlayer+=1;
			//Reset weapon
			players.get((currentPlayer) % Constants.NUM_PLAYERS).weaponFired=false;
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
	
	public Array<Player> getPlayers(){
		return players;
	}


}
