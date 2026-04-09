package ch.braincell.plantuml.vitruv;

import java.util.Set;

import ch.braincell.plantuml.vitruv.style.Color;
import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * Represents a leaf node in a PlantUML diagram, which can have a type, color,
 * and documentation.
 * 
 * @author boessu
 */
public class Leaf extends Block {

	/** the type of the leaf */
	public final ElementStyle leafStyle;
	private final Color color;
	private final Color highlightColor;

	/**
	 * Constructs a new Leaf instance.
	 * 
	 * @param name           The name of the leaf.
	 * @param userID         The user ID associated with the leaf.
	 * @param leafStyle       The type of the leaf.
	 * @param url            The URL for documentation or reference.
	 * @param color          The primary color of the leaf.
	 * @param highlightColor The highlight color of the leaf; if null, the primary
	 *                       color is used.
	 * @param documentations Varargs parameter for documentation paragraphs.
	 */
	Leaf(String name, String userID, ElementStyle leafStyle, Link url, Color color, Color highlightColor,
			Paragraph... documentations) {
		super(name, userID, url, documentations);
		this.leafStyle = leafStyle;
		this.color = color;
		this.highlightColor = highlightColor == null ? color : highlightColor;
	}

	/**
	 * Provides a short string representation of the leaf.
	 * 
	 * @return A string that represents the leaf.
	 */
	@Override
	protected String getShort() {
		return "leaf";
	}

	/**
	 * Generates the PlantUML representation of the leaf.
	 * 
	 * @param config The rendering configuration.
	 * @param focus  The set of blocks that are in focus.
	 * @return The PlantUML string representation of the leaf.
	 */
	@Override
	public String getPlant(RenderConfig config, Set<Block> focus) {
		boolean bold = focus.contains(this);
		String focuscolor = getColor(bold);

		StringBuilder result = new StringBuilder(leafStyle.getPlant(name, ID, link, focuscolor, bold, null));

		if (config.leafDocumentation() && documentations.length > 0) {
			result.append("note top of ").append(ID).append("\n");
			for (Paragraph doc : documentations) {
				result.append(doc.getPlant());
			}
			result.append("end note\n");
		}

		return result.toString();
	}

	/**
	 * returns color of the leaf in the Hex String form #RRGGBB. Returns null if
	 * there is no color defined.
	 * 
	 * @param bold true for highlight color, false otherwise.
	 * @return color in the String form of #RRGGBB if available. If there is no
	 *         color defined, null will be returned.
	 */
	String getColor(boolean bold) {
		String resultColor = leafStyle.getColor();
		if (color != null)
			resultColor = bold ? highlightColor.toString() : color.toString();
		return resultColor;
	}

}
