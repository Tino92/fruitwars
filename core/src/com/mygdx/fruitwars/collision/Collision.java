package com.mygdx.fruitwars.collision;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.fruitwars.tokens.Minion;

public class Collision implements ContactListener {


	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getBody().getUserData();
		Object b = contact.getFixtureB().getBody().getUserData();
		
		if (a instanceof Minion && b instanceof Minion) {
			System.out.println("two minions");
			Minion minionA = (Minion) a;
			Minion minionB = (Minion) b;
			minionA.setHealth(0);
			minionB.setHealth(0);
			
		}
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
