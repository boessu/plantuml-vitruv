package ch.braincell.plantuml.vitruv;

import java.util.Arrays;

import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * Create a Render Configuration to display Archimate diagrams.
 * 
 * @param canvasShadows         true draws PlantUML shadows behind graphical
 *                              elements.
 * @param canvasLeftToRight     true draws the graph from left to right.
 * @param connectionLabel       true draws the label on the connection.
 * @param connectionTitle       true draws the title to the connection.
 * @param connectionDescription true draws the description to the connection.
 * @param showGroups            true groups the labels on the drawing.
 * @param showConsolidated      true draws a consolidated view where the labels
 *                              are a for of a list in a group. Only active if
 *                              showGroups is also true.
 * @param groupDocumentation    true draws the documentation to the group.
 * @param leafDocumentation     true draws the documentation to the leaf.
 * @param showLeafs             list of leaf types which will be drawn. If
 *                              there are no types configured, all leafs will be
 *                              drawn.
 * 
 * @author boessu
 */
public record RenderConfig(boolean canvasShadows, boolean canvasLeftToRight, boolean connectionLabel,
		boolean connectionTitle, boolean connectionDescription, boolean showGroups, boolean showConsolidated,
		boolean groupDocumentation, boolean leafDocumentation, ElementStyle... showLeafs) {
	/**
	 * returns true if block should be visible
	 * 
	 * @param block block to render
	 * @return true if the block should be visible, false otherwise.
	 */
	public boolean show(Block block) {
		if (block instanceof Leaf leaf) {
			return showLeafs.length == 0 || Arrays.stream(showLeafs).anyMatch(lt -> lt == leaf.leafStyle);
		}
		return true;
	}
}
