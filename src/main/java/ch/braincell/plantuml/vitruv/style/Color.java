package ch.braincell.plantuml.vitruv.style;

/**
 * Represents a color using RGB(A) values. Provides predefined constants for
 * common colors and methods to manipulate and convert color values.
 */
public record Color(int argb) {
	// Predefined color constants
	/** The color black. */
	public final static Color BLACK = new Color(0xFF000000);
	/** The color white. */
	public final static Color WHITE = new Color(0xFFFFFFFF);
	/** The color red. */
	public final static Color RED = new Color(0xFFFF0000);
	/** The color green. */
	public final static Color GREEN = new Color(0xFF00FF00);
	/** The color blue. */
	public final static Color BLUE = new Color(0xFF0000FF);
	/** Transparent */
	public final static Color TRANSPARENT = new Color(0x00000000);

	/**
	 * Constructs a new {@code Color} instance using individual red, green, and blue
	 * components.
	 *
	 * @param red   The red component value of the color (0-255).
	 * @param green The green component value of the color (0-255).
	 * @param blue  The blue component value of the color (0-255).
	 */
	public Color(int red, int green, int blue) {
		this(red, green, blue, 255);
	}

	/**
	 * Constructs a new {@code Color} instance using individual red, green, and blue
	 * components.
	 *
	 * @param red   The red component value of the color (0-255).
	 * @param green The green component value of the color (0-255).
	 * @param blue  The blue component value of the color (0-255).
	 * @param alpha The transparency component value of the color (0-255).
	 */
	public Color(int red, int green, int blue, int alpha) {
		this((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF));
	}

	/**
	 * Converts Hex Code color format (e.g. #CCAA00) into {@link Color}.
	 * 
	 * @param cssColor The color in the form of a hex code like in CSS
	 * @return an instance of the according color
	 */
	public static Color fromCSS(String cssColor) {
		// check css Hex Code color format
		if (!cssColor.startsWith("#")) {
			throw new NumberFormatException("Invalid format: " + cssColor);
		}

		String hex = cssColor.substring(1);

		if (hex.length() == 6) {
			// Standard RRGGBB -> Assume Opaque (FF)
			int rgb = Integer.parseInt(hex, 16);
			return new Color(0xFF000000 | rgb);
		} else if (hex.length() == 8) {
			// PlantUML RRGGBBAA -> Convert to ARGB
			long rgba = Long.parseLong(hex, 16);
			int r = (int) ((rgba >> 24) & 0xFF);
			int g = (int) ((rgba >> 16) & 0xFF);
			int b = (int) ((rgba >> 8) & 0xFF);
			int a = (int) (rgba & 0xFF);
			return new Color(a, r, g, b);
		}

		throw new NumberFormatException("Hex color must be 6 or 8 chars (#rrggbb or #rrggbbaa): " + cssColor);
	}

	/**
	 * Returns a string representation of the color in CSS hex format.
	 *
	 * @return A string representing the color in CSS hex format (e.g., "#FFFFFF").
	 */
	@Override
	public String toString() {
		int a = (argb >> 24) & 0xFF;
		int r = (argb >> 16) & 0xFF;
		int g = (argb >> 8) & 0xFF;
		int b = argb & 0xFF;

		// If fully opaque, standard #RRGGBB is usually fine
		if (a == 255) {
			return String.format("#%02X%02X%02X", r, g, b);
		}

		// PlantUML Alpha Suffix: #RRGGBBAA
		return String.format("#%02X%02X%02X%02X", r, g, b, a);
	}
}
