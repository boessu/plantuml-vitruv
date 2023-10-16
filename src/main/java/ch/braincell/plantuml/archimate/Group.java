package ch.braincell.plantuml.archimate;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

import ch.braincell.plantuml.archimate.style.GroupStyle;

public class Group extends Block {

	private Set<Block> blocks = new TreeSet<>();
	private final GroupStyle normalStyle;
	private final GroupStyle highlightStyle;

	Group(String name, String userID, URL url, GroupStyle normalStyle, GroupStyle highlightStyle, Paragraph... documentations) {
		super(name, userID, url, documentations);
		this.normalStyle = normalStyle;
		this.highlightStyle = highlightStyle;
	}

	void addBlock(Block block) {
		blocks.add(block);
	}

	boolean contains(Block block) {
		return blocks.contains(block);
	}

	@Override
	protected String getShort() {
		return "group";
	}

	@Override
	String getPlant(RenderConfig config, Set<Block> focus) {
		String drawLeafs = config.showConsolidated() && config.showGroups() ? "  file " + ID + " as \"\n" : "";
		String drawGroups = "";
		for (Block block : blocks) {
			if (config.show(block)) {
				if (config.showConsolidated() && config.showGroups()) {
					if (block instanceof Leaf leaf) {
						boolean hasFocus = focus.contains(block);
						String bold = hasFocus ? "**" : "";
						String focusColor = leaf.getColor(hasFocus);
						String name = leaf.name + " (" + leaf.leafType.getStereotype() + ")";
						if (leaf.url != null) {
							name = "[[" + leaf.url.toString() + " " + name + "]]";
						}
						drawLeafs += "    <" + focusColor + ",#white>|" + bold + name + bold + "|\n";
					} else {
						drawGroups += block.getPlant(config, focus);
					}
				} else {
					drawLeafs += block.getPlant(config, focus);
				}
			}
		}

		drawLeafs += config.showConsolidated() && config.showGroups() ? "  \"\n" : "";

		String result;

		if (config.showGroups()) {
			String CID = config.showConsolidated() ? "c" + ID : ID;
			boolean highlight = focus.contains(this);
			result = name;
			if (url != null)
				result = "[[" + url.toString() + " " + result + "]]";

			// fiddle out stereotype
			String stereotype = "";
			if (normalStyle != null) {
				if (highlight && highlightStyle != null)
					stereotype = highlightStyle.stereotype();
				else
					stereotype = normalStyle.stereotype();
				stereotype = "<<" + stereotype + ">>";
			}

			// TODO: it is possible that either normal or highlight style or both are null.
			// In that case, we have 3 options here:
			// rectangle <<boundary>>
			// folder <<grouping>>
			// folder <<group>>
			result = "rectangle \"" + result + "\" as " + CID + " " + stereotype + " {\n";
			result += drawLeafs + drawGroups;
			result += "}\n";

			if (config.groupDocumentation() && documentations.length > 0) {
				result += "note top of " + CID + "\n";
				for (Paragraph doc : documentations) {
					result += doc.getPlant();
				}
				result += "end note\n";
			}
		} else {
			result = drawLeafs + drawGroups;
		}

		return result;
	}

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
