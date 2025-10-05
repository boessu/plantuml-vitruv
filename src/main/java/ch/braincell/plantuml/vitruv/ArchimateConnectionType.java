package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

/**
 * Enum representing different types of connections in Archimate modeling. Each
 * connection type is associated with a specific visual representation in
 * PlantUML diagrams.
 */
public enum ArchimateConnectionType implements ConnectionType {
	/** Represents an access connection in Archimate. */
	ACCESS("~?~"),
	/** Represents a read-only access connection in Archimate. */
	ACCESS_READ("<-?~"),
	/** Represents a read-write access connection in Archimate. */
	ACCESS_READWRITE("<-?~>"),
	/** Represents a write-only access connection in Archimate. */
	ACCESS_WRITE("~?->"),
	/** Represents an aggregation connection in Archimate. */
	AGGREGATION("o-?-"),
	/** Represents an assignment connection in Archimate. */
	ASSIGNMENT("0-?->>"),
	/** Represents an association connection in Archimate. */
	ASSOCIATION("=?="),
	/** Represents a directed association connection in Archimate. */
	ASSOCIATION_DIRECTED("=?=>"),
	/** Represents a composition connection in Archimate. */
	COMPOSITION("*-?-"),
	/** Represents a flow connection in Archimate. */
	FLOW(".?.>>"),
	/** Represents an influence connection in Archimate. */
	INFLUENCE(".?.>"),
	/** Represents a realization connection in Archimate. */
	REALIZATION("~?~|>"),
	/** Represents a serving connection in Archimate. */
	SERVING("-?->"),
	/** Represents a specialization connection in Archimate. */
	SPECIALIZATION("-?-|>"),
	/** Represents a triggering connection in Archimate. */
	TRIGGERING("-?->>");

	// Fields representing the beginning and ending syntax for PlantUML
	private final String beginPlant;
	private final String endPlant;

	/**
	 * Constructor for the connection type enum. It splits the PlantUML syntax into
	 * beginning and ending parts.
	 *
	 * @param plant The complete PlantUML syntax for the connection type.
	 */
    ArchimateConnectionType(String plant) {
		int split = plant.indexOf('?');
		this.beginPlant = " " + plant.substring(0, split);
		this.endPlant = plant.substring(split + 1) + " ";
	}

	/**
	 * Retrieves the enum constant that matches the given name. Defaults to SERVING
	 * if the name is null or no match is found.
	 *
	 * @param name The name of the enum constant to retrieve.
	 * @return The matching enum constant or SERVING as the default.
	 */
	public static ConnectionType getEnum(String name) {
		if (name == null)
			return SERVING;
		for (ArchimateConnectionType value : ArchimateConnectionType.values()) {
			if (name.toUpperCase().equals(value.name())) {
				return value;
			}
		}

		return SERVING;
	}

	@Override
	public String getPlant(Color color, boolean bold) {
		final Color resultColor = color == null ? Color.BLACK : color;
		String result = "";
		if (bold) {
			result = "[thickness=3," + resultColor + "]";
		} else if (!(resultColor == Color.BLACK)) {
			result = "[" + resultColor + "]";
		}
		return beginPlant + result + endPlant;
	}
}
