package com.mygdx.fruitwars.tokens;

public enum WeaponCostume {
	UZI("weapons/Uzi.png"),
//	UZI("weapons/Uzi_Bullet.png"),
	BAZOOKA("weapons/Bazooka.png");
	
	private final String url;

	private WeaponCostume(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return url;
	}
}
