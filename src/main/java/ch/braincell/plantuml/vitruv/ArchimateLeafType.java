package ch.braincell.plantuml.vitruv;

import java.text.MessageFormat;

import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * Enum representing different types of leaf elements in Archimate modeling.
 * Each leaf type is associated with a specific stereotype and visual
 * representation in PlantUML diagrams.
 */
public enum ArchimateLeafType implements ElementStyle {

	//////////// General, neutral Elements ////////////

	/**
	 * Represents an 'access' leaf type in Archimate.
	 */
	ACCESS("access"),
	/**
	 * Represents an 'activity' leaf type in Archimate.
	 */
	ACTIVITY("activity"),
	/**
	 * Represents an 'actor' leaf type in Archimate, with a default color of light
	 * yellow.
	 */
	ACTOR("actor", "#LightYellow", ArchimateLeafType.P_ACTOR),
	/**
	 * Represents an 'aggregation' leaf type in Archimate.
	 */
	AGGREGATION("aggregation"),
	/**
	 * Represents an 'filled assessment' leaf type in Archimate, with a specific
	 * color code.
	 */
	ASSESSMENT_FILLED("assessment-filled", "#White"),
	/**
	 * Represents an 'assessment' leaf type in Archimate.
	 */
	ASSESSMENT("assessment"),
	/**
	 * Represents an 'assignment' leaf type in Archimate.
	 */
	ASSIGNMENT("assignment"),
	/**
	 * Represents a 'unidirectional association' leaf type in Archimate.
	 */
	ASSOCIATION_UNIDIRECT("association-unidirect"),
	/**
	 * Represents an 'association' leaf type in Archimate.
	 */
	ASSOCIATION("association"),
	/**
	 * Represents a 'collaboration' leaf type in Archimate.
	 */
	COLLABORATION("collaboration"),
	/**
	 * Represents a 'communication path' leaf type in Archimate.
	 */
	COMMUNICATION_PATH("communication-path"),
	/**
	 * Represents a 'component' leaf type in Archimate.
	 */
	COMPONENT("component"),
	/**
	 * Represents a 'composition' leaf type in Archimate.
	 */
	COMPOSITION("composition"),
	/**
	 * Represents a 'filled constraint' leaf type in Archimate.
	 */
	CONSTRAINT_FILLED("constraint-filled"),
	/**
	 * Represents a 'constraint' leaf type in Archimate.
	 */
	CONSTRAINT("constraint"),
	/**
	 * Represents a 'contract' leaf type in Archimate.
	 */
	CONTRACT("contract"),
	/**
	 * Represents a 'filled deliverable' leaf type in Archimate.
	 */
	DELIVERABLE_FILLED("deliverable-filled"),
	/**
	 * Represents a 'deliverable' leaf type in Archimate.
	 */
	DELIVERABLE("deliverable"),
	/**
	 * Represents a 'device' leaf type in Archimate.
	 */
	DEVICE("device"),
	/**
	 * Represents a 'filled driver' leaf type in Archimate.
	 */
	DRIVER_FILLED("driver-filled"),
	/**
	 * Represents a 'driver' leaf type in Archimate.
	 */
	DRIVER("driver"),
	/**
	 * Represents an 'event' leaf type in Archimate.
	 */
	EVENT("event"),
	/**
	 * Represents a 'flow' leaf type in Archimate.
	 */
	FLOW("flow"),
	/**
	 * Represents a 'function' leaf type in Archimate.
	 */
	FUNCTION("function"),
	/**
	 * Represents a 'filled gap' leaf type in Archimate.
	 */
	GAP_FILLED("gap-filled"),
	/**
	 * Represents a 'gap' leaf type in Archimate.
	 */
	GAP("gap"),
	/**
	 * Represents a 'filled goal' leaf type in Archimate.
	 */
	GOAL_FILLED("goal-filled"),
	/**
	 * Represents a 'goal' leaf type in Archimate.
	 */
	GOAL("goal"),
	/**
	 * Represents an 'influence' leaf type in Archimate.
	 */
	INFLUENCE("influence"),
	/**
	 * Represents an 'interaction' leaf type in Archimate.
	 */
	INTERACTION("interaction"),
	/**
	 * Represents a 'required interface' leaf type in Archimate.
	 */
	INTERFACE_REQUIRED("interface-required"),
	/**
	 * Represents a 'symmetric interface' leaf type in Archimate.
	 */
	INTERFACE_SYMMETRIC("interface-symmetric"),
	/**
	 * Represents an 'interface' leaf type in Archimate.
	 */
	INTERFACE("interface"),
	/**
	 * Represents an 'and junction' leaf type in Archimate.
	 */
	JUNCTION_AND("junction-and"),
	/**
	 * Represents an 'or junction' leaf type in Archimate.
	 */
	JUNCTION_OR("junction-or"),
	/**
	 * Represents a 'junction' leaf type in Archimate.
	 */
	JUNCTION("junction"),
	/**
	 * Represents a 'location' leaf type in Archimate.
	 */
	LOCATION("location"),
	/**
	 * Represents a 'meaning' leaf type in Archimate.
	 */
	MEANING("meaning"),
	/** Represents a 'network' leaf type in Archimate. */
	NETWORK("network"),
	/** Represents a 'node' leaf type in Archimate. */
	NODE("node"),
	/** Represents an 'object' leaf type in Archimate. */
	OBJECT("object"),
	/** Represents a 'plateau' leaf type in Archimate. */
	PLATEAU("plateau"),
	/** Represents a 'filled principle' leaf type in Archimate. */
	PRINCIPLE_FILLED("principle-filled"),
	/** Represents a 'principle' leaf type in Archimate. */
	PRINCIPLE("principle"),
	/** Represents a 'process' leaf type in Archimate. */
	PROCESS("process"),
	/** Represents a 'product' leaf type in Archimate. */
	PRODUCT("product"),
	/** Represents a 'realisation' leaf type in Archimate. */
	REALISATION("realisation"),
	/** Represents a 'representation' leaf type in Archimate. */
	REPRESENTATION("representation"),
	/** Represents a 'filled requirement' leaf type in Archimate. */
	REQUIREMENT_FILLED("requirement-filled"),
	/** Represents a 'requirement' leaf type in Archimate. */
	REQUIREMENT("requirement"),
	/** Represents a 'role' leaf type in Archimate. */
	ROLE("role"),
	/** Represents a 'service' leaf type in Archimate. */
	SERVICE("service"),
	/** Represents a 'serving' leaf type in Archimate. */
	SERVING("serving"),
	/** Represents a 'specialisation' leaf type in Archimate. */
	SPECIALISATION("specialisation"),
	/** Represents a 'specialization' leaf type in Archimate. */
	SPECIALIZATION("specialization"),
	/** Represents a 'filled stakeholder' leaf type in Archimate. */
	STAKEHOLDER_FILLED("stakeholder-filled"),
	/** Represents a 'system software' leaf type in Archimate. */
	SYSTEM_SOFTWARE("system-software"),
	/** Represents a 'triggering' leaf type in Archimate. */
	TRIGGERING("triggering"),
	/** Represents a 'used by' leaf type in Archimate. */
	USED_BY("used-by"),
	/** Represents a 'value' leaf type in Archimate. */
	VALUE("value"),
	/** Represents a 'workpackage-filled' leaf type in Archimate. */
	WORKPACKAGE_FILLED("workpackage-filled"),

	//////////// Application Layer ////////////

	/**
	 * Represents an 'application collaboration' leaf type in Archimate, with a
	 * specific color code.
	 */
	APPLICATION_COLLABORATION("application-collaboration", "#APPLICATION"),
	/**
	 * Represents an 'application component' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_COMPONENT("application-component", "#APPLICATION"),
	/**
	 * Represents an 'application data object' leaf type in Archimate, with a
	 * specific color code.
	 */
	APPLICATION_DATA_OBJECT("application-data-object", "#APPLICATION"),
	/**
	 * Represents an 'application event' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_EVENT("application-event", "#APPLICATION"),
	/**
	 * Represents an 'application function' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_FUNCTION("application-function", "#APPLICATION"),
	/**
	 * Represents an 'application interaction' leaf type in Archimate, with a
	 * specific color code.
	 */
	APPLICATION_INTERACTION("application-interaction", "#APPLICATION"),
	/**
	 * Represents an 'application interface' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_INTERFACE("application-interface", "#APPLICATION"),
	/**
	 * Represents an 'application process' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_PROCESS("application-process", "#APPLICATION"),
	/**
	 * Represents an 'application service' leaf type in Archimate, with a specific
	 * color code.
	 */
	APPLICATION_SERVICE("application-service", "#APPLICATION"),

	//////////// Business Layer ////////////

	/**
	 * Represents a 'business activity' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_ACTIVITY("business-activity", "#BUSINESS"),
	/**
	 * Represents a 'business actor' leaf type in Archimate, with a specific color
	 * code and actor format.
	 */
	BUSINESS_ACTOR("business-actor", "#BUSINESS", ArchimateLeafType.P_ACTOR),
	/**
	 * Represents a 'business collaboration' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_COLLABORATION("business-collaboration", "#BUSINESS"),
	/**
	 * Represents a 'business contract' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_CONTRACT("business-contract", "#BUSINESS"),
	/**
	 * Represents a 'business event' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_EVENT("business-event", "#BUSINESS"),
	/**
	 * Represents a 'business function' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_FUNCTION("business-function", "#BUSINESS"),
	/**
	 * Represents a 'business interaction' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_INTERACTION("business-interaction", "#BUSINESS"),
	/**
	 * Represents a 'business interface' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_INTERFACE("business-interface", "#BUSINESS"),
	/**
	 * Represents a 'business location' leaf type in Archimate, with a specific
	 * color code.
	 */
	BUSINESS_LOCATION("business-location", "#BUSINESS"),
	/**
	 * Represents a 'business meaning' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_MEANING("business-meaning", "#BUSINESS"),
	/**
	 * Represents a 'business object' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_OBJECT("business-object", "#BUSINESS"),
	/**
	 * Represents a 'business process' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_PROCESS("business-process", "#BUSINESS"),
	/**
	 * Represents a 'business product' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_PRODUCT("business-product", "#BUSINESS"),
	/**
	 * Represents a 'business representation' leaf type in Archimate, with a
	 * specific color code.
	 */
	BUSINESS_REPRESENTATION("business-representation", "#BUSINESS"),
	/**
	 * Represents a 'business role' leaf type in Archimate, with a specific color
	 * code and actor format.
	 */
	BUSINESS_ROLE("business-role", "#BUSINESS"),
	/**
	 * Represents a 'business service' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_SERVICE("business-service", "#BUSINESS"),
	/**
	 * Represents a 'business value' leaf type in Archimate, with a specific color
	 * code.
	 */
	BUSINESS_VALUE("business-value", "#BUSINESS"),

	//////////// Implementation Layer ////////////

	/**
	 * Represents an 'implementation deliverable' leaf type in Archimate, with a
	 * specific color code.
	 */
	IMPLEMENTATION_DELIVERABLE("implementation-deliverable", "#IMPLEMENTATION"),
	/**
	 * Represents an 'implementation event' leaf type in Archimate, with a specific
	 * color code.
	 */
	IMPLEMENTATION_EVENT("implementation-event", "#IMPLEMENTATION"),
	/**
	 * Represents an 'implementation gap' leaf type in Archimate, with a specific
	 * color code.
	 */
	IMPLEMENTATION_GAP("implementation-gap", "#IMPLEMENTATION"),
	/**
	 * Represents an 'implementation plateau' leaf type in Archimate, with a
	 * specific color code.
	 */
	IMPLEMENTATION_PLATEAU("implementation-plateau", "#IMPLEMENTATION"),
	/**
	 * Represents an 'implementation workpackage' leaf type in Archimate, with a
	 * specific color code.
	 */
	IMPLEMENTATION_WORKPACKAGE("implementation-workpackage", "#IMPLEMENTATION"),

	//////////// Motivation Layer ////////////

	/**
	 * Represents a 'motivation assessment' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_ASSESSMENT("motivation-assessment", "#MOTIVATION"),
	/**
	 * Represents a 'motivation constraint' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_CONSTRAINT("motivation-constraint", "#MOTIVATION"),
	/**
	 * Represents a 'motivation driver' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_DRIVER("motivation-driver", "#MOTIVATION"),
	/**
	 * Represents a 'motivation goal' leaf type in Archimate, with a specific color
	 * code.
	 */
	MOTIVATION_GOAL("motivation-goal", "#MOTIVATION"),
	/**
	 * Represents a 'motivation meaning' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_MEANING("motivation-meaning", "#MOTIVATION"),
	/**
	 * Represents a 'motivation outcome' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_OUTCOME("motivation-outcome", "#MOTIVATION"),
	/**
	 * Represents a 'motivation principle' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_PRINCIPLE("motivation-principle", "#MOTIVATION"),
	/**
	 * Represents a 'motivation requirement' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_REQUIREMENT("motivation-requirement", "#MOTIVATION"),
	/**
	 * Represents a 'motivation stakeholder' leaf type in Archimate, with a specific
	 * color code.
	 */
	MOTIVATION_STAKEHOLDER("motivation-stakeholder", "#MOTIVATION"),
	/**
	 * Represents a 'motivation value' leaf type in Archimate, with a specific color
	 * code.
	 */
	MOTIVATION_VALUE("motivation-value", "#MOTIVATION"),

	//////////// Physical Layer ////////////

	/**
	 * Represents a 'physical distribution network' leaf type in Archimate, with a
	 * specific color code.
	 */
	PHYSICAL_DISTRIBUTION_NETWORK("physical-distribution-network", "#PHYSICAL"),
	/**
	 * Represents a 'physical equipment' leaf type in Archimate, with a specific
	 * color code.
	 */
	PHYSICAL_EQUIPMENT("physical-equipment", "#PHYSICAL"),
	/**
	 * Represents a 'physical facility' leaf type in Archimate, with a specific
	 * color code.
	 */
	PHYSICAL_FACILITY("physical-facility", "#PHYSICAL"),
	/**
	 * Represents a 'physical material' leaf type in Archimate, with a specific
	 * color code.
	 */
	PHYSICAL_MATERIAL("physical-material", "#PHYSICAL"),

	//////////// Strategic Layer ////////////

	/**
	 * Represents a 'strategy capability' leaf type in Archimate, with a specific
	 * color code.
	 */
	STRATEGY_CAPABILITY("strategy-capability", "#STRATEGY"),
	/**
	 * Represents a 'strategy course of action' leaf type in Archimate, with a
	 * specific color code.
	 */
	STRATEGY_COURSE_OF_ACTION("strategy-course-of-action", "#STRATEGY"),
	/**
	 * Represents a 'strategy resource' leaf type in Archimate, with a specific
	 * color code.
	 */
	STRATEGY_RESOURCE("strategy-resource", "#STRATEGY"),
	/**
	 * Represents a 'strategy value stream' leaf type in Archimate, with a specific
	 * color code.
	 */
	STRATEGY_VALUE_STREAM("strategy-value-stream", "#STRATEGY"),

	//////////// Technology Layer ////////////

	/**
	 * Represents a 'technology artifact' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_ARTIFACT("technology-artifact", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology collaboration' leaf type in Archimate, with a
	 * specific color code.
	 */
	TECHNOLOGY_COLLABORATION("technology-collaboration", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology communication network' leaf type in Archimate, with
	 * a specific color code.
	 */
	TECHNOLOGY_COMMUNICATION_NETWORK("technology-communication-network", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology communication path' leaf type in Archimate, with a
	 * specific color code.
	 */
	TECHNOLOGY_COMMUNICATION_PATH("technology-communication-path", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology device' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_DEVICE("technology-device", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology event' leaf type in Archimate, with a specific color
	 * code.
	 */
	TECHNOLOGY_EVENT("technology-event", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology function' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_FUNCTION("technology-function", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology infrastructure interface' leaf type in Archimate,
	 * with a specific color code.
	 */
	TECHNOLOGY_INFRA_INTERFACE("technology-infra-interface", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology infrastructure service' leaf type in Archimate, with
	 * a specific color code.
	 */
	TECHNOLOGY_INFRA_SERVICE("technology-infra-service", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology interaction' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_INTERACTION("technology-interaction", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology interface' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_INTERFACE("technology-interface", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology network' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_NETWORK("technology-network", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology node' leaf type in Archimate, with a specific color
	 * code.
	 */
	TECHNOLOGY_NODE("technology-node", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology path' leaf type in Archimate, with a specific color
	 * code.
	 */
	TECHNOLOGY_PATH("technology-path", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology process' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_PROCESS("technology-process", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology service' leaf type in Archimate, with a specific
	 * color code.
	 */
	TECHNOLOGY_SERVICE("technology-service", "#TECHNOLOGY"),
	/**
	 * Represents a 'technology system software' leaf type in Archimate, with a
	 * specific color code.
	 */
	TECHNOLOGY_SYSTEM_SOFTWARE("technology-system-software", "#TECHNOLOGY");

	// Fields representing the stereotype, default color, and PlantUML format string
	// for each leaf type
	private final String stereotype;
	private final String color;
	private final String plant;

	// Format strings for PlantUML representation of rectangle and actor elements
	private final static String P_ARCHIMATE = "archimate {2} \"{0}\" as {1} <<{3}>>";
	private final static String P_ACTOR = "actor \"{0}\" as {1}";
	// Maximum character width for wrapping names in PlantUML
	private final static int P_WRAP_NAME = 25;

	// Constructors for defining leaf types with varying levels of detail
	ArchimateLeafType(String stereotype) {
		this(stereotype, "#White");
	}

	ArchimateLeafType(String stereotype, String color) {
		this(stereotype, color, P_ARCHIMATE);
	}

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
	 *                   {3}: archimate stereotype
	 */
	ArchimateLeafType(String stereotype, String color, String plant) {
		this.stereotype = stereotype;
		this.color = color;
		this.plant = plant;
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
			return MEANING;
		for (ArchimateLeafType value : ArchimateLeafType.values()) {
			if (name.toLowerCase().equals(value.stereotype)) {
				return value;
			}
		}
		return MEANING;
	}

	@Override
	public String getPlant(String name, String ID, Block.Link link, String color, boolean bold, String grouping) {
		// TODO: Grouped
		String result;
		String fatText = "";
		String fatLine = "";
		color = color == null ? this.color : color;
		if (bold) {
			fatText = "**";
			fatLine = color == null ? "#line.bold" : ";line.bold";
		}
		String fillStyle = color == null ? fatLine : color + fatLine;
//		name = fatText + StringUtil.wrap(name, P_WRAP_NAME, fatText + "\\n" + fatText) + fatText;
		name = StringUtil.wrap(fatText + name + fatText, P_WRAP_NAME, "\\n");
		if (grouping == null || grouping.isBlank()) {
			result = MessageFormat.format(plant, name, ID, fillStyle, stereotype) + '\n';
		} else {
			result = MessageFormat.format(P_ARCHIMATE, name, ID, fillStyle, stereotype) + " {\n";
			result += grouping;
			result += "}\n";
		}

		if (link != null) {
			result += "url for " + ID + " is [[" + link.url() + "]]\n";
		}

		return result;
	}

	@Override
	public String getStereotype() {
		return stereotype;
	}

	@Override
	public String getColor() {
		return color;
	}

	@Override
	public String getImport() {
		return "!include <archimate/Archimate>";
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
