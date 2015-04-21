package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Minion extends Token {
	public static final Vector2 dimension = new Vector2(32, 32);
	public static final float density = 10f;
	public static final float restitution = 0.1f;
	public static final float friction = 100.0f;
	private Body sensorBody;

	private boolean active = false;
	private ActiveArrow activeArrow = new ActiveArrow(this);

	public Minion(World world, Vector2 position, MinionCostume costume,
			int health) {
		super(new Texture(costume.toString()));
		Body body;
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((dimension.x * 0.5f), (dimension.y * 0.5f));
		polygon.setAsBox(dimension.x * 0.5f, dimension.y * 0.5f, size, 0.0f);
		FixtureDef fd = new FixtureDef();

		fd.density = density;
		fd.restitution = restitution;
		fd.friction = friction;
		fd.shape = polygon;

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(position);
		body = world.createBody(bd);
		body.setFixedRotation(true);
		body.createFixture(fd);
		body.setUserData(this);
		fd.shape.dispose();
		this.setBody(body);
		this.health = health;
		
		createSensorBody(world);
	}

	public void moveLeft() {
		this.body.applyForceToCenter(new Vector2(-100000f, 100000f), true);
	}

	public void moveRight() {
		this.body.applyForceToCenter(new Vector2(100000f, 100000f), true);
	}
	
	public void moveLeftInAir() {
		this.body.applyForceToCenter(new Vector2(-100000f, 0), true);
	}
	
	public void moveRightInAir() {
		this.body.applyForceToCenter(new Vector2(100000f, 0), true);
	}

	public void jump() {
		this.body.setLinearVelocity(new Vector2(0, 50f));
	}
	
	public void stopHorizontalMovement() {
		body.setLinearVelocity(0, body.getLinearVelocity().y);
	}
	
	private void createSensorBody(World world) {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(body.getPosition());
		sensorBody = world.createBody(bd);
		
		
		// create box shape for player foot
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(5, 3);
		
		// create fixturedef for player foot
		FixtureDef fd = new FixtureDef();
		fd.shape = polygon;
		fd.isSensor = true;
		
		// create player foot fixture
		sensorBody.createFixture(fd).setUserData("foot");
		polygon.dispose();
	}

	public void draw(Batch batch, Body body) {
		super.draw(batch, body);
		Vector2 bodyPos = body.getPosition();
		bodyPos.x += (dimension.x / 2);
		sensorBody.setTransform(bodyPos, 0);
		if (this.active) {
			activeArrow.draw(batch);
		}
	}

	class ActiveArrow extends Sprite {
		private Minion parent;

		public ActiveArrow(Minion parent) {
			super(new Texture("sprites/Arrow.png"));
			this.parent = parent;
			this.setSize(parent.getWidth(), parent.getHeight());
		}

		public void draw(Batch batch) {
			this.setPosition(parent.getBody().getPosition().x, parent.getBody()
					.getPosition().y + parent.getHeight());
			super.draw(batch);
		}
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
