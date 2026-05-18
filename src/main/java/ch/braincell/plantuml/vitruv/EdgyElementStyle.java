package ch.braincell.plantuml.vitruv;

import java.text.MessageFormat;

import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * This class provides an implementation of the ElementType interface for the Edgy
 * Enterprise design. The EdgyLeafType enum represents types of leafs that are
 * unique to the Edgy Enterprise design and require specific attributes and
 * behavior.
 * 
 * @author boessu
 */
public enum EdgyElementStyle implements ElementStyle {
	/**
	 * At the intersection of all three facets, four generic base elements allow us
	 * to capture what is happening in an enterprise and its ecosystem, without
	 * focusing on a particular facet or intersection perspective:
	 * 
	 * people perform activities, using and creating objects to achieve outcomes.
	 * 
	 * Everything that goes on in and around enterprises can be described with those
	 * base elements only. They are the shared vocabulary that underpins the facet
	 * and intersection elements.
	 * 
	 * This classification of enterprise elements is inspired by the FBS Ontology:
	 * outcomes represent an enterprise's actual or desired function, activities
	 * represent its behaviour over time, and objects represent the way it is
	 * enabled by structures.
	 */
	BASEFACET("base", "1"),
	/**
	 * The individuals co-creating the enterprise or using products.
	 */
	BASE_PEOPLE("people", "people"),
	/**
	 * A result or change that occurs within our enterprise or its ecosystem.
	 */
	BASE_OUTCOME("outcome", "outcomeBlack"),
	/**
	 * What is being done or going on in our enterprise or its ecosystem.
	 */
	BASE_ACTIVITY("activity", "activityBlack"),
	/**
	 * A structure that is relevant to the enterprise.
	 */
	BASE_OBJECT("object", "objectBlack"),

	/**
	 * The values and beliefs enterprises exhibit through their messages and
	 * actions.
	 */
	IDENTITYFACET("identity", "0"),
	/**
	 * A reason why an enterprise exists and what people pursue and believe in.
	 */
	IDENTITY_PURPOSE("purpose", "outcomeWhite"),
	/**
	 * The way we make sense of our enterprise and communicate to people what it
	 * does.
	 */
	IDENTITY_STORY("story", "activityWhite"),
	/**
	 * What is being communicated to people.
	 */
	IDENTITY_CONTENT("content", "objectWhite"),

	/**
	 * How are we being perceived? What is our reputation and image when people are
	 * in touch with us or our products?
	 */
	BRANDFACET("brand", "0"),
	/**
	 * Our name and what it stands for.
	 */
	BRAND("brand", "objectWhite"),

	/**
	 * The impact through interactions the enterprise has on people and their lives.
	 */
	EXPERIENCEFACET("experience", "0"),
	/**
	 * What people want to achieve and get done.
	 */
	EXPERIENCE_TASK("task", "outcomeWhite"),
	/**
	 * The means people use to engage and interact with us.
	 */
	EXPERIENCE_CHANNEL("channel", "objectWhite"),
	/**
	 * The events and activities people experience in their lives.
	 */
	EXPERIENCE_JOURNEY("journey", "activityWhite"),

	/**
	 * What do we make and offer to people? What is the result of our work? What is
	 * the value these results create for people?
	 */
	PRODUCTFACET("product", "0"),
	/**
	 * What we make, offer and deliver for people's benefit.
	 */
	PRODUCT("product", "objectWhite"),

	/**
	 * The structures needed to make an enterprise operate and connect to the
	 * ecosystem.
	 */
	ARCHITECTUREFACET("architecture", "0"),
	/**
	 * What we are able to do by orchestrating people and assets.
	 */
	ARCHITECTURE_CAPABILITY("capability", "outcomeWhite"),
	/**
	 * An object we need and use to perform our capabilities.
	 */
	ARCHITECTURE_ASSET("asset", "objectWhite"),
	/**
	 * A set of related activities our enterprise carries out.
	 */
	ARCHITECTURE_PROCESS("process", "activityWhite"),

	/**
	 * How do we organise ourselves as teams? How do we work together?
	 */
	ORGANISATIONFACET("organisation", "0"),
	/**
	 * A group of people working together.
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
	EdgyElementStyle(String command, String sprite) {
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
	 * Retrieves the enum constant that matches the given name. Defaults to BASE_OBJECT
	 * if the name is null or no match is found.
	 *
	 * @param name The name of the enum constant to retrieve.
	 * @return The matching enum constant or MEANING as the default.
	 */
	public static ElementStyle getEnum(String name) {
		if (name == null)
			return BASE_OBJECT;
		for (EdgyElementStyle value : EdgyElementStyle.values()) {
			if (name.toLowerCase().equals(value.command)) {
				return value;
			}
		}
		return BASE_OBJECT;
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
