package ch.braincell.plantuml.vitruv.style;

public record Sprite(String name, String svg) {
	public String getPlantSprite() {
		return "sprite $" + name + " " + svg;
	}
}
