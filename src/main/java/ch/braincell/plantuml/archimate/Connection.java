package ch.braincell.plantuml.archimate;

public record Connection(Block sender, Block receiver, ConnectionStyle style, String label, String description,
		Reference[] references) {

	private final static int WRAP_DESCRIPTION = 45;

	public Connection(Block sender, Block receiver, ConnectionStyle style, String label, String description,
			Reference[] references) {
		this.sender = sender;
		this.receiver = receiver;
		this.style = style;
		this.label = label.replace("\n", "\\n");
		this.description = description;
		this.references = references;
	}

	String getPlant(RenderConfig config) {
		String result = sender.ID + style.getPlant() + receiver.ID;
		// Label: If there is no description and no references, the label will be drawn always.
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

	private String getPlantTitles() {
		String result = "";
		if (references.length > 0) {
			for (Reference ref : references) {
				result += ref.getPlant();
			}
		}
		return result;
	}

	private String getPlantDescription() {
		String result = "";
		if (description != null && !description.isEmpty()) {
			result += StringUtil.wrap(description, WRAP_DESCRIPTION) + "\n";
		}
		return result;
	}

}
