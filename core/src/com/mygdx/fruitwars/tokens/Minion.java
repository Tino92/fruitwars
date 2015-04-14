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
	public Minion(World world, Vector2 position, Vector2 dimension) {
		super(new Texture("worm.png"));
		
		
		PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((position.x + dimension.x* 0.5f),
                                   (position.y + dimension.y* 0.5f ));
        polygon.setAsBox(dimension.x* 0.5f,
                         dimension.y* 0.5f,
                         size,
                         0.0f);
        FixtureDef fd = new FixtureDef();
        fd.density = 10f;
        fd.restitution = 0.3f;
        fd.friction = 0.9f;
        fd.shape = polygon;
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		
		body = world.createBody(bd);
		body.createFixture(fd);

		fd.shape.dispose();
	}
	
	public void draw(Batch sb) {
		this.setPosition(body.getPosition().x, body.getPosition().y);
		super.draw(sb);
	}
}
