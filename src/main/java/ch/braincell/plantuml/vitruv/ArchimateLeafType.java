package ch.braincell.plantuml.vitruv;

import java.net.URL;
import java.text.MessageFormat;

public enum ArchimateLeafType implements LeafType {

	ACCESS("access"), //
	ACTIVITY("activity"), //
	ACTOR("actor", "#LightYellow", ArchimateLeafType.P_ACTOR), //
	AGGREGATION("aggregation"), //
	APPLICATION_COLLABORATION("application-collaboration", "#APPLICATION"), //
	APPLICATION_COMPONENT("application-component", "#APPLICATION"), //
	APPLICATION_DATA_OBJECT("application-data-object", "#APPLICATION"), //
	APPLICATION_EVENT("application-event", "#APPLICATION"), //
	APPLICATION_FUNCTION("application-function", "#APPLICATION"), //
	APPLICATION_INTERACTION("application-interaction", "#APPLICATION"), //
	APPLICATION_INTERFACE("application-interface", "#APPLICATION"), //
	APPLICATION_PROCESS("application-process", "#APPLICATION"), //
	APPLICATION_SERVICE("application-service", "#APPLICATION"), //
	ASSESSMENT_FILLED("assessment-filled", "#White"), //
	ASSESSMENT("assessment"), //
	ASSIGNMENT("assignment"), //
	ASSOCIATION_UNIDIRECT("association-unidirect"), //
	ASSOCIATION("association"), //
	BUSINESS_ACTIVITY("business-activity", "#BUSINESS"), //
	BUSINESS_ACTOR("business-actor", "#BUSINESS", ArchimateLeafType.P_ACTOR), //
	BUSINESS_COLLABORATION("business-collaboration", "#BUSINESS"), //
	BUSINESS_CONTRACT("business-contract", "#BUSINESS"), //
	BUSINESS_EVENT("business-event", "#BUSINESS"), //
	BUSINESS_FUNCTION("business-function", "#BUSINESS"), //
	BUSINESS_INTERACTION("business-interaction", "#BUSINESS"), //
	BUSINESS_INTERFACE("business-interface", "#BUSINESS"), //
	BUSINESS_LOCATION("business-location", "#BUSINESS"), //
	BUSINESS_MEANING("business-meaning", "#BUSINESS"), //
	BUSINESS_OBJECT("business-object", "#BUSINESS"), //
	BUSINESS_PROCESS("business-process", "#BUSINESS"), //
	BUSINESS_PRODUCT("business-product", "#BUSINESS"), //
	BUSINESS_REPRESENTATION("business-representation", "#BUSINESS"), //
	BUSINESS_ROLE("business-role", "#BUSINESS", ArchimateLeafType.P_ACTOR), //
	BUSINESS_SERVICE("business-service", "#BUSINESS"), //
	BUSINESS_VALUE("business-value", "#BUSINESS"), //
	COLLABORATION("collaboration"), //
	COMMUNICATION_PATH("communication-path"), //
	COMPONENT("component"), //
	COMPOSITION("composition"), //
	CONSTRAINT_FILLED("constraint-filled"), //
	CONSTRAINT("constraint"), //
	CONTRACT("contract"), //
	DELIVERABLE_FILLED("deliverable-filled"), //
	DELIVERABLE("deliverable"), //
	DEVICE("device"), //
	DRIVER_FILLED("driver-filled"), //
	DRIVER("driver"), //
	EVENT("event"), //
	FLOW("flow"), //
	FUNCTION("function"), //
	GAP_FILLED("gap-filled"), //
	GAP("gap"), //
	GOAL_FILLED("goal-filled"), //
	GOAL("goal"), //
	IMPLEMENTATION_DELIVERABLE("implementation-deliverable", "#IMPLEMENTATION"), //
	IMPLEMENTATION_EVENT("implementation-event", "#IMPLEMENTATION"), //
	IMPLEMENTATION_GAP("implementation-gap", "#IMPLEMENTATION"), //
	IMPLEMENTATION_PLATEAU("implementation-plateau", "#IMPLEMENTATION"), //
	IMPLEMENTATION_WORKPACKAGE("implementation-workpackage", "#IMPLEMENTATION"), //
	INFLUENCE("influence"), //
	INTERACTION("interaction"), //
	INTERFACE_REQUIRED("interface-required"), //
	INTERFACE_SYMMETRIC("interface-symmetric"), //
	INTERFACE("interface"), //
	JUNCTION_AND("junction-and"), //
	JUNCTION_OR("junction-or"), //
	JUNCTION("junction"), //
	LOCATION("location"), //
	MEANING("meaning"), //
	MOTIVATION_ASSESSMENT("motivation-assessment", "#MOTIVATION"), //
	MOTIVATION_CONSTRAINT("motivation-constraint", "#MOTIVATION"), //
	MOTIVATION_DRIVER("motivation-driver", "#MOTIVATION"), //
	MOTIVATION_GOAL("motivation-goal", "#MOTIVATION"), //
	MOTIVATION_MEANING("motivation-meaning", "#MOTIVATION"), //
	MOTIVATION_OUTCOME("motivation-outcome", "#MOTIVATION"), //
	MOTIVATION_PRINCIPLE("motivation-principle", "#MOTIVATION"), //
	MOTIVATION_REQUIREMENT("motivation-requirement", "#MOTIVATION"), //
	MOTIVATION_STAKEHOLDER("motivation-stakeholder", "#MOTIVATION"), //
	MOTIVATION_VALUE("motivation-value", "#MOTIVATION"), //
	NETWORK("network"), //
	NODE("node"), //
	OBJECT("object"), //
	PHYSICAL_DISTRIBUTION_NETWORK("physical-distribution-network", "#PHYSICAL"), //
	PHYSICAL_EQUIPMENT("physical-equipment", "#PHYSICAL"), //
	PHYSICAL_FACILITY("physical-facility", "#PHYSICAL"), //
	PHYSICAL_MATERIAL("physical-material", "#PHYSICAL"), //
	PLATEAU("plateau"), //
	PRINCIPLE_FILLED("principle-filled"), //
	PRINCIPLE("principle"), //
	PROCESS("process"), //
	PRODUCT("product"), //
	REALISATION("realisation"), //
	REPRESENTATION("representation"), //
	REQUIREMENT_FILLED("requirement-filled"), //
	REQUIREMENT("requirement"), //
	ROLE("role"), //
	SERVICE("service"), //
	SERVING("serving"), //
	SPECIALISATION("specialisation"), //
	SPECIALIZATION("specialization"), //
	STAKEHOLDER_FILLED("stakeholder-filled"), //
	STRATEGY_CAPABILITY("strategy-capability", "#STRATEGY"), //
	STRATEGY_COURSE_OF_ACTION("strategy-course-of-action", "#STRATEGY"), //
	STRATEGY_RESOURCE("strategy-resource", "#STRATEGY"), //
	STRATEGY_VALUE_STREAM("strategy-value-stream", "#STRATEGY"), //
	SYSTEM_SOFTWARE("system-software"), //
	TECHNOLOGY_ARTIFACT("technology-artifact", "#TECHNOLOGY"), //
	TECHNOLOGY_COLLABORATION("technology-collaboration", "#TECHNOLOGY"), //
	TECHNOLOGY_COMMUNICATION_NETWORK("technology-communication-network", "#TECHNOLOGY"), //
	TECHNOLOGY_COMMUNICATION_PATH("technology-communication-path", "#TECHNOLOGY"), //
	TECHNOLOGY_DEVICE("technology-device", "#TECHNOLOGY"), //
	TECHNOLOGY_EVENT("technology-event", "#TECHNOLOGY"), //
	TECHNOLOGY_FUNCTION("technology-function", "#TECHNOLOGY"), //
	TECHNOLOGY_INFRA_INTERFACE("technology-infra-interface", "#TECHNOLOGY"), //
	TECHNOLOGY_INFRA_SERVICE("technology-infra-service", "#TECHNOLOGY"), //
	TECHNOLOGY_INTERACTION("technology-interaction", "#TECHNOLOGY"), //
	TECHNOLOGY_INTERFACE("technology-interface", "#TECHNOLOGY"), //
	TECHNOLOGY_NETWORK("technology-network", "#TECHNOLOGY"), //
	TECHNOLOGY_NODE("technology-node", "#TECHNOLOGY"), //
	TECHNOLOGY_PATH("technology-path", "#TECHNOLOGY"), //
	TECHNOLOGY_PROCESS("technology-process", "#TECHNOLOGY"), //
	TECHNOLOGY_SERVICE("technology-service", "#TECHNOLOGY"), //
	TECHNOLOGY_SYSTEM_SOFTWARE("technology-system-software", "#TECHNOLOGY"), //
	TRIGGERING("triggering"), //
	USED_BY("used-by"), //
	VALUE("value"), //
	WORKPACKAGE_FILLED("workpackage-filled");//

	private final String stereotype;
	private final String color;
	private final String plant;

	private final static String P_RECTANGLE = "archimate {2} \"{0}\" as {1} <<{3}>>\n";
	private final static String P_ACTOR = "actor \"{0}\" as {1}\n";
	private final static int P_WRAP_NAME = 25;

	private ArchimateLeafType(String stereotype) {
		this(stereotype, "#White");
	}
	
	private ArchimateLeafType(String stereotype, String color) {
		this(stereotype, color, P_RECTANGLE);
	}

	/**
	 * 
	 * @param stereotype archimate stereotype
	 * @param color 	 archimate default color
	 * @param plant      formating string which results in a plant UML string:<br>
	 *                   {0}: name<br>
	 *                   {1}: ID in plant<br>
	 *                   {2}: color and line style<br>
	 *                   {3}: archimate stereotype
	 */
	private ArchimateLeafType(String stereotype, String color, String plant) {
		this.stereotype = stereotype;
		this.color = color;
		this.plant = plant;
	}

	public static LeafType getEnum(String name) {
		if (name == null)
			return MEANING;
		LeafType result = MEANING;
		for (ArchimateLeafType value : ArchimateLeafType.values()) {
			if (name.toLowerCase().equals(value.stereotype)) {
				result = value;
			}
		}

		return result;
	}

	@Override
	public String getPlant(String name, String ID, URL url, String color, boolean bold) {
		String fatText = "";
		String fatLine = "";
		if (bold) {
			fatText = "**";
			fatLine = color == null ? "#line.bold" : ";line.bold";
		}
		String fillStyle = color == null ? fatLine : color + fatLine;
		name = fatText + StringUtil.wrap(name, P_WRAP_NAME, fatText + "\\n" + fatText) + fatText;
		String result = MessageFormat.format(plant, name, ID, fillStyle, stereotype);
		if (url != null) {
			result += "url for " + ID + " is [[" + url.toString() + "]]\n";
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
}
