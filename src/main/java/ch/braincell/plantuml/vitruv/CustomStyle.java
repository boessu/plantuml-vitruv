package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;
import ch.braincell.plantuml.vitruv.style.ElementStyle;
import ch.braincell.plantuml.vitruv.style.FontStyle;
import ch.braincell.plantuml.vitruv.style.FormStyle;
import ch.braincell.plantuml.vitruv.style.FormStyle.Style;
import ch.braincell.plantuml.vitruv.style.LineStyle;
import ch.braincell.plantuml.vitruv.style.Sprite;

/**
 * Represents the style of a group in PlantUML, defined by various style
 * components. This record encapsulates the style settings for a group element,
 * such as rectangles. It is also a coding example how a custom style can be programmed
 * if there is the need to do so.
 * 
 * @author boessu
 */
public record CustomStyle(String stereotype, FormStyle formStyle, LineStyle lineStyle, FontStyle fontStyle, Sprite sprite,
		Color backgroundColor, boolean shadow, int wordWrap) implements ElementStyle {

	/**
	 * A standard style which will be used if there is no style defined.
	 */
	public static final CustomStyle standardStyle = new CustomStyle();

	private CustomStyle() {
		// Standard style if no style is defined.
		this(null, new FormStyle(Style.RECTANGLE, 0), null, null, null, Color.WHITE, false, 0);
	}

	/**
	 * Generates the PlantUML CSS representation of the group style. It includes
	 * settings for shadowing, background color, font style, corner style, and line
	 * style.
	 * 
	 * @return A string representing the PlantUML CSS properties for the group
	 *         style.
	 */
	@Override
	public StyleSheet getPlantCSS() {
		if (stereotype == null)
			return null;
		StringBuilder define = new StringBuilder();
		if (shadow)
			define.append("    Shadowing ").append(1.5).append("\n");
		if (backgroundColor != null)
			define.append("    BackgroundColor ").append(backgroundColor).append('\n');
		if (fontStyle != null)
			fontStyle.appendPlantSubCSS(define);
		if (formStyle != null)
			formStyle.appendPlantSubCSS(define);
		if (lineStyle != null)
			lineStyle.appendPlantSubCSS(define);
		return new StyleSheet(formStyle.style().command, stereotype, define.toString());
	}

	@Override
	public String getPlant(String name, String ID, Block.Link link, String color, boolean bold, String grouped) {
		StringBuilder result = new StringBuilder();
		String fatLine = "";
		String boldName = "";
		if (bold) {
			boldName = "**";
			fatLine = color == null ? "#line.bold" : ";line.bold";
		}
		String fillStyle = color == null ? fatLine : color + fatLine;

		if (link != null && link.url() != null && !link.block()) {
			name = boldName + "[[" + link.url() + " " + name + "]]" + boldName;
		} else {
			name = boldName + name + boldName;
		}

		if (wordWrap > 0)
			name = StringUtil.wrap(name, wordWrap, "\\n");
		result.append(formStyle.getPlantCommand(name, ID, stereotype, fillStyle, sprite));
		
		if (grouped != null)
			result.append(" {\n").append(grouped).append("}\n");
		else
			result.append("\n");
		
		if (link != null && link.url() != null && link.block())
			result.append("url for ").append(ID).append(" is [[").append(link.url()).append("]]\n");
		return result.toString();
	}

	@Override
	public String getStereotype() {
		return stereotype;
	}

	@Override
	public String getColor() {
		return backgroundColor == null ? null : backgroundColor.toString();
	}

	@Override
	public String getImport() {
		return null;
	}

	@Override
	public String getSpriteHeader() {
		return sprite == null ? null : sprite.getPlantSprite();
	}
}
