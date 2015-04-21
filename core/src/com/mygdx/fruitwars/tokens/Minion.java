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
	}

	public void move_left() {
		System.out.println("Moving left");
		active = false;
		Vector2 pos = body.getPosition();
		this.body.applyLinearImpulse(
				new Vector2(-5 * body.getMass(), body.getMass()), pos, true);
		// }

	}

	public void move_right() {
		System.out.println("Moving right");
		active = false;
		Vector2 pos = body.getPosition();
		this.body.applyLinearImpulse(
				new Vector2(5 * body.getMass(), body.getMass()), pos, true);
	}

	public void jump() {
		System.out.println("Jumping");
		active = false;
		if (!this.body.isAwake()) {
			this.body.setLinearVelocity(new Vector2(0f, 50f));
		}
	}

	public void draw(Batch batch, Body body) {
		super.draw(batch, body);
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
