package ch.braincell.plantuml.vitruv.style;

public record Color(int rgb) {
	public final static Color BLACK = new Color(0x000000);
	public final static Color WHITE = new Color(0xFFFFFF);
	public final static Color RED = new Color(0xFF0000);
	public final static Color GREEN = new Color(0x00FF00);
	public final static Color BLUE = new Color(0x0000FF);

	public Color(int red, int green, int blue) {
		this(red << 16 | green << 8 | blue);
	}

	/**
	 * Converts Hex Code color format (e.g. #CCAA00) into {@link Color}.
	 * 
	 * @param cssColor
	 * @return
	 */
	public static Color fromCSS(String cssColor) {
		// check css Hex Code color format
		if (cssColor.matches("#[0-9a-fA-F]{6}")) {
			int rgb = Integer.parseInt(cssColor.substring(1, 7), 16);
			return new Color(rgb);
		}

		throw new NumberFormatException("Invalid css color format (should be #rrggbb): " + cssColor);
	}

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

//		return "#" + StringUtils.leftPad(Integer.toHexString(rgb), 6, '0');
	}
}
