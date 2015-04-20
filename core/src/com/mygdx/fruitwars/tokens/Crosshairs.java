package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Crosshairs extends Token {
	
	private boolean setToRemove;
	public static final Vector2 dimension = new Vector2(32, 32);

	public Crosshairs(World world, Vector2 position, SpriteCostume costume) {
		super(new Texture(costume.toString()));
		Body body;
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((dimension.x * 0.5f), (dimension.y * 0.5f));
		polygon.setAsBox(dimension.x * 0.5f, dimension.y * 0.5f, size, 0.0f);
		FixtureDef fd = new FixtureDef();
		
		fd.shape = polygon;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.StaticBody;
		bd.position.set(position);
		body = world.createBody(bd);
		body.createFixture(fd);
		body.setUserData(this);
		fd.shape.dispose();
		this.setBody(body);
	}

	public boolean isSetToRemove() {
		return setToRemove;
	}

	public void setSetToRemove(boolean setToRemove) {
		this.setToRemove = setToRemove;
	}
}
