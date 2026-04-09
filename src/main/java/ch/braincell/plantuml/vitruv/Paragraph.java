package ch.braincell.plantuml.vitruv;

import java.util.Collection;

/**
 * Creates a paragraph for documentations. It is possible to use Creole in the
 * text.
 * 
 * @param title         The title of the paragraph.
 * @param documentation The text content of the paragraph.
 * @param wrap          If true, wraps the text to a specified character length.
 * @param references    An array of {@link Reference} objects for URL links
 *                      related to the paragraph. Will be added at the end of
 *                      the paragraph.
 * 
 * @author boessu
 */
public record Paragraph(String title, String documentation, boolean wrap, Reference... references) {

	private static final int WRAP_DOCUMENTATION = 80;

	/**
	 * Constructor for {@code Paragraph} that replaces single quotes in the
	 * documentation with double quotes to prevent them from being interpreted as
	 * comments in PlantUML.
	 * 
	 * @param title         The title of the paragraph.
	 * @param documentation The text content of the paragraph.
	 * @param wrap          If true, wraps the text to a specified character length.
	 * @param references    An array of {@link Reference} objects for URL links
	 *                      related to the paragraph.
	 */
	public Paragraph {
		// this is usually build as a block text. If this includes single quotes at the
		// beginning of the line, the text will be missing as it is interpreted as
		// "comment" in PlantUML. So we'll replace single quotes here with double
		// quotes.
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

	/**
	 * Generates the PlantUML representation of the paragraph, including the title,
	 * wrapped documentation, and any associated references formatted as URL links.
	 * 
	 * @return A string representing the PlantUML formatted paragraph.
	 */
	String getPlant() {
		StringBuilder result = new StringBuilder();
		if (title != null && !title.isEmpty()) {
			result.append("**").append(title).append("**\n");
		}
		if (wrap && documentation != null && !documentation.isEmpty()) {
			result.append(StringUtil.wrap(documentation, WRAP_DOCUMENTATION)).append("\n");
		} else if (documentation != null) {
			result.append(documentation);
		}
		if (references != null) {
			for (Reference ref : references) {
				result.append(ref.getPlant());
			}
		}

		return result.toString();
	}
}
