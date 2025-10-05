package ch.braincell.plantuml.vitruv.style;

/**
 * Defines the corner style for elements in PlantUML, including the corner type
 * and size. This record can be used to generate PlantUML CSS properties for
 * corner styling.
 */
public record FormStyle(Style style, int size) implements Style.SubStyleSteet {

	/**
	 * Constructs a {@code CornerStyle} with the specified style and size. Defaults
	 * to {@code Style.NORMAL} if the style is not specified.
	 *
	 * @param style The corner style to apply.
	 * @param size  The size of the corner in case it is {@link Style#ROUNDEDCORNER}
	 *              or {@link Style#DIAGONALCORNER}.
	 */
	public FormStyle(Style style, int size) {
		this.style = style == null ? Style.RECTANGLE : style;
		this.size = size;
	}

	/**
	 * Generates the PlantUML CSS representation of the corner style. It includes
	 * the corner type and size if they are not default values.
	 *
	 * @param define Appends the string representing the PlantUML CSS properties for
	 *               the corner to the StringBuilder object.
	 */
	@Override
	public void appendPlantSubCSS(StringBuilder define) {
		if (style.plantCSS == null)
			return;
		define.append("    ").append(style.getPlantCSS()).append(' ').append(size).append('\n');
	}

	/**
	 * 
	 * @param name
	 * @param ID
	 * @param stereotype
	 * @return
	 */
	public String getPlantCommand(String name, String ID, String stereotype, String fillStyle) {
		StringBuilder result = new StringBuilder(style.command);
		result.append(" \"").append(name).append("\" as ").append(ID);
		if (stereotype != null)
			result.append(" <<").append(stereotype).append(">>");
		if (fillStyle != null)
			result.append(" ").append(fillStyle);
		return result.toString();
	}

	/**
	 * Enumerates the possible corner styles for elements in PlantUML.
	 */
	public enum Style {
		RECTANGLE("rectangle"), ROUNDEDCORNER("rectangle", "RoundCorner"),
		DIAGONALCORNER("rectangle", "DiagonalCorner"), ARTIFACT("artifact"), CARD("card"), CLOUD("cloud"),
		COMPONENT("component"), DATABASE("database"), FILE("file"), FOLDER("folder"), FRAME("frame"),
		HEXAGON("hexagon"), NODE("node"), PACKAGE("package"), STORAGE("storage");

		private final String plantCSS;
		public final String command;

		/**
		 * Constructs a {@code Style} with the specified PlantUML CSS representation.
		 *
		 * @param plantCSS The PlantUML CSS representation of the corner style.
		 */
		private Style(String command) {
			this(command, null);
		}

		private Style(String command, String plantCSS) {
			this.command = command;
			this.plantCSS = plantCSS;
		}

		/**
		 * Retrieves the PlantUML CSS representation of the corner style.
		 *
		 * @return The PlantUML CSS representation of the corner style.
		 */
		String getPlantCSS() {
			return plantCSS;
		}
	}
}
