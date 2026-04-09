package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

/**
 * Interface defining the contract for generating PlantUML syntax for connection
 * types.
 * 
 * @author boessu
 */
public interface ConnectionType {
	/**
	 * Generates the PlantUML syntax for the connection type with optional color and
	 * bold styling. This method is intended to be implemented by enums or classes
	 * that represent specific connection types.
	 *
	 * @param color The color to apply to the connection line. If null, the default
	 *              color (black) is used.
	 * @param bold  Indicates whether the connection line should be rendered in
	 *              bold. A bold line is typically represented with increased
	 *              thickness in PlantUML diagrams.
	 * @return The generated PlantUML syntax for the connection, including any
	 *         specified color and boldness.
	 */
	String getPlant(Color color, boolean bold);
}
