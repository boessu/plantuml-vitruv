package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;
import ch.braincell.plantuml.vitruv.style.ElementStyle;
import ch.braincell.plantuml.vitruv.style.FontStyle;
import ch.braincell.plantuml.vitruv.style.FormStyle;
import ch.braincell.plantuml.vitruv.style.FormStyle.Style;
import ch.braincell.plantuml.vitruv.style.LineStyle;

/**
 * Represents the style of a group in PlantUML, defined by various style
 * components. This record encapsulates the style settings for a group element,
 * such as rectangles.
 */
public record CustomStyle(String stereotype, FormStyle formStyle, LineStyle lineStyle, FontStyle fontStyle,
		Color backgroundColor, boolean shadow, int wordWrap) implements ElementStyle {

	public static final CustomStyle standardStyle = new CustomStyle();

	private CustomStyle() {
		// Standard style if no style is defined.
		this(null, new FormStyle(Style.RECTANGLE, 0), null, null, Color.WHITE, false, 0);
	}
	
	/**
	 * Generates the PlantUML CSS representation of the group style. It includes
	 * settings for shadowing, background color, font style, corner style, and line
	 * style.
	 * 
	 * @return A string representing the PlantUML CSS properties for the group
	 *         style.
	 */
	public StyleSheet getPlantCSS() {
		if (stereotype == null)
			return null;
		StringBuilder define = new StringBuilder();
		if (shadow) define.append("    Shadowing ").append(1.5).append("\n");
		if (backgroundColor != null) define.append("    BackgroundColor ").append(backgroundColor).append('\n');
		if (fontStyle != null) fontStyle.appendPlantSubCSS(define);
		if (formStyle != null) formStyle.appendPlantSubCSS(define);
		if (lineStyle != null) lineStyle.appendPlantSubCSS(define);
		return new StyleSheet(formStyle.style().command, stereotype, define.toString());
	}

	@Override
	public String getPlant(String name, String ID, Block.Link link, String color, boolean bold, String grouped) {
		StringBuilder result = new StringBuilder();
		String fatLine = "";
		if (bold) {
			name = "**" + name + "**";
			fatLine = color == null ? "#line.bold" : ";line.bold";
		}
		String fillStyle = color == null ? fatLine : color + fatLine;

		if (wordWrap > 0)
			name = StringUtil.wrap(name, wordWrap, "\\n");
		if (link == null || link.block())
			result.append(formStyle.getPlantCommand(name, ID, stereotype, fillStyle));
		else if (link != null)
			result.append(formStyle.getPlantCommand("[[" + link.url() + " " + name + "]]", ID, stereotype, fillStyle));
		if (grouped != null)
			result.append(" {\n").append(grouped).append("}\n");
		else
			result.append("\n");
		if (link != null && link.block())
			result.append("url for ").append(ID).append(" is [[").append(link.url()).append("]]\n");
		return result.toString();
	}

	@Override
	public String getStereotype() {
		return stereotype;
	}

	@Override
	public String getColor() {
		return backgroundColor.toString();
	}

	@Override
	public String getImport() {
		return null;
	}
}
