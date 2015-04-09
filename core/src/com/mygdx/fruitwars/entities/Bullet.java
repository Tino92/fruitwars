package com.mygdx.fruitwars.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {

	public Bullet(Sprite sprite, TiledMapTileLayer collisionLayer, Vector2 position, Vector2 velocity, boolean destroyOnCollision) {
		super(sprite, collisionLayer, position, velocity, destroyOnCollision);
	}

	@Override
	public void update(float delta) {
		updateEntityPosition(delta);
		setRotation(velocity.angle());
	}

}
