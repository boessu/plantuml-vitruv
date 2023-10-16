package ch.braincell.plantuml.archimate;

import java.util.Set;
import java.util.TreeSet;

public class GroupConnection {

	public final GroupConnectionID connectionID;
	public final Set<String> labels = new TreeSet<>();
	public final Set<String> descriptions = new TreeSet<>();
	public final Set<Reference> references = new TreeSet<>();

	private final static int WRAP_DESCRIPTION = 25;

	GroupConnection(GroupConnectionID connectionID) {
		this.connectionID = connectionID;
	}

	void addConnection(Connection con) {
		if (con.label() != null)
			labels.add(con.label());
		if (con.references().length > 0)
			for (Reference r : con.references())
				references.add(r);
		if (con.description() != null && !con.description().isEmpty())
			descriptions.add(con.description());
	}

	String getPlant(RenderConfig config) {
		String result = connectionID.sender().ID + connectionID.style().getPlant() + connectionID.receiver().ID;

		// Labels: If there is no description and no references, the labels will be drawn always.
		result += config.connectionLabel() || (descriptions.size() == 0 && references.size() == 0)
				? ":" + String.join("\\n", labels) + "\n"
				: "\n";

		// description
		if ((references.size() > 0 && config.connectionTitle())
				|| ((descriptions.size() > 0) && config.connectionDescription())) {
			result += "note top on link\n";
			if (config.connectionTitle())
				result += getPlantTitles();
			if (config.connectionDescription())
				result += getPlantDescription();
			result += "end note\n";
		}

		return result;
	}

	private String getPlantDescription() {
		String result = "";
		if (descriptions.size() > 0) {
			for (String desc : descriptions)
				result += StringUtil.wrap(desc, WRAP_DESCRIPTION) + "\n";
		}
		return result;
	}

	private String getPlantTitles() {
		String result = "";
		if (references.size() > 0) {
			for (Reference ref : references) {
				result += ref.getPlant();
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		return connectionID.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return connectionID.equals(obj);
	}

	public record GroupConnectionID(Block sender, Block receiver, ConnectionStyle style) {
	}
}
