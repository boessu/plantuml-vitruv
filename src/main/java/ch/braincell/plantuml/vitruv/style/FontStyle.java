package ch.braincell.plantuml.vitruv.style;

/**
 * Defines the font style for text elements in PlantUML, including the font
 * style and color. This record can be used to generate PlantUML CSS properties
 * for text styling.
 * 
 * @author boessu
 */
public record FontStyle(Style style, Color color) implements Style.SubStyleSteet {

	/**
	 * Constructs a {@code FontStyle} with the specified style and color. Defaults
	 * to {@code Style.NORMAL} and {@code Color.BLACK} if not specified.
	 *
	 * @param style The font style to apply.
	 * @param color The color of the font.
	 */
	public FontStyle(Style style, Color color) {
		this.style = style == null ? Style.NORMAL : style;
		this.color = color == null || color.equals(Color.BLACK) ? Color.BLACK : color;
	}

	/**
	 * Generates the PlantUML CSS representation of the font style. It includes the
	 * font color and style if they are not default values.
	 *
	 * @param define appends the string representing the PlantUML CSS properties for
	 *               the font style to the StringBuilder.
	 */
	@Override
	public void appendPlantSubCSS(StringBuilder define) {
		if (color != Color.BLACK)
			define.append("    FontColor ").append(color.toString()).append('\n');
		if (style != Style.NORMAL)
			define.append("    FontStyle ").append(style.toPlantCSS()).append('\n');
	}

	/**
	 * Enumerates the possible font styles for text in PlantUML.
	 */
	public enum Style {
		/** normal font style */
		NORMAL(""),
		/** bold font style */
		BOLD("bold"),
		/** italic font style */
		ITALIC("italic");

		private String plantCSS;

		/**
		 * Constructs a {@code Style} with the specified PlantUML CSS representation.
		 *
		 * @param plantCSS The PlantUML CSS representation of the font style.
		 */
		private Style(String plantCSS) {
			this.plantCSS = plantCSS;
		}

		/**
		 * Retrieves the PlantUML CSS representation of the font style.
		 *
		 * @return The PlantUML CSS representation of the font style.
		 */
		String toPlantCSS() {
			return plantCSS;
		}
	}
}
