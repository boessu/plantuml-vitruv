package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

/**
 * Defines the style of a connection in a PlantUML diagram.
 * 
 * @param type  The type of the connection.
 * @param color The color of the connection line.
 * @param bold  A boolean indicating if the connection line is bold.
 * 
 * @author boessu
 */
public record ConnectionStyle(ConnectionType type, Color color, boolean bold) {

	/**
	 * Constructs a new ConnectionStyle instance with default color and boldness.
	 * 
	 * @param type The type of the connection.
	 */
	public ConnectionStyle(ConnectionType type) {
		this(type, null, false);
	}

	/**
	 * Generates the PlantUML representation of the connection style.
	 * 
	 * @return The PlantUML string representation of the connection style.
	 */
	String getPlant() {
		return type.getPlant(color, bold);
	}
}
