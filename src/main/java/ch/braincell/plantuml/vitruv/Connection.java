package ch.braincell.plantuml.vitruv;

/**
 * Represents a connection between two blocks in a PlantUML diagram.
 * 
 * @param sender      The starting block of the connection.
 * @param receiver    The ending block of the connection.
 * @param style       The style of the connection line.
 * @param label       The label of the connection.
 * @param description A description of the connection.
 * @param references  An array of references associated with the connection.
 * 
 * @author boessu
 */
public record Connection(Block sender, Block receiver, ConnectionStyle style, String label, String description,
		Reference[] references) {

	private final static int WRAP_DESCRIPTION = 45;

	/**
	 * Constructs a new Connection instance.
	 * 
	 * @param sender      The starting block of the connection.
	 * @param receiver    The ending block of the connection.
	 * @param style       The style of the connection line.
	 * @param label       The label of the connection, with newline characters
	 *                    replaced by literal "\n".
	 * @param description A description of the connection.
	 * @param references  An array of references associated with the connection.
	 */
	public Connection(Block sender, Block receiver, ConnectionStyle style, String label, String description,
			Reference[] references) {
		this.sender = sender;
		this.receiver = receiver;
		this.style = style;
		this.label = label != null ? label.replace("\n", "\\n") : null;
		this.description = description;
		this.references = references;
	}

	/**
	 * Generates the PlantUML representation of the connection.
	 * 
	 * @param config The rendering configuration.
	 * @return The PlantUML string representation of the connection.
	 */
	String getPlant(RenderConfig config) {
		String result = sender.ID + style.getPlant() + receiver.ID;
		// Label: If there is no description and no references, the label will be drawn
		// always.
		if (config.connectionLabel() || ((description == null || description.isEmpty()) && references.length == 0))
			result += label == null ? "\n" : ": " + label + "\n";
		else
			result += "\n";

		// description
		if ((references.length > 0 && config.connectionTitle())
				|| ((description != null && !description.isEmpty()) && config.connectionDescription())) {
			result += "note top on link\n";
			if (config.connectionTitle())
				result += getPlantTitles();
			if (config.connectionDescription())
				result += getPlantDescription();
			result += "end note\n";
		}

		return result;
	}

	/**
	 * Generates the PlantUML representation of the titles from references.
	 * 
	 * @return The PlantUML string representation of the titles.
	 */
	private String getPlantTitles() {
		StringBuilder result = new StringBuilder();
        for (Reference ref : references) {
            result.append(ref.getPlant());
        }
        return result.toString();
	}

	/**
	 * Generates the PlantUML representation of the description.
	 * 
	 * @return The PlantUML string representation of the wrapped description.
	 */
	private String getPlantDescription() {
		String result = "";
		if (description != null && !description.isEmpty()) {
			result += StringUtil.wrap(description, WRAP_DESCRIPTION) + "\n";
		}
		return result;
	}

}
