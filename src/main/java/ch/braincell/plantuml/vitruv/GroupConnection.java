package ch.braincell.plantuml.vitruv;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Manages connections between groups in a PlantUML diagram.
 * 
 * @author boessu
 */
public class GroupConnection {

	/** the ID of the grouped connections */
	public final GroupConnectionID connectionID;
	/** The labels on the connections in this group */
	public final Set<String> labels = new TreeSet<>();
	/** Descriptions on the connections in this group */
	public final Set<String> descriptions = new TreeSet<>();
	/** References on the connections in this group */
	public final Set<Reference> references = new TreeSet<>();

	private final static int WRAP_DESCRIPTION = 25;

	/**
	 * Constructs a new GroupConnection instance.
	 * 
	 * @param connectionID The unique identifier for this group connection.
	 */

	GroupConnection(GroupConnectionID connectionID) {
		this.connectionID = connectionID;
	}

	/**
	 * Adds a connection to the group connection.
	 * 
	 * @param con The connection to add.
	 */
	void addConnection(Connection con) {
		if (con.label() != null)
			labels.add(con.label());
        Collections.addAll(references, con.references());
		if (con.description() != null && !con.description().isEmpty())
			descriptions.add(con.description());
	}

	/**
	 * Generates the PlantUML representation of the group connection.
	 * 
	 * @param config The rendering configuration.
	 * @return The PlantUML string representation of the group connection.
	 */
	String getPlant(RenderConfig config) {
		String result = connectionID.sender().ID + connectionID.style().getPlant() + connectionID.receiver().ID;

		// Labels: If there is no description and no references, the labels will be
		// drawn always.
		result += config.connectionLabel() || (descriptions.isEmpty() && references.isEmpty())
				? ":" + String.join("\\n", labels) + "\n"
				: "\n";

		// description
		if ((!references.isEmpty() && config.connectionTitle())
				|| ((!descriptions.isEmpty()) && config.connectionDescription())) {
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
	 * Generates the PlantUML representation of the descriptions.
	 * 
	 * @return The PlantUML string representation of the wrapped descriptions.
	 */
	private String getPlantDescription() {
		StringBuilder result = new StringBuilder();
		if (!descriptions.isEmpty()) {
			for (String desc : descriptions)
				result.append(StringUtil.wrap(desc, WRAP_DESCRIPTION)).append("\n");
		}
		return result.toString();
	}

	/**
	 * Generates the PlantUML representation of the titles from references.
	 * 
	 * @return The PlantUML string representation of the titles.
	 */
	private String getPlantTitles() {
		StringBuilder result = new StringBuilder();
		if (!references.isEmpty()) {
			for (Reference ref : references) {
				result.append(ref.getPlant());
			}
		}
		return result.toString();
	}

	@Override
	public int hashCode() {
		return connectionID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return connectionID.equals(obj);
	}

	/**
	 * Represents a unique identifier for a group connection.
	 */
	public record GroupConnectionID(Block sender, Block receiver, ConnectionStyle style) {
	}
}
