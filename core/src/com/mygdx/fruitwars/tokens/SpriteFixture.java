package com.mygdx.fruitwars.tokens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

public class SpriteFixture {
	protected Fixture fixture; 
	protected Sprite sprite;
	
	public void draw(SpriteBatch sb) {
		Vector2 pos = fixture.getBody().getPosition();
		sprite.setPosition(pos.x, pos.y-sprite.getHeight()/2);
		//sprite.rotate((float) Math.toDegrees(fixture.getBody().getAngle()));
		sprite.draw(sb);
	}
}
