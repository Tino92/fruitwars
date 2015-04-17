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

public class Minion extends Sprite {
	
	private Body body;
	private Vector2 position;
	
	private int health ;
	private boolean alive;
	
	
	public Minion(World world, Vector2 position, Vector2 dimension) {
		super(new Texture("sprites/worm.png"));
		
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.position.set(position);
		body = world.createBody(bd);
		
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		shape.setAsBox(dimension.x* 0.5f, dimension.y* 0.5f);
		fdef.shape = shape;
		body.createFixture(fdef);
	}
	
	public void draw(Batch sb) {
		this.setPosition(body.getPosition().x, body.getPosition().y);
		super.draw(sb);
		
	}
}