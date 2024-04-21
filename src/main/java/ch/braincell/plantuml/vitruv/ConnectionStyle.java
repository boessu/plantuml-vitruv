package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

public record ConnectionStyle(ConnectionType type, Color color, boolean bold) {

	public ConnectionStyle(ConnectionType type) {
		this(type, null, false);
	}
	
	String getPlant() {
		return type.getPlant(color, bold);
	}
}
