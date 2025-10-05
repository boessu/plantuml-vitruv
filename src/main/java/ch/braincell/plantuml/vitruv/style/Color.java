package ch.braincell.plantuml.vitruv.style;

/**
 * Represents a color using RGB values. Provides predefined constants for common
 * colors and methods to manipulate and convert color values.
 */
public record Color(int rgb) {
	// Predefined color constants
	/** The color black. */
	public final static Color BLACK = new Color(0x000000);
	/** The color white. */
	public final static Color WHITE = new Color(0xFFFFFF);
	/** The color red. */
	public final static Color RED = new Color(0xFF0000);
	/** The color green. */
	public final static Color GREEN = new Color(0x00FF00);
	/** The color blue. */
	public final static Color BLUE = new Color(0x0000FF);

	/**
	 * Constructs a new {@code Color} instance using individual red, green, and blue
	 * components.
	 *
	 * @param red   The red component value of the color (0-255).
	 * @param green The green component value of the color (0-255).
	 * @param blue  The blue component value of the color (0-255).
	 */
	public Color(int red, int green, int blue) {
		this(red << 16 | green << 8 | blue);
	}

	/**
	 * Converts Hex Code color format (e.g. #CCAA00) into {@link Color}.
	 * 
	 * @param cssColor The color in the form of a hex code like in CSS
	 * @return an instance of the according color
	 */
	public static Color fromCSS(String cssColor) {
		// check css Hex Code color format
		if (cssColor.matches("#[0-9a-fA-F]{6}")) {
			int rgb = Integer.parseInt(cssColor.substring(1, 7), 16);
			return new Color(rgb);
		}

		throw new NumberFormatException("Invalid css color format (should be #rrggbb): " + cssColor);
	}

	/**
	 * Returns a string representation of the color in CSS hex format.
	 *
	 * @return A string representing the color in CSS hex format (e.g., "#FFFFFF").
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(Integer.toHexString(rgb));
		if (sb.length() < 6) {
			int charsToGo = 6 - sb.length();
			while (charsToGo > 0) {
				sb.insert(0, '0');
				charsToGo--;
			}
		}

		sb.insert(0, "#");

		return sb.toString();
	}
}
