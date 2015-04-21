package com.mygdx.fruitwars.tokens;
public enum ProjectileCostume {
	GRENADE("projectiles/Grenade.png"),
	UZI("projectiles/Uzi_Bullet.png"),
	BAZOOKA("projectiles/Bazooka_Shell.png");
	
	private final String url;

	private ProjectileCostume(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return url;
	}
}

