package ch.braincell.plantuml.vitruv.style;

import ch.braincell.plantuml.vitruv.Block;

/**
 * Defines the contract for {@code ElementStyle} in a PlantUML diagram.
 * {@code ElementStyle}s are responsible for providing the visual representation
 * details such as PlantUML syntax, stereotypes, and colors for elements in the
 * diagram.
 */
public interface ElementStyle extends Style {

	/**
	 * Generates the PlantUML syntax for the leaf type with optional URL, color, and
	 * bold styling.
	 * 
	 * @param name  The name of the element.
	 * @param ID    The identifier for the element in PlantUML.
	 * @param link   The URL associated with the element (optional).
	 * @param color The color to apply to the element. If null, the default color is
	 *              used.
	 * @param bold  Whether the element should be bold.
	 * @param grouped these are the subelements of the element if the element will be used for grouping.
	 * @return The generated PlantUML syntax for the leaf type.
	 */
	String getPlant(String name, String ID, Block.Link link, String color, boolean bold, String grouped);

	/**
	 * Import for the specific leaf types.
	 * 
	 * @return import statement for plantUML (e.g.
	 *         {@code !include <archimate/Archimate>})
	 */
	String getImport();

	/**
	 * Gets the (additional) stereotype of the leaf as a string. This will be used
	 * in a consolidated view to show what element it is.
	 * 
	 * @return The stereotype name of the leaf.
	 */
	String getStereotype();

	/**
	 * Gets the filling color of the leaf
	 * 
	 * @return filling color of the leaf.
	 */
	String getColor();

}
