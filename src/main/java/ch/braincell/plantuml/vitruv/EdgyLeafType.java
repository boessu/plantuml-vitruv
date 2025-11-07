package ch.braincell.plantuml.vitruv;

import java.text.MessageFormat;

import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * This class provides an implementation of the LeafType interface for the Edgy
 * Enterprise design. The EdgyLeafType enum represents types of leafs that are
 * unique to the Edgy Enterprise design and require specific attributes and
 * behavior.
 */
public enum EdgyLeafType implements ElementStyle {
	/**
	 * 
	 */
	BASEFACET("base", "1"),
	/**
	 * 
	 */
	PEOPLE("people", "people"),
	/**
	 * 
	 */
	OUTCOME("outcome", "outcomeBlack"),
	/**
	 * 
	 */
	ACTIVITY("activity", "activityBlack"),
	/**
	 * 
	 */
	OBJECT("object", "objectBlack"),

	/**
	 * 
	 */
	IDENTITYFACET("identity", "0"),
	/**
	 * 
	 */
	PURPOSE("purpose", "outcomeWhite"),
	/**
	 * 
	 */
	STORY("story", "activityWhite"),
	/**
	 * 
	 */
	CONTENT("content", "objectWhite"),

	/**
	 * 
	 */
	BRANDFACET("brand", "0"),
	/**
	 * 
	 */
	BRAND("brand", "objectWhite"),

	/**
	 * 
	 */
	EXPERIENCEFACET("experience", "0"),
	/**
	 * 
	 */
	TASK("task", "outcomeWhite"),
	/**
	 * 
	 */
	CHANNEL("channel", "objectWhite"),
	/**
	 * 
	 */
	JOURNEY("journey", "activityWhite"),

	/**
	 * 
	 */
	PRODUCTFACET("product", "0"),
	/**
	 * 
	 */
	PRODUCT("product", "objectWhite"),

	/**
	 * 
	 */
	ARCHITECTUREFACET("architecture", "0"),
	/**
	 * 
	 */
	CAPABILITY("capability", "outcomeWhite"),
	/**
	 * 
	 */
	ASSET("asset", "objectWhite"),
	/**
	 * 
	 */
	PROCESS("process", "activityWhite"),

	/**
	 * 
	 */
	ORGANISATIONFACET("organisation", "0"),
	/**
	 * 
	 */
	ORGANISATION("organisation", "objectWhite");

	// Fields representing the stereotype, default color, and PlantUML format string
	// for each leaf type
	private final String command;
	private final String builderCommand;
	private final String sprite;
	private final int facet;

	// Format strings for normal edgy elements:
	// {0} = command
	// {1} = label
	// {2} = ID
	private final static String P_EDGY = "${0}(\"{1}\", {2}, {3})";
	// Format strings for edgy builder:
	// $elementBuilder($name, $label, $alias, $islight, $fillcolor,
	// $fillcolorlight=#000000, $facet, $noSprite)
	// {0} = command, first letter upper.
	// {1} = label
	// {2} = ID
	// {3} = color
	// {4} = facet style (0 = false, 1 = true)
	// {5} = draw sprite (1 = true)
	private final static String P_EDGY_BUILDER = "$elementBuilder(\"{0}\", \"{1}\", {2}, 0, \"{3}\", \"#ffffff\", {4}, {5})";
	// Maximum character width for wrapping names in PlantUML
	private final static int P_WRAP_NAME = 25;

	/**
	 * Constructor for leaf type with full specification of stereotype, color, and
	 * PlantUML format.
	 *
	 * @param stereotype archimate stereotype
	 * @param color      archimate default color
	 * @param plant      formating string which results in a plant UML string:<br>
	 *                   {0}: name<br>
	 *                   {1}: ID in plant<br>
	 *                   {2}: color and line style<br>
	 *                   {3}: edgy stereotype {4}: sprite
	 */
	EdgyLeafType(String command, String sprite) {
		if (sprite.equals("1") || sprite.equals("0")) {
			this.command = command + "Facet";
			facet = 1;
		} else {
			this.command = command;
			facet = 0;
		}
		this.builderCommand = Character.toUpperCase(command.charAt(0)) + command.substring(1);
		this.sprite = sprite;
	}

	/**
	 * Retrieves the enum constant that matches the given name. Defaults to MEANING
	 * if the name is null or no match is found.
	 *
	 * @param name The name of the enum constant to retrieve.
	 * @return The matching enum constant or MEANING as the default.
	 */
	public static ElementStyle getEnum(String name) {
		if (name == null)
			return OBJECT;
		for (EdgyLeafType value : EdgyLeafType.values()) {
			if (name.toLowerCase().equals(value.command)) {
				return value;
			}
		}
		return OBJECT;
	}

	@Override
	public String getPlant(String name, String ID, Block.Link link, String color, boolean bold, String grouping) {
		String result;
		String fatText = "";
		if (bold) {
			fatText = "**";
		}
		name = fatText + StringUtil.wrap(name, P_WRAP_NAME, fatText + "\\n" + fatText) + fatText;
		if (grouping == null || grouping.isBlank()) {
			result = convertPlant(name, ID, color, bold) + '\n';
		} else {
			result = convertPlant(name, ID, color, bold) + " {\n";
			result += grouping;
			result += "}\n";
		}

		if (link != null) {
			result += "url for " + ID + " is [[" + link.url() + "]]\n";
		}
		return result;
	}

	private String convertPlant(String name, String ID, String color, boolean bold) {
		String result = MessageFormat.format(P_EDGY, command, name, ID, bold ? 1 : 0);
		if (color != null) {
			result = MessageFormat.format(P_EDGY_BUILDER, builderCommand, name, ID, color, facet, sprite);
		}

		return result;
	}

	@Override
	public String getStereotype() {
		return null;
	}

	@Override
	public String getColor() {
		return null;
	}

	@Override
	public String getImport() {
		return "!include <edgy/edgy>";
	}

	@Override
	public StyleSheet getPlantCSS() {
		return null;
	}

	@Override
	public String getSpriteHeader() {
		return null;
	}
}
