package com.mygdx.fruitwars.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.fruitwars.tokens.Crosshairs;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Projectile;

public class Collision implements ContactListener {
	
	private int damage;
	private int health;


	@Override
	public void beginContact(Contact contact) {
		Object collisionObjectA = contact.getFixtureA().getBody().getUserData();
		Object collisionObjectB = contact.getFixtureB().getBody().getUserData();
		
		/*
		 * Collision between minion and projectile: Update health
		*/
	
		if (collisionObjectA instanceof Minion  && collisionObjectB instanceof Projectile) {
			updateHealth(collisionObjectA, collisionObjectB);
		}
		
		else if (collisionObjectB instanceof Minion && collisionObjectA instanceof Projectile) {
			updateHealth(collisionObjectB, collisionObjectA);
		}

		/*
		 * Collision between two projectiles: Ignore
		 */
		if (collisionObjectA instanceof Projectile && collisionObjectB instanceof Projectile) {
			return;
		}		
		/*
		 * Collision between map and projectiles: Remove projectiles 
		 */
		else if (collisionObjectA instanceof Projectile) {
			Projectile current_projectile = (Projectile) collisionObjectA;
			current_projectile.setDestroy(true);
		}
		else if (collisionObjectB instanceof Projectile) {
			Projectile current_projectile = (Projectile) collisionObjectB;
			current_projectile.setDestroy(true);
		}
		
	}
	
	private void updateHealth(Object minion, Object projectile) {
		Minion current_minion = (Minion) minion;
		Projectile current_projectile = (Projectile) projectile;	
		damage = current_projectile.getDamage();
		health = current_minion.getHealth();
		int new_health = health - damage;
		
		current_minion.setHealth(new_health);	
		current_projectile.setDestroy(true);

	}

	@Override
	public void endContact(Contact contact) {
	
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	
}
