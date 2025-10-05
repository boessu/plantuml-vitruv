package ch.braincell.plantuml.vitruv;

import java.util.Set;
import java.util.TreeSet;

import ch.braincell.plantuml.vitruv.style.ElementStyle;

/**
 * Represents a group of blocks in a PlantUML diagram, which can be styled and
 * documented.
 */
public class Group extends Block {

	private final Set<Block> blocks = new TreeSet<>();
	private final ElementStyle normalStyle;
	private final ElementStyle highlightStyle;

	/**
	 * Constructs a new Group instance.
	 * 
	 * @param name           The name of the group.
	 * @param userID         The user ID associated with the group.
	 * @param url            The URL for documentation or reference.
	 * @param normalStyle    The normal style for the group.
	 * @param highlightStyle The highlight style for the group.
	 * @param documentations Varargs parameter for documentation paragraphs.
	 */

	Group(String name, String userID, Link url, ElementStyle normalStyle, ElementStyle highlightStyle,
			Paragraph... documentations) {
		super(name, userID, url, documentations);
		if (normalStyle != null)
			this.normalStyle = normalStyle;
		else
			this.normalStyle = CustomStyle.standardStyle;
		this.highlightStyle = highlightStyle;
	}

	/**
	 * Adds a block to the group.
	 * 
	 * @param block The block to add.
	 */
	void addBlock(Block block) {
		blocks.add(block);
	}

	/**
	 * Checks if a block is contained within the group.
	 * 
	 * @param block The block to check.
	 * @return True if the block is contained within the group, false otherwise.
	 */
	boolean contains(Block block) {
		return blocks.contains(block);
	}

	/**
	 * Provides a short string representation of the group.
	 * 
	 * @return A string that represents the group.
	 */
	@Override
	protected String getShort() {
		return "group";
	}

	/**
	 * Generates the PlantUML representation of the group.
	 * 
	 * @param config The rendering configuration.
	 * @param focus  The set of blocks that are in focus.
	 * @return The PlantUML string representation of the group.
	 */
	@Override
	String getPlant(RenderConfig config, Set<Block> focus) {
		StringBuilder drawLeafs = new StringBuilder(
				config.showConsolidated() && config.showGroups() ? "  file " + ID + " as \"\n" : "");
		StringBuilder drawGroups = new StringBuilder();
		for (Block block : blocks) {
			if (config.show(block)) {
				if (config.showConsolidated() && config.showGroups()) {
					if (block instanceof Leaf leaf) {
						boolean hasFocus = focus.contains(block);
						String bold = hasFocus ? "**" : "";
						String focusColor = leaf.getColor(hasFocus);
						String name = leaf.name + " (" + leaf.leafStyle.getStereotype() + ")";
						if (leaf.link != null) {
							name = "[[" + leaf.link.url() + " " + name + "]]";
						}
						drawLeafs.append("    <").append(focusColor).append(",#white>|").append(bold).append(name)
								.append(bold).append("|\n");
					} else {
						drawGroups.append(block.getPlant(config, focus));
					}
				} else {
					drawLeafs.append(block.getPlant(config, focus));
				}
			}
		}

		drawLeafs.append(config.showConsolidated() && config.showGroups() ? "  \"\n" : "");

		StringBuilder result;

		if (config.showGroups()) {
			String CID = config.showConsolidated() ? "c" + ID : ID;
			boolean highlight = focus.contains(this);

			if (highlight && highlightStyle != null)
				result = new StringBuilder(
						highlightStyle.getPlant(name, CID, link, null, true, drawLeafs + drawGroups.toString()));
			else
				result = new StringBuilder(
						normalStyle.getPlant(name, CID, link, null, false, drawLeafs + drawGroups.toString()));
			if (config.groupDocumentation() && documentations.length > 0) {
				result.append("note top of ").append(CID).append("\n");
				for (Paragraph doc : documentations) {
					result.append(doc.getPlant());
				}
				result.append("end note\n");
			}
		} else {
			result = new StringBuilder(drawLeafs + drawGroups.toString());
		}

		return result.toString();
	}

	/**
	 * Counts the number of leaf blocks within the group.
	 * 
	 * @param config The rendering configuration.
	 * @return The number of leaf blocks.
	 */
	int countLeaf(RenderConfig config) {
		int i = 0;
		for (Block b : blocks) {
			if (config.show(b)) { // filter leaf
				if (b instanceof Leaf) {
					i++;
				} else {
					i += ((Group) b).countLeaf(config);
				}
			}
		}
		return i;
	}
}
