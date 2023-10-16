package ch.braincell.plantuml.archimate;

import ch.braincell.plantuml.archimate.style.Color;

public record ConnectionStyle(ConnectionType type, Color color, boolean bold) {

	public ConnectionStyle(ConnectionType type) {
		this(type, null, false);
	}
	
	String getPlant() {
		return type.getPlant(color, bold);
	}
}
