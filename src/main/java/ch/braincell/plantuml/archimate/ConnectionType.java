package ch.braincell.plantuml.archimate;

import ch.braincell.plantuml.archimate.style.Color;

public enum ConnectionType {
	ACCESS("~?~"),
	ACCESS_READ("<-?~"), 
	ACCESS_READWRITE("<-?~>"), 
	ACCESS_WRITE("~?->"), 
	AGGREGATION("o-?-"), 
	ASSIGNMENT("0-?->>"),
	ASSOCIATION("=?="), 
	ASSOCIATION_DIRECTED("=?=>"), 
	COMPOSITION("*-?-"), 
	FLOW(".?.>>"), 
	INFLUENCE(".?.>"), 
	REALIZATION("~?~|>"), 
	SERVING("-?->"), 
	SPECIALIZATION("-?-|>"), 
	TRIGGERING("-?->>");
	
	private final String beginPlant;
	private final String endPlant;
	
	private ConnectionType(String plant) {
		int split = plant.indexOf('?');
		this.beginPlant = " " + plant.substring(0, split);
		this.endPlant = plant.substring(split + 1) + " ";
	}

	public static ConnectionType getEnum(String name) {
		if (name == null)
			return SERVING;
		ConnectionType result = SERVING;
		for (ConnectionType value : ConnectionType.values()) {
			if (name.toUpperCase().equals(value.name())) {
				result = value;
			}
		}
		
		return result;
	}
	
	/**
	 * TODO: Maybe StringBuilder and not String?
	 * @param color
	 * @param bold
	 * @return
	 */
	public String getPlant(Color color, boolean bold) {
		final Color resultColor = color == null ? Color.BLACK : color;
		String result = "";
		if (bold) {
			result = "[thickness=3," + resultColor + "]";
		} else if (!(resultColor == Color.BLACK)){
			result = "[" + resultColor + "]";
		}
		return beginPlant + result + endPlant;
	}
}
