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

public class Weapon extends Sprite {
	
	private Body body;
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 dimension;
	
	public Weapon(World world, Vector2 position, Vector2 velocity, Texture texture) {
		super(texture);
		this.position = position;
		this.velocity = velocity;
		dimension = new Vector2(texture.getWidth(), texture.getHeight());
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(position);
		body = world.createBody(bd);
		body.applyForceToCenter(velocity.x, velocity.y, true);
		
	}
	
	public void draw(Batch sb) {
		this.setPosition(body.getPosition().x, body.getPosition().y);
		this.setRotation(body.getLinearVelocity().angle());
		super.draw(sb);
	}
	
	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		body.setTransform(position, 0.0f);
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}