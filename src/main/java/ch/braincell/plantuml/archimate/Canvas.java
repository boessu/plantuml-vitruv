package ch.braincell.plantuml.archimate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.braincell.plantuml.archimate.style.Color;
import ch.braincell.plantuml.archimate.style.GroupStyle;

public class Canvas {

	private final Logger log = Logger.getLogger(Canvas.class.getCanonicalName());

	private static String PLANT_HEADER = null;
	private static final String PLANT_TAIL = "\n@enduml";

	// skin for the groups
	private Map<String, GroupStyle> groupSkins = new HashMap<>();

	// decoration
	public final String title;
	public final List<Paragraph> documentations = new ArrayList<>();

	// data management
	/** Connections between blocks */
	public final List<Connection> connections = new ArrayList<>();
	/** used for consolidated view on groups */
	public final Map<GroupConnection.GroupConnectionID, GroupConnection> groupConnections = new HashMap<>();
	public final Map<String, Leaf> leafs = new HashMap<>();
	public final Map<String, Group> groups = new HashMap<>();
	public final Map<String, Block> root = new HashMap<>();

	/**
	 * Create a canvas with optional documentation paragraphs. The documentation
	 * will be displayed as legend.
	 * 
	 * @param title          Title of the canvas.
	 * @param documentations paragraphs as documentation on the canvas.
	 */
	public Canvas(String title, Paragraph... documentations) {
		this.title = title;
		Stream.of(documentations).forEach(p -> addDocumentation(p));
		if (PLANT_HEADER == null) {
			// https://plantuml.com/de/archimate-diagram (sort of...)
			InputStream in = this.getClass().getResourceAsStream("Archimate.puml");
			String archimate = "";
			if (in != null)
				archimate = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
			else
				log.severe("Failed Archimate.puml loading!");
			PLANT_HEADER = "@startuml\n" + archimate + "\n";
		}
	}

	/**
	 * Add a paragraph to the documentation of the canvas. The paragraphs will be
	 * displayed as legend.
	 * 
	 * @param paragraph the paragraph to add.
	 */
	public void addDocumentation(Paragraph paragraph) {
		if (paragraph != null)
			this.documentations.add(paragraph);
	}

	/**
	 * Create a styled group for adding leafs with a specific highlighted style. If
	 * the group already exists with his name, the existing group will be returned
	 * (all attributes will be ignored, no change on the group).
	 * 
	 * @param name           Name of the group (mandatory, identification of the
	 *                       group)
	 * @param userID         specific ID for the user of the renderer which will be
	 *                       used to identify the leaf in the resulting Plantuml
	 *                       code, optional)
	 * @param url            URL of the group (optional)
	 * @param normalStyle    normal style of the group.
	 * @param highlightStyle highlighted style of the group.
	 * @param parentGroup    Parent group (optional)
	 * @param documentations documentation of the group with paragraphs (optional)
	 * @return New group if there is no group with that name.
	 */
	public Group addGroup(String name, String userID, URL url, GroupStyle normalStyle, GroupStyle highlightStyle,
			Group parentGroup, Paragraph... documentations) {
		if (normalStyle != null)
			groupSkins.put(normalStyle.stereotype(), normalStyle);
		if (highlightStyle != null)
			groupSkins.put(highlightStyle.stereotype(), highlightStyle);
		Group result = groups.get(name);
		if (result == null) {
			result = new Group(name, userID, url, normalStyle, highlightStyle, checkNull(documentations));
			addBlock(result, parentGroup);
		}

		return result;
	}

	/**
	 * Create a Leaf in a Group if it doesn't exist already.
	 * 
	 * @param name           Name of the leaf (Identifies the leaf, mandatory).
	 * @param userID         specific ID for the user of the renderer which will be
	 *                       used to identify the leaf in the resulting Plantuml
	 *                       code, optional)
	 * @param type           Type of Leaf (Archimate type of elements, mandatory).
	 * @param url            URL to further documentation for the leaf (optional)
	 * @param color          Color for the leaf (optional)
	 * @param highlightColor Highlighted color of the leaf (optional)
	 * @param parentGroup    Group where the leaf lives (optional)
	 * @param documentations paragraphs to the leaf as documentations (optional).
	 * @return new leaf if there is no leaf with the same name.
	 */
	public Leaf addLeaf(String name, String userID, LeafType type, URL url, Color color, Color highlightColor,
			Group parentGroup, Paragraph... documentations) {
		Leaf result = leafs.get(name);
		if (result == null) {
			result = new Leaf(name, userID, type, url, color, highlightColor, checkNull(documentations));
			addBlock(result, parentGroup);
		}

		return result;
	}

	/**
	 * Create a connection between leafs or groups.
	 * 
	 * @param sender      sender leaf or group (mandatory)
	 * @param receiver    receiver leaf or group (mandatory)
	 * @param style       style of the Connection (optional)
	 * @param label       label of the connection (mandatory)
	 * @param description additional description for the connection (optional)
	 * @param references  additional references to the description (optional)
	 * @return new connection between blocks.
	 */
	public Connection addConnection(Block sender, Block receiver, ConnectionStyle style, String label,
			String description, Reference... references) {
		Connection con = new Connection(sender, receiver, style, label, description, checkNull(references));
		connections.add(con);
		return con;
	}

	/**
	 * get an archimate compatible graphic in the PlantUML format.
	 * 
	 * @param config configuration of the drawing
	 * @param focus  blocks which will be drawn with focus
	 * @return
	 */
	public String getPlant(RenderConfig config, Set<Block> focus) {
		StringBuffer result = new StringBuffer(PLANT_HEADER);
		if (groupSkins.size() > 0) {
			for (GroupStyle skin : groupSkins.values()) {
				result.append(skin.toPlantCSS());
			}
		}
		result.append(config.canvasShadows() ? "" : "skinparam Shadowing false\n");
		result.append(config.canvasLeftToRight() ? "left to right direction\n" : "");

		result.append("title **" + title + "**\n");
		if (documentations.size() > 0) {
			result.append("legend left\n");
			Iterator<Paragraph> parit = documentations.iterator();
			while (parit.hasNext()) {
				result.append(parit.next().getPlant());
				if (parit.hasNext()) // add a newline if there is another paragraph
					result.append("\n");
			}
			result.append("end legend\n");
		}

		result.append(getRootPlant(config, focus));
		result.append(getConnectionsPlant(config));
		result.append(PLANT_TAIL);

		return result.toString();
	}

	/**
	 * Gets the blocks part of PlantUML.
	 * 
	 * @param config       Configuration of the drawing
	 * @param consolidated true if it should be drawed consolidated, false
	 *                     otherwise.
	 * @param focus        Elements or blocks in focus of this drawing.
	 * @return
	 */
	private String getRootPlant(RenderConfig config, Set<Block> focus) {
		StringBuffer result = new StringBuffer();
		TreeMap<Integer, StringBuffer> map = new TreeMap<>();
		String temp;
		for (Block block : root.values()) {
			temp = block.getPlant(config, focus);
			if (block instanceof Leaf) {
				// single elements will be added at the beginning.
				if (config.show(block)) // filter leafs
					result.append(temp);
			} else {
				// sort groups according to its size
				int count = ((Group) block).countLeaf(config);
				if (count >= 1) {
					if (map.get(count) == null)
						map.put(count, new StringBuffer());
					map.get(count).append(temp);
				}
			}
		}

		// the groups should be added in descending order (biggest first).
		map.descendingMap().forEach((k, u) -> result.append(u));

		return result.toString();
	}

	/**
	 * Draws all connections between blocks
	 * 
	 * @param config
	 * @param consolidated
	 * @return
	 */
	private String getConnectionsPlant(RenderConfig config) {
		if (connections.size() == 0)
			return "";

		StringBuffer result = new StringBuffer();

		if (config.showConsolidated() && config.showGroups()) {
			groupConnections.clear();
			connections.stream().filter(c -> config.show(c.sender()) && config.show(c.receiver()))
					.forEach(c -> addGroupConnection(c)); // leaf filter.
			groupConnections.values().stream().forEach(gcon -> result.append(gcon.getPlant(config)));
		} else {
			connections.stream().filter(c -> config.show(c.sender()) && config.show(c.receiver()))
					.forEach(con -> result.append(con.getPlant(config))); // leaf filter
		}

		return result.toString();
	}

	private GroupConnection addGroupConnection(Connection con) {
		Block sender = getConsolidatedBlock(con.sender());
		Block receiver = getConsolidatedBlock(con.receiver());

		GroupConnection.GroupConnectionID key = new GroupConnection.GroupConnectionID(sender, receiver, con.style());
		GroupConnection result = groupConnections.get(key);
		if (result == null) {
			result = new GroupConnection(key);
			groupConnections.put(key, result);
		}

		result.addConnection(con);
		return result;
	}

	private Block getConsolidatedBlock(Block block) {
		if (block instanceof Leaf leaf && (root.get(leaf.name) == null || root.get(leaf.name) instanceof Group)) {
			return groups.values().stream().filter(g -> g.contains(leaf)).findFirst().get();
		}

		return block;
	}

	private void addBlock(Block block, Group parentGroup) {
		if (block instanceof Leaf leaf)
			leafs.put(block.name, leaf);
		else
			groups.put(block.name, (Group) block);

		if (parentGroup == null) {
			root.put(block.name, block);
		} else {
			parentGroup.addBlock(block);
		}
	}

	private static Paragraph[] checkNull(Paragraph[] array) {
		if (array.length > 0) {
			if (array[0] == null)
				return new Paragraph[0];
		}
		return array;
	}

	private static Reference[] checkNull(Reference[] array) {
		if (array.length > 0) {
			if (array[0] == null)
				return new Reference[0];
		}
		return array;
	}
}
