package com.mygdx.fruitwars.screens;

import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.Controller;
import com.mygdx.fruitwars.FruitWarsMain;
import com.mygdx.fruitwars.Player;
import com.mygdx.fruitwars.UserInterface;
import com.mygdx.fruitwars.collision.Collision;
import com.mygdx.fruitwars.modes.Default;
import com.mygdx.fruitwars.modes.GameMode;
import com.mygdx.fruitwars.modes.HighPace;
import com.mygdx.fruitwars.modes.Juggernaut;
import com.mygdx.fruitwars.modes.OneShot;
import com.mygdx.fruitwars.tokens.Bullet;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Projectile;
import com.mygdx.fruitwars.tokens.SpriteCostume;
import com.mygdx.fruitwars.utils.Constants;

public class GameScreen implements Screen{
	
	public static final String TAG = "GameScreen";
	
	final FruitWarsMain game;
	public OrthographicCamera camera;
	public InputMultiplexer inputMultiplexer;
	public boolean paused = false;
	
	private Array<Player> players;
	private int currentPlayer = Constants.PLAYER1;
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
	private Minion activeMinion;
	private Body activeBody;
	private Stage stage;
	

	
	public GameScreen(FruitWarsMain game) {
		this.game = game;
		
		world = new World(new Vector2(0.0f, -0.5f), true);
		world.setContactListener(new Collision());
		
		players = new Array<Player>();
		Array<Minion> minions_p1 = new Array<Minion>();
		Array<Minion> minions_p2 = new Array<Minion>();
		
		
		players.add(new Player(Constants.PLAYER1,minions_p1));
		players.add(new Player(Constants.PLAYER2,minions_p2));
		
		switch(game.gameMode){
			case Constants.HIGH_PACE:
				gameMode = new HighPace(this);
				break;
			case Constants.JUGGERNAUT:
				gameMode = new Juggernaut(this);
				break;
			case Constants.ONESHOT:
				gameMode = new OneShot(this);
				break;
			default:
				gameMode = new Default(this);
		}
		
		for (int i=0; i< Constants.NUM_MINIONS; i++){
			minions_p1.add(new Minion(world,new Vector2(400+i*10,400),SpriteCostume.APPLE, gameMode.getMinionsHealth()));
			minions_p2.add(new Minion(world,new Vector2(400+i*10,400),SpriteCostume.BANANA, gameMode.getMinionsHealth()));
			
		}
		turnTimeLeft = gameMode.getTurnTime();
		bullets = new Array<Bullet>();
		
	}
	
	@Override
	public void show() {
		
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		controller = new Controller(this);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 
				Constants.TILE_SIZE*Constants.TILES_IN_ROW,
				(h/w)*Constants.TILE_SIZE*Constants.TILES_IN_ROW);
		camera.update();

		map = new TmxMapLoader().load("maps/map.tmx");

		
		// References to the controllers
		userInterface = new UserInterface(this);
		controller = new Controller(this);
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(0, userInterface.getStage());
		inputMultiplexer.addProcessor(1, controller);
		Gdx.input.setInputProcessor(inputMultiplexer);

		box2DRenderer = new Box2DDebugRenderer();
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		buildShapes(map, 1, world);
		spriteBatch = new SpriteBatch();
		bodies = new Array<Body>();
		
		//activeMinion = new Minion(world, new Vector2(400, 200), SpriteCostume.APPLE, gameMode.getMinionsHealth());
		//activeBody = activeMinion.getBody();
		
//		Body bulletBody = Bullet.createBullet(world, new Vector2(600,200), new Vector2(22,12));
		
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
		dt = Math.max(dt, 0.25f);
		
		if (!paused){
			//collision.collisionCheck();
		
			clearScreen();
			mapRender(dt);
			spriteRender(dt);
			//box2DRender(dt);
			camera.update();
			world.step(dt, 6,  6);
			
			//Check if game is finished
			if (gameMode.gameFinished())
				game.setScreen(new GameOverScreen(game,players.get(Constants.PLAYER1).getScore(),players.get(Constants.PLAYER2).getScore()));
			
			//Decrease turn time
			turnTimeLeft-=1;
			if (turnTimeLeft==0 || players.get(currentPlayer).weaponFired){
				turnTimeLeft = gameMode.getTurnTime();
				//Next player
				currentPlayer=(currentPlayer+1) % (Constants.NUM_PLAYERS);
				//Select next minion
				players.get(currentPlayer).nextMinion();
				//Gdx.app.debug(TAG, "Turn is over currentPlayer is: " + currentPlayer);
				System.out.println("Turn is over nextPlayer is: " + players.get(currentPlayer).getPlayerNumber() + 
						" current minion is: " + players.get(currentPlayer).activeMinion);
				//Reset player weapon
				players.get(currentPlayer).weaponFired=false;
				
				// Pause the game to give the other player time
				userInterface.showPauseTitle("Player " + players.get(currentPlayer).getPlayerNumber() + " is next!");
				paused = true;
			}
		}
		userInterface.draw();
			
	}
	
	public void removeDeadBodies() {		
		world.getBodies(bodies);	
		for (Body b : bodies) {
			if(b.getUserData()==null) {
				continue;
			}		
			
			if (b.getUserData() instanceof Minion) {
				Minion data = (Minion) b.getUserData();
				if (data.getHealth() == 0) {
					world.destroyBody(b);
				}
			}
			if (b.getUserData() instanceof Projectile) {
				Projectile data = (Projectile) b.getUserData();
				if (data.getDestroy()) {
					world.destroyBody(b);
				}
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
	
/*	public boolean fireBullet(int screenX, int screenY) {
		
		if (System.currentTimeMillis() - lastFire >= 350) {
					
			lastFire = System.currentTimeMillis();
		
			Vector2 bulletVelocity = new Vector2(500, 100);
		
			Projectile current_projectile = new Projectile(world, new Vector2(screenX, camera.viewportHeight-screenY), new Vector2(22, 12), bulletVelocity);
		
			Body bodyProjectile = current_projectile.getBody();
				
			bodyProjectile.applyLinearImpulse(bulletVelocity.x, bulletVelocity.y, screenX, camera.viewportHeight-screenY, true);
			
		//	bodyProjectile.applyForceToCenter(bulletVelocity.x, bulletVelocity.y, true);
			
			return true;
		}
		return false;
	}
	*/


	//Not tested but should convert screen coordinates to world coordinates
	public Vector2 getWorldCoordinates(float screenX, float screenY) {
		Vector3 vec = new Vector3(screenX, camera.viewportHeight-screenY, 0.f);
		vec = camera.unproject(vec);
		return new Vector2(vec.x, vec.y);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		userInterface.showPauseTitle("Game paused!");
		paused = true;
		
	}

	@Override
	public void resume() {
		userInterface.hidePauseTitle();
		paused = false;
		
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
	
	public Player getCurrentPlayer(){
		return players.get(currentPlayer);
	}

	public int getTimeLeft(){
		return turnTimeLeft;
	}
	
	public boolean isPaused(){
		return paused;
	}

}
