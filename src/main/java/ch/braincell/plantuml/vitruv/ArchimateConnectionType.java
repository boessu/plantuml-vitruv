package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

/**
 * Enum representing different types of connections in Archimate modeling. Each
 * connection type is associated with a specific visual representation in
 * PlantUML diagrams.
 * <p>
 * This enum defines all possible connection types used in Archimate diagrams,
 * including their symbolic representations for use in PlantUML. These
 * connections are used to depict relationships such as access, assignment,
 * association, composition, flow, influence, realization, serving,
 * specialization, and triggering between elements.
 * </p>
 *
 * @author boessu
 */
public enum ArchimateConnectionType implements ConnectionType {

	// -- Structural Relationships --
	/**
	 * Indicates that an element consists of one or more other concepts. If the
	 * parent is destroyed, the child is usually destroyed too (e.g., a "Financial
	 * Suite" composed of "Invoicing" and "Ledger" modules).
	 */
	COMPOSITION("*-?-", Category.STRUCTURAL),
	/**
	 * A weaker form of composition. It indicates that an element groups other
	 * concepts, but those concepts can exist independently.
	 */
	AGGREGATION("o-?-", Category.STRUCTURAL),
	/**
	 * Links units of behavior to the active structure (the "actor") that performs
	 * them. For example, a Business Actor is assigned to a Business Process.
	 */
	ASSIGNMENT("0-?->>", Category.STRUCTURAL),
	/**
	 * Indicates that an entity plays a critical role in creating, achieving, or
	 * delivering a more abstract entity (e.g., a "Web Service" realizes a "Data
	 * Access Interface").
	 */
	REALIZATION("~?~|>", Category.STRUCTURAL),

	// -- Dependency Relationships --
	/**
	 * Formerly known as "Used By." This shows that one element provides its
	 * functionality to another. For example, an Application Service serves a
	 * Business Process.
	 */
	SERVING("-?->", Category.DEPENDENCY),
	/**
	 * Represents a general dependency or communication between behavior and data
	 * where the specific direction of the flow is either unknown, non-directional,
	 * or irrelevant for the current architectural view. Used in high-level
	 * conceptual diagrams or "Context Maps" where the goal is to show that a
	 * relationship exists without defining the CRUD (Create, Read, Update, Delete)
	 * specifics (e.g. A Human Resources System accesses Employee Records.)
	 */
	ACCESS("~?~", Category.DEPENDENCY),
	/**
	 * Represents a "Read" or "View" operation. The behavioral element requires the
	 * data as an input to perform its function but does not alter the state of that
	 * data.Used when modeling information retrieval, lookups, or decision support
	 * where the underlying record remains pristine (e.g. Credit Check Service reads
	 * a Customer Credit History.).
	 */
	ACCESS_READ("<-?~", Category.DEPENDENCY),
	/**
	 * Represents a bidirectional flow of information where the behavioral element
	 * both retrieves and subsequently modifies the same data object within a single
	 * logic flow. Often used for "Calculated Updates" where the current state must
	 * be known before a new state can be determined and saved (e.g. An Inventory
	 * Management Function reads and writes a Stock Level Object to decrement
	 * quantities based on sales.)
	 */
	ACCESS_READWRITE("<-?~>", Category.DEPENDENCY),
	/**
	 * Represents a "Create," "Update," or "Delete" operation. The behavioral
	 * element is responsible for changing the state, existence, or value of the
	 * data object. Used to model the output of a process or the modification of a
	 * system's state (e.g. An Order Fulfillment Process writes (updates) the status
	 * of an Order Object to "Shipped.")
	 */
	ACCESS_WRITE("~?->", Category.DEPENDENCY),
	/**
	 * Used primarily in Motivation elements to show how one requirement or goal
	 * affects another, either positively or negatively.
	 */
	INFLUENCE(".?.>", Category.DEPENDENCY),

	// -- Dynamic Relationships --
	/**
	 * Describes a temporal or causal relationship. One process step triggers the
	 * next one immediately (e.g., "Receive Order" triggers "Process Payment").
	 */
	TRIGGERING("-?->>", Category.DYNAMIC),
	/**
	 * Describes the transfer of information or value between elements without
	 * necessarily implying a strict sequence of time.
	 */
	FLOW(".?.>>", Category.DYNAMIC),

	// -- Other Relationships --
	/**
	 * The "is-a" relationship. It indicates that an element is a specific type of a
	 * more general element (e.g., a "Premium Account" is a specialization of
	 * "Account").
	 */
	SPECIALIZATION("-?-|>", Category.OTHER),
	/**
	 * The most generic relationship. It indicates a link between two elements that
	 * isn't captured by other specific relationship types.
	 */
	ASSOCIATION("=?=", Category.OTHER),
	/**
	 * Represents a directed link between two elements where the relationship has a
	 * specific orientation, but the exact nature (like "realization" or
	 * "assignment") is not specified. It signifies that the source element is
	 * related to the target element in a way that implies a "to," "towards," or
	 * "uses" logic. Used when a relationship exists but doesn't fit the strict
	 * rules of other ArchiMate connectors. Useful for modeling general flows or
	 * dependencies in early-stage architecture "sketches." Frequently used to
	 * connect Requirement or Constraint elements to other concepts to show which
	 * element the requirement applies to. Example: A Legal Regulation (Requirement)
	 * has a Directed Association to a Business Process, indicating the process must
	 * comply with that specific law.
	 */
	ASSOCIATION_DIRECTED("=?=>", Category.OTHER);

	/**
	 * Categorization based on ArchiMate semantic groups.
	 */
	public enum Category {
		/**
		 * Structural Relationships
		 * <p>
		 * These represent the static "bones" of your architecture. They describe how
		 * elements are composed or where they are assigned.
		 * </p>
		 */
		STRUCTURAL,
		/**
		 * Dependency Relationships
		 * <p>
		 * These describe how elements rely on one another, often showing the flow of
		 * value or requirements.
		 * </p>
		 */
		DEPENDENCY,
		/**
		 * Dynamic Relationships
		 * <p>
		 * These describe the temporal or causal "flow" of the architecture—essentially,
		 * what happens next.
		 * </p>
		 */
		DYNAMIC,
		/**
		 * Other Relationships
		 * <p>
		 * These are "catch-all" or specialized connectors that don't fit the rigid
		 * structural or dynamic categories.
		 * </p>
		 */
		OTHER
	}

	// Category of association
	private final Category category;

	// Fields representing the beginning and ending syntax for PlantUML
	private final String beginPlant;
	private final String endPlant;

	// default style
	private final ConnectionStyle defaultStyle;

	/**
	 * Constructor for the connection type enum. It splits the PlantUML syntax into
	 * beginning and ending parts.
	 *
	 * @param plant The complete PlantUML syntax for the connection type.
	 */
	ArchimateConnectionType(String plant, Category category) {
		int split = plant.indexOf('?');
		this.category = category;
		this.beginPlant = " " + plant.substring(0, split);
		this.endPlant = plant.substring(split + 1) + " ";
		this.defaultStyle = new ConnectionStyle(this);
	}

	/**
	 * gets the category of the connetion. E.g. for use like this:
	 * <p>
	 * {@code values().stream().filter(c -> c.getCategory() == Category.STRUCTURAL));}
	 * </p>
	 * 
	 * @return
	 */
	public Category getCategory() {
		return category;
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

	@Override
	public ConnectionStyle getDefaultStyle() {
		return defaultStyle;
	}
}
