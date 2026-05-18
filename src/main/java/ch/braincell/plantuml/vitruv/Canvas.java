package ch.braincell.plantuml.vitruv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.braincell.plantuml.vitruv.style.Color;
import ch.braincell.plantuml.vitruv.style.ElementStyle;
import ch.braincell.plantuml.vitruv.style.Style;

/**
 * The {@code Canvas} class represents a canvas in the PlantUML Archimate / Edgy
 * diagramming tool. It manages the creation and arrangement of various diagram
 * elements such as groups, leafs, and connections. The canvas also handles the
 * generation of PlantUML source code based on the added elements and their
 * styles.
 * 
 * @author boessu
 */
public class Canvas {
	private final Logger log = Logger.getLogger(Canvas.class.getCanonicalName());

	private static final String PLANT_TAIL = "\n@enduml";
	private static String PLANT_HEADER = null;
	/**
	 * The title of this canvas.
	 */
	public final String title;
	/**
	 * the documentations of this canvas.
	 */
	public final List<Paragraph> documentations = new ArrayList<>();
	/**
	 * Connections between blocks
	 */
	public final List<Connection> connections = new ArrayList<>();

	// decoration
	/**
	 * used for consolidated view on groups
	 */
	public final Map<GroupConnection.GroupConnectionID, GroupConnection> groupConnections = new HashMap<>();
	/**
	 * Leafs within the canvas
	 */
	public final Map<String, Leaf> leafs = new HashMap<>();

	// data management
	/**
	 * Groups within the canvas
	 */
	public final Map<String, Group> groups = new HashMap<>();
	/**
	 * Root blocks within the canvas
	 */
	public final Map<String, Block> root = new HashMap<>();

	// needed imports at the beginning of a plantuml source.
	private final Set<String> imports;

	private final Set<String> sprites;

	// style for elements and groups
	private final Map<String, Set<Style.StyleSheet>> styleSkins = new HashMap<>();

	/**
	 * Create a canvas with optional documentation paragraphs. The documentation
	 * will be displayed as legend.
	 *
	 * @param title          Title of the canvas (mandatory)
	 * @param documentations paragraphs as documentation on the canvas (optional)
	 */
	public Canvas(String title, Paragraph... documentations) {
		this.imports = new HashSet<>();
		this.sprites = new HashSet<>();
		this.title = title;
		Stream.of(documentations).forEach(this::addDocumentation);
		if (PLANT_HEADER == null) {
			InputStream in = this.getClass().getResourceAsStream("header.puml");
			String header = "";
			if (in != null) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
					header = reader.lines().collect(Collectors.joining("\n"));
				} catch (IOException e) {
					log.log(Level.SEVERE, "Failed header.puml loading!", e);
				}
				PLANT_HEADER = header + "\n";
			}
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

	/**
	 * Add a paragraph to the documentation of the canvas. The paragraphs will be
	 * displayed as legend.
	 *
	 * @param paragraph the paragraph to add to the documentation (mandatory)
	 */
	public void addDocumentation(Paragraph paragraph) {
		if (paragraph != null)
			this.documentations.add(paragraph);
	}

	/**
	 * Add a styled group for adding leafs with a specific highlighted style. If the
	 * group already exists with his name, the existing group will be returned (all
	 * attributes will be ignored, no change on the group).
	 *
	 * @param name           the name of the group (mandatory, identification of the
	 *                       group)
	 * @param userID         a specific ID for the user of the renderer which will
	 *                       be used to identify the leaf in the resulting Plantuml
	 *                       code, (optional)
	 * @param url            the URL of the group (optional)
	 * @param normalStyle    the normal style of the group. (optional, takes a
	 *                       standard style if there is no one)
	 * @param highlightStyle the highlighted style of the group. (optional)
	 * @param parentGroup    the Parent group (optional)
	 * @param documentations the documentation of the group with paragraphs
	 *                       (optional)
	 * @return the new group or existing group if there is already a group with that
	 *         name.
	 */
	public Group addGroup(String name, String userID, Block.Link url, ElementStyle normalStyle,
			ElementStyle highlightStyle, Group parentGroup, Paragraph... documentations) {
		addStyleSheet(highlightStyle);
		addStyleSheet(normalStyle);
		Group result = groups.get(name);
		if (result == null) {
			result = new Group(name, userID, url, normalStyle, highlightStyle, checkNull(documentations));
			addBlock(result, parentGroup);
		}

		return result;
	}

	/**
	 * Add a simple styled group for adding leafs. If the group already exists with
	 * his name, the existing group will be returned (all attributes will be
	 * ignored, no change on the group).
	 *
	 * @param name        the name of the group (mandatory, identification of the
	 *                    group)
	 * @param normalStyle the normal style of the group. (optional, takes a standard
	 *                    style if there is no one)
	 * @return the new group or existing group if there is already a group with that
	 *         name.
	 */
	public Group addGroup(String name, ElementStyle normalStyle) {
		return addGroup(name, name, null, normalStyle, normalStyle, null);
	}

	private void addStyleSheet(ElementStyle style) {
		Style.StyleSheet styleSheet;
		if (style != null && (styleSheet = style.getPlantCSS()) != null) {
			if (style.getSpriteHeader() != null)
				sprites.add(style.getSpriteHeader());
			if (styleSkins.get(style.getPlantCSS().element()) == null)
				styleSkins.put(style.getPlantCSS().element(), new HashSet<>());
			styleSkins.get(style.getPlantCSS().element()).add(styleSheet);
		}
	}

	/**
	 * Create a Leaf in a Group if it doesn't exist already.
	 *
	 * @param name           the name of the leaf (Identifies the leaf, mandatory).
	 * @param userID         a specific ID for the user of the renderer which will
	 *                       be used to identify the leaf in the resulting Plantuml
	 *                       code, (optional)
	 * @param url            the URL to further documentation for the leaf
	 *                       (optional)
	 * @param style          the style of Leaf (optional).
	 * @param color          the color for the leaf (optional)
	 * @param highlightColor the highlighted color of the leaf (optional)
	 * @param parentGroup    the group where the leaf lives (optional)
	 * @param documentations the documentation paragraphs for the leaf (optional).
	 * @return new leaf or existing leaf if there is already a leaf with the same
	 *         name.
	 */
	public Leaf addLeaf(String name, String userID, Block.Link url, ElementStyle style, Color color,
			Color highlightColor, Group parentGroup, Paragraph... documentations) {
		Leaf result = leafs.get(name);
		if (result == null) {
			if (style == null)
				style = CustomElementStyle.standardStyle;
			addStyleSheet(style);
			result = new Leaf(name, userID, style, url, color, highlightColor, checkNull(documentations));
			addBlock(result, parentGroup);
			if (style.getImport() != null)
				imports.add(style.getImport());
		}

		return result;
	}

	/**
	 * Create a simple Leaf in a Group if it doesn't exist already.
	 *
	 * @param name        the name of the leaf (Identifies the leaf, mandatory).
	 * @param style       the style of Leaf (optional).
	 * @param parentGroup the group where the leaf lives (optional)
	 * @return new leaf or existing leaf if there is already a leaf with the same
	 *         name.
	 */
	public Leaf addLeaf(String name, ElementStyle style, Group parentGroup) {
		return addLeaf(name, name, null, style, null, null, parentGroup);
	}

	/**
	 * Create a connection between leafs or groups with a specific style.
	 *
	 * @param sender      the sender leaf or group (mandatory)
	 * @param receiver    the receiver leaf or group (mandatory)
	 * @param style       the style of the Connection (mandatory)
	 * @param label       the label of the connection (mandatory)
	 * @param description an additional description for the connection (optional)
	 * @param references  additional references to the description (optional)
	 * @return the new connection between blocks.
	 */
	public Connection addConnection(Block sender, Block receiver, ConnectionStyle style, String label,
			String description, Reference... references) {
		Connection con = new Connection(sender, receiver, style, label, description, checkNull(references));
		connections.add(con);
		return con;
	}

	/**
	 * Create a simple connection between leafs or groups with a specific style.
	 *
	 * @param sender   the sender leaf or group (mandatory)
	 * @param receiver the receiver leaf or group (mandatory)
	 * @param style    the style of the Connection (mandatory)
	 * @param label    the label of the connection (mandatory)
	 * @return the new connection between blocks.
	 */
	public Connection addConnection(Block sender, Block receiver, ConnectionStyle style, String label) {
		return addConnection(sender, receiver, style, label, null);
	}

	/**
	 * Create a connection between leafs or groups, using the default style of the
	 * connection type.
	 *
	 * @param sender      the sender leaf or group (mandatory)
	 * @param receiver    the receiver leaf or group (mandatory)
	 * @param type        the type of the Connection (mandatory)
	 * @param label       the label of the connection (mandatory)
	 * @param description an additional description for the connection (optional)
	 * @param references  additional references to the description (optional)
	 * @return the new connection between blocks.
	 */
	public Connection addConnection(Block sender, Block receiver, ConnectionType type, String label, String description,
			Reference... references) {
		return addConnection(sender, receiver, type.getDefaultStyle(), label, description, references);
	}

	/**
	 * Create a simple connection between leafs or groups, using the default style
	 * of the connection type.
	 *
	 * @param sender      the sender leaf or group (mandatory)
	 * @param receiver    the receiver leaf or group (mandatory)
	 * @param type        the type of the Connection (mandatory)
	 * @param label       the label of the connection (mandatory)
	 * @return the new connection between blocks.
	 */
	public Connection addConnection(Block sender, Block receiver, ConnectionType type, String label) {
		return addConnection(sender, receiver, type.getDefaultStyle(), label, null);
	}

	/**
	 * Generates the PlantUML source code for the current state of the canvas.
	 *
	 * @param config the configuration of the drawing (mandatory)
	 * @param focus  the blocks which will be drawn with focus (optional)
	 * @return the generated plant source code.
	 */
	public String getPlant(RenderConfig config, Set<Block> focus) {
		focus = focus == null ? Set.of() : focus;
		StringBuilder result = new StringBuilder("@startuml\n");
		// PlantUML Imports
		imports.forEach(importing -> result.append(importing).append('\n'));
		if (PLANT_HEADER != null)
			result.append(PLANT_HEADER);
		sprites.forEach(sprite -> result.append(sprite).append('\n'));
		// If there are custom skins for custom styles: enrich them here.
		if (!styleSkins.isEmpty()) {
			result.append("<style>\n");
			for (Map.Entry<String, Set<Style.StyleSheet>> skin : styleSkins.entrySet()) {
				result.append(' ').append(skin.getKey()).append(" {\n");
				skin.getValue().forEach(ss -> result.append(ss.getStereoType()));
				result.append(" }\n");
			}
			result.append("</style>\n");
		}
		result.append(config.canvasShadows() ? "" : "skinparam Shadowing false\n");
		result.append(config.canvasLeftToRight() ? "left to right direction\n" : "");

		// TItle and legend for the canwas.
		result.append("title **").append(title).append("**\n");
		if (!documentations.isEmpty()) {
			result.append("legend left\n");
			Iterator<Paragraph> parit = documentations.iterator();
			while (parit.hasNext()) {
				result.append(parit.next().getPlant());
				if (parit.hasNext()) // add a newline if there is another paragraph
					result.append("\n");
			}
			result.append("end legend\n");
		}

		// Add element definitions.
		result.append(getRootPlant(config, focus));
		// Add connections between elements
		result.append(getConnectionsPlant(config));
		result.append(PLANT_TAIL);

		return result.toString();
	}

	/**
	 * Generates the PlantUML source code for the current state of the canvas.
	 *
	 * @param config the configuration of the drawing (mandatory)
	 * @return the generated plant source code.
	 */
	public String getPlant(RenderConfig config) {
		return getPlant(config, null);
	}

	/**
	 * Gets the blocks part of PlantUML.
	 *
	 * @param config the configuration of the drawing
	 * @param focus  the elements or blocks in focus of this drawing
	 * @return the root header PlantUML sourcecode.
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
					map.computeIfAbsent(count, k -> new StringBuffer());
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
	 * @param config Configuration of the rendering
	 * @return A string representing the plant of the connections, taking into
	 *         account whether consolidate or group connections should be shown.
	 */
	private String getConnectionsPlant(RenderConfig config) {
		if (connections.isEmpty())
			return "";

		StringBuffer result = new StringBuffer();

		if (config.showConsolidated() && config.showGroups()) {
			groupConnections.clear();
			connections.stream().filter(c -> config.show(c.sender()) && config.show(c.receiver()))
					.forEach(this::addGroupConnection); // leaf filter.
			groupConnections.values().forEach(gcon -> result.append(gcon.getPlant(config)));
		} else {
			connections.stream().filter(c -> config.show(c.sender()) && config.show(c.receiver()))
					.forEach(con -> result.append(con.getPlant(config))); // leaf filter
		}

		return result.toString();
	}

	/**
	 * Groups multiple connections between the same pair of blocks into a single
	 * {@code GroupConnection}.
	 *
	 * @param con the connection to consolidate
	 * @return the resulting group connection
	 */
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

	/**
	 * Returns the parent group of a leaf if it is contained within one; otherwise
	 * returns the block itself. Used for connection consolidation.
	 *
	 * @param block the block to check
	 * @return the consolidated block (the group or the leaf)
	 */
	private Block getConsolidatedBlock(Block block) {
		if (block instanceof Leaf leaf && (root.get(leaf.name) == null || root.get(leaf.name) instanceof Group)) {
			return groups.values().stream().filter(g -> g.contains(leaf)).findFirst().get();
		}

		return block;
	}

	/**
	 * Registers a block in the canvas's internal maps and attaches it to its parent
	 * group if provided.
	 *
	 * @param block       the block to add (Leaf or Group)
	 * @param parentGroup the parent group to attach the block to, or null if it is
	 *                    root
	 */
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
}
