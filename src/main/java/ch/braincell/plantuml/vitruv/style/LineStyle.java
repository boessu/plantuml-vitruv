package ch.braincell.plantuml.vitruv.style;

/**
 * Defines the style of a line with a specific style, color, and thickness. This
 * record can be used to generate PlantUML CSS properties for lines.
 * 
 * @author boessu
 */
public record LineStyle(Style style, Color color, int thickness) implements Style.SubStyleSteet {

	/**
	 * Constructs a {@code LineStyle} with the specified style, color, and
	 * thickness. If the style is not specified, it defaults to
	 * {@code Style.NORMAL}. If the color is not specified or is black, it defaults
	 * to {@code Color.BLACK}.
	 * 
	 * @param style     The style of the line, which can be normal, dashed, or
	 *                  dotted.
	 * @param color     The color of the line.
	 * @param thickness The thickness of the line.
	 */
	public LineStyle(Style style, Color color, int thickness) {
		this.style = style == null ? Style.NORMAL : style;
		this.color = color == null || color.equals(Color.BLACK) ? Color.BLACK : color;
		this.thickness = thickness;
	}

	/**
	 * Generates the PlantUML CSS representation of the line style. It includes the
	 * border style, thickness, and color if they are not default values.
	 * 
	 * @param define Appends a string representing the PlantUML CSS properties for
	 *               the line style.
	 */
	@Override
	public void appendPlantSubCSS(StringBuilder define) {
		if (style != Style.NORMAL)
			define.append("    LineStyle ").append(style.toPlantCSS()).append('\n');
		if (thickness > 1)
			define.append("    LineThickness ").append(thickness).append('\n');
		if (color != Color.BLACK)
			define.append("    LineColor ").append(color).append('\n');
	}

	/**
	 * Enumerates the possible styles for lines in PlantUML.
	 */
	public enum Style {
		/** normal lines */
		NORMAL(""),
		/** line is dashed */
		DASHED("5-5"),
		/** line is dotted */
		DOTTED("2-2");

		private String plantCSS;

		/**
		 * Constructs a {@code Style} with the specified PlantUML CSS representation.
		 * 
		 * @param plantCSS The PlantUML CSS representation of the style.
		 */
		private Style(String plantCSS) {
			this.plantCSS = plantCSS;
		}

		/**
		 * Retrieves the PlantUML CSS representation of the style.
		 * 
		 * @return The PlantUML CSS representation of the style.
		 */
		String toPlantCSS() {
			return plantCSS;
		}
	}
}