package com.mygdx.fruitwars;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.fruitwars.tokens.Ball;
import com.mygdx.fruitwars.tokens.Block;
import com.mygdx.fruitwars.tokens.SpriteFixture;

public class FruitWarsMain extends Game implements InputProcessor {
	private Texture img;
	private TiledMap map;
	private OrthographicCamera camera;
	private TiledMapRenderer renderer;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Vector2 touchDown;
	private Array<Body> bodies;
	private Array<SpriteFixture> spriteFixtures;
	private SpriteBatch sb;
	
	@Override
	public void create () {
		bodies = new Array<Body>();
		spriteFixtures = new Array<SpriteFixture>();
		sb = new SpriteBatch();
		float w, h;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		
		map = new TmxMapLoader().load("test.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		Gdx.input.setInputProcessor(this);
		
		world = new World(new Vector2(0.0f, -0.5f), true);
		debugRenderer = new Box2DDebugRenderer();
		int blockWidth = (int)camera.viewportWidth/10;
		for(int i = 0; i < 10; i++) {
			spriteFixtures.add(new Block(
					world,
					new Vector2(i*blockWidth, 10),
					new Vector2(blockWidth, 10)
					));
		}
	}
	
	
	public Fixture createBall(Vector2 pos) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pos);
		Body body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(6f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 10f;
		fixtureDef.friction = 0.1f;
		fixtureDef.restitution = 0.1f;
		Ball ball = new Ball();
		ball.setSize(circle.getRadius()*2, circle.getRadius()*2);
		body.setUserData(ball);
		return body.createFixture(fixtureDef);
	}
	
	public Fixture createGround() {
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, 10));
		Body groundBody = world.createBody(groundBodyDef);
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(camera.viewportWidth, 10.0f);
		return groundBody.createFixture(groundBox, 0.0f);
	}
	
	public Fixture createBlock(Vector2 pos, Vector2 dim) {
		BodyDef blockBodyDef = new BodyDef();
		blockBodyDef.position.set(pos);
		Body blockBody = world.createBody(blockBodyDef);
		PolygonShape block = new PolygonShape();
		block.setAsBox(dim.x, dim.y);
		return blockBody.createFixture(block, 0.0f);
	}


	@Override
	public void render () {
		float dt = Math.max(Gdx.graphics.getDeltaTime(), 0.25f);
        sb.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        /*for(Body b : bodies) {
        	if(b.getUserData()!=null) {
        		Ball ball = (Ball)b.getUserData();
        		ball.setPosition(b.getPosition().x-ball.getWidth()/2, b.getPosition().y-ball.getHeight()/2);
        		ball.draw(sb);
        	}
        }
        
        for(SpriteFixture sf : spriteFixtures) {
        		//sf.draw(sb);
        }
        sb.end();
		debugRenderer.render(world, camera.combined);
		world.step(dt, 6, 2);
		*/
	}
	

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,-32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,32);
        if(keycode == Input.Keys.NUM_1)
            map.getLayers().get(0).setVisible(!map.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            map.getLayers().get(1).setVisible(!map.getLayers().get(1).isVisible());
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.touchDown = new Vector2(screenX, camera.viewportHeight-screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(touchDown == null) {
			return false;
		}
		Vector2 velocity = new Vector2(screenX, camera.viewportHeight-screenY).sub(touchDown).rotate(180).scl(100);
		Fixture ball = createBall(touchDown);
		ball.getBody().applyForce(velocity, ball.getBody().getPosition(), true);
		touchDown = null;
		bodies.add(ball.getBody());
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		/*Fixture ball = createBall(new Vector2(screenX, camera.viewportHeight-screenY));
		bodies.add(ball.getBody());
		*/
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
