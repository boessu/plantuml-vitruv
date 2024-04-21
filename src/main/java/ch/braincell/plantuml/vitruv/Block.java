package ch.braincell.plantuml.vitruv;

import java.net.URL;
import java.util.Set;

/**
 * A block is an object which can have connections to other blocks. A Block is
 * identified by it's ID, which will be provided at instantiation of a block. It
 * is possible to have two blocks with the same name, however the {@link Canvas}
 * only support unique names for {@link Leaf} and unique names for
 * {@link Group}. So it is possible to have a {@link Group} and a {@link Leaf}
 * with the same name but it is not possible to have two {@link Group} with the
 * same name or two {@link Leaf} with the same name. This is a tradeoff to
 * decouple the model of an external source, whereas objects should have unique
 * names and groups should have unique names.
 * 
 * @author boessu
 *
 */
public abstract class Block implements Comparable<Block> {
	/** Matching for the parameter user-ID. */
	public static final String USER_ID_MATCHING = "[A-Za-z0-9]{1,35}";
	/**
	 * The user-ID will be inserted at the end of the identifier and separated with
	 * this String.
	 */
	public static final String USER_ID_SEPARATOR = "__";

	private static int count = 0;

	// Block Identification
	public final String name;
	protected final String ID;

	// decoration
	public final URL url;
	public final Paragraph[] documentations;

	/**
	 * name is the ID of a block and so must be unique in the subclass.
	 * 
	 * @param name           name of the block
	 * @param userID         ID provided from outside to identify the block in the
	 *                       resulting plant source as element. It will be inserted
	 *                       at the end separated by
	 *                       {@link Block#USER_ID_SEPARATOR}. Only 35 alphanumeric
	 *                       chars without white spaces supported. It will be
	 *                       ignored, if it doesn't fulfill this rule. Can be null.
	 * @param url            URL for the block. Will be used in SVG to get a link.
	 *                       Can be null if unused.
	 * @param documentations Documentations for the block. Will be used for
	 *                       commenting the block.
	 */
	protected Block(String name, String userID, URL url, Paragraph... documentations) {
		super();
		this.name = name;
		this.url = url;
		this.documentations = documentations;
		if (userID == null || !userID.matches(USER_ID_MATCHING)) {
			ID = getShort() + count++;
		} else {
			ID = getShort() + count++ + USER_ID_SEPARATOR + userID;
		}
	}

	/**
	 * provided shortcut of the subclass. Used for building a unique ID for this
	 * Block.
	 * 
	 * @return shortcut for the ID.
	 */
	protected abstract String getShort();

	abstract String getPlant(RenderConfig config, Set<Block> focus);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	/**
	 * String used in comparison for sorting Blocks.
	 * 
	 * @return
	 */
	private String compareString() {
		return name + ID;
	}

	@Override
	public int compareTo(Block b) {
		return this.compareString().compareTo(b.compareString());
	}
}
