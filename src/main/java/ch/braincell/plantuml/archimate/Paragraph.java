package ch.braincell.plantuml.archimate;

import java.util.Collection;

/**
 * Creates a paragraph for documentations. It is possible to use Creole in the
 * text.
 * 
 * @param title         title of the paragraph.
 * @param documentation text of the paragraph.
 * @param wrap          true wraps the text to 80 characters length.
 * @param references    References as URL links for the paragraph. Will be added
 *                      at the end.
 */
public record Paragraph(String title, String documentation, boolean wrap, Reference... references) {

	private static final int WRAP_DOCUMENTATION = 80;

	public Paragraph {
		// this is usually build as a block text. If this includes single quotes, the
		// text will be missing. So we'll replace single quotes here.
		documentation = StringUtil.replaceSingleQuotes(documentation);
	}

	/**
	 * Creates a paragraph for documentations. It is possible to use Creole in the
	 * text. the text will be wrapped (constructor is for convenience)
	 * 
	 * @param title         title of the paragraph.
	 * @param documentation text of the paragraph.
	 * @param references    References as URL links for the paragraph. Will be added
	 *                      at the end.
	 */
	public Paragraph(String title, String documentation, Collection<Reference> references) {
		this(title, documentation, true, references.toArray(new Reference[0]));
	}

	/**
	 * Creates a paragraph for documentations. It is possible to use Creole in the
	 * text. the text will be wrapped (constructor is for convenience)
	 * 
	 * @param title         title of the paragraph.
	 * @param documentation text of the paragraph.
	 * @param references    References as URL links for the paragraph. Will be added
	 *                      at the end.
	 */
	public Paragraph(String title, String documentation, Reference... references) {
		this(title, documentation, true, references);
	}

	String getPlant() {
		String result = "";
		if (title != null && !title.isEmpty()) {
			result += "**" + title + "**\n";
		}
		if (wrap && documentation != null && !documentation.isEmpty()) {
			result += StringUtil.wrap(documentation, WRAP_DOCUMENTATION) + "\n";
		} else if (documentation != null) {
			result += documentation;
		}
		if (references != null && references.length > 0) {
			for (Reference ref : references) {
				result += ref.getPlant();
			}
		}

		return result;
	}
}
