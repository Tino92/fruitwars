package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Projectile extends Weapon {
	public static final float density = 10f;
	public static final float restitution = 0.3f;
	public static final float friction = 0.9f;

	public Projectile(World world, Vector2 position,
			Vector2 dimension, Vector2 velocity) {
		super(new Texture("buttet.png"));
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
		bd.bullet = true; // so CCD is performed between dynamic bodies
		bd.type = BodyType.DynamicBody;
		bd.position.set(position);
		body = world.createBody(bd);
		body.createFixture(fd);
		body.setUserData(this);
		this.setBody(body);
		fd.shape.dispose();
	}
	
}
