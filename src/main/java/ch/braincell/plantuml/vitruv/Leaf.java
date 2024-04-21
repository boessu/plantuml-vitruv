package ch.braincell.plantuml.vitruv;

import java.net.URL;
import java.util.Set;

import ch.braincell.plantuml.vitruv.style.Color;

public class Leaf extends Block {

	public final LeafType leafType;
	private final Color color;
	private final Color highlightColor;

	Leaf(String name, String userID, LeafType leafType, URL url, Color color, Color highlightColor, Paragraph... documentations) {
		super(name, userID, url, documentations);
		this.leafType = leafType;
		this.color = color;
		this.highlightColor = highlightColor == null ? color : highlightColor;
	}

	@Override
	protected String getShort() {
		return "leaf";
	}

	@Override
	public String getPlant(RenderConfig config, Set<Block> focus) {
		boolean bold = focus.contains(this);
		String focuscolor = getColor(bold);

		String result = leafType.getPlant(name, ID, url, focuscolor, bold);

		if (config.leafDocumentation() && documentations.length > 0) {
			result += "note top of " + ID + "\n";
			for (Paragraph doc : documentations) {
				result += doc.getPlant();
			}
			result += "end note\n";
		}

		return result;
	}

	/**
	 * returns color of the leaf in the Hex String form #RRGGBB. Returns null if
	 * there is no color defined.
	 * 
	 * @param bold true for highlight color, false otherwise.
	 * @return color in the String form of #RRGGBB if available. If there is no color
	 *         defined, null will be returned.
	 */
	String getColor(boolean bold) {
		String resultColor = leafType.getColor();
		if (color != null)
			resultColor = bold ? highlightColor.toString() : color.toString();
		return resultColor;
	}

}
