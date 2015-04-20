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
	public Minion(Body body) {
		super(new Texture("worm.png"), body);
	}
	
	public void move_left() {
		System.out.println("Moving left");
//		this.flip(true, false);
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
	
	public static Body createMinion(World world, Vector2 position,
			Vector2 dimension) {
		Body body;
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((dimension.x * 0.5f), (dimension.y * 0.5f));
		polygon.setAsBox(dimension.x * 0.5f, dimension.y * 0.5f, size, 0.0f);
		FixtureDef fd = new FixtureDef();
		fd.density = 10f;
		fd.restitution = 0.3f;
		fd.friction = 0.9f;
		fd.shape = polygon;
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(position);
		body = world.createBody(bd);
		body.createFixture(fd);
		body.setUserData(new Minion(body));
		fd.shape.dispose();
		return body;
	}
	
}
