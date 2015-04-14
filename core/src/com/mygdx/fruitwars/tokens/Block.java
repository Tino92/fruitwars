package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Block extends SpriteFixture {
	public Block(World world, Vector2 pos, Vector2 dim) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(pos);
		Body body = world.createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dim.x, dim.y);
		this.fixture = body.createFixture(shape, 0.0f);
		
		this.sprite = new Sprite(new Texture("ball.png"));
		this.sprite.setSize(dim.x, dim.y);
	}
}
