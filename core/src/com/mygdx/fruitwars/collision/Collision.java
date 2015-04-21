package com.mygdx.fruitwars.collision;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.fruitwars.screens.GameScreen;
import com.mygdx.fruitwars.tokens.Minion;
import com.mygdx.fruitwars.tokens.Projectile;
import com.mygdx.fruitwars.utils.Constants;

public class Collision implements ContactListener {
	
	private int damage;
	private int health;
	private boolean playerOnGround = false;
	
	private GameScreen gameScreen;
	
	public Collision(GameScreen gameScreen){
		this.gameScreen = gameScreen;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Object collisionObjectA = fixtureA.getBody().getUserData();
		Object collisionObjectB = fixtureB.getBody().getUserData();
		
		/*
		 * Collision between minion and projectile: Update health
		*/
	
		if (collisionObjectA instanceof Minion  && collisionObjectB instanceof Projectile) {
			updateHealth(collisionObjectA, collisionObjectB);
			//End the turn after projectile hits some target
			gameScreen.setTurnTimeLeft(Constants.COLLISION_TIMER);
		}
		
		else if (collisionObjectB instanceof Minion && collisionObjectA instanceof Projectile) {
			updateHealth(collisionObjectB, collisionObjectA);
			//End the turn after projectile hits some target
			gameScreen.setTurnTimeLeft(Constants.COLLISION_TIMER);
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
			//End the turn after projectile hits some target
			gameScreen.setTurnTimeLeft(Constants.COLLISION_TIMER);
		}
		else if (collisionObjectB instanceof Projectile) {
			Projectile current_projectile = (Projectile) collisionObjectB;
			current_projectile.setDestroy(true);
			//End the turn after projectile hits some target
			gameScreen.setTurnTimeLeft(Constants.COLLISION_TIMER);
		}
		
		if ((fixtureA.getUserData() != null && fixtureA.getUserData().equals("activeFoot")) 
				|| (fixtureB.getUserData() != null && fixtureB.getUserData().equals("activeFoot"))) {
			playerOnGround = true;
			System.out.println("Player on ground");
		}
		
	}
	
	private void updateHealth(Object minion, Object projectile) {
		Minion current_minion = (Minion) minion;
		Projectile current_projectile = (Projectile) projectile;	
		damage = current_projectile.getDamage();
		health = current_minion.getHealth();
		int new_health = health - damage;
		
		//Checking code to avoid suicide
		if (current_minion != gameScreen.getCurrentPlayer().getActiveMinion())
			current_minion.setHealth(new_health);	
		current_projectile.setDestroy(true);

	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		if ((fixtureA.getUserData() != null && fixtureA.getUserData().equals("activeFoot"))
				|| (fixtureB.getUserData() != null && fixtureB.getUserData().equals("activeFoot"))) {
			playerOnGround = false;
			System.out.println("Player no longer on ground");
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	public boolean isPlayerOnGround() {
		return playerOnGround;
	}

	
}
