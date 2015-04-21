package com.mygdx.fruitwars.tokens;

public enum MinionCostume {
	APPLE("sprites/Apple.png"), BANANA("sprites/Banana.png"), PEAR(
			"sprites/Pear.png"), PINEAPPLE("sprites/Pinepple.png"), ORANGE(
			"sprites/Orange.png");

	private final String url;

	private MinionCostume(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return url;
	}
}
