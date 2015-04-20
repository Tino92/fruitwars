package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;
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
	public static final float friction = 10.0f;

	public Minion(World world, Vector2 position, SpriteCostume costume) {
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
	}

	public void move_left() {
		System.out.println("Moving left");
		if (!this.body.isAwake()) {
			this.body.setLinearVelocity(new Vector2(-100f, 50f));
		}

	}

	public void move_right() {
		System.out.println("Moving right");
		if (!this.body.isAwake()) {
			this.body.setLinearVelocity(new Vector2(100f, 50f));
		}
	}

}

