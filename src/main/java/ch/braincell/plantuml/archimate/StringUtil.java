package ch.braincell.plantuml.archimate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class StringUtil {

	private final static Pattern SKIPPING_LISTS = Pattern.compile("^\\s*[*#=]+[\\s].*"); // for skipping lists (can't be
																							// wrapped) Lists
	// need at least a space to be before text to be
	// recognized.
	private final static String[] TOKEN = { "**", "//", "\"\"", "--", "__", "~~" };

	private final static String LINK_OPEN = "[[";
	private final static String LINK_CLOSE = "]]";

	/**
	 * Wraps a String with specific length and some sort of creole sensitivity from
	 * plantuml. The wrapping character is a {@code \n} (newline).
	 * 
	 * @param input
	 * @param wrapLength
	 * @return
	 */
	public static String wrap(String input, int wrapLength) {
		return wrap(input, wrapLength, "\n");
	}

	/**
	 * Wraps a String with specific length and some sort of creole sensitivity from
	 * plantuml. This wrapper can't fix curious scenarios with overlapping creole
	 * syntax (plantuml can't handle that too). TODO: Looks rather ugly (but works
	 * as expected). Should be better optimized.
	 * 
	 * @param input      input string to word wrap
	 * @param wrapLength length to wrap the lines. the longest word will overwrite
	 *                   this value.
	 * @param newLine    new line string to use for end of line (e.g.
	 *                   {@code "\n", "</br>"} etc.)
	 * @return text wrapped with newLine.
	 */
	public static String wrap(String input, int wrapLength, String newLine) {
		if (input == null || input.isEmpty())
			return null;

		StringBuffer result = new StringBuffer(input.length() + 32);

		wrapLength = getMaxWordLength(input, wrapLength, 50);

		String[] splitter = input.split("\\r\\n|\\n\\r|\\r|\\n");

		boolean nextLine = false;
		for (String line : splitter) {
			if (nextLine || line.isEmpty()) {
				trimEnd(result);
				result.append(newLine);
			}
			if (line.length() <= wrapLength || SKIPPING_LISTS.matcher(line).matches()) {
				result.append(line);
			} else {
				int lineLength = 0;
				int creoleLength = 0;
				StringTokenizer toktok = new StringTokenizer(line, " \t\f", false);
				List<String> formats = new ArrayList<>(TOKEN.length);
				List<String> toRemove = new ArrayList<>(TOKEN.length);
				List<String> toAdd = new ArrayList<>(TOKEN.length);
				boolean openLink = false;
				while (toktok.hasMoreTokens()) {
					String token = toktok.nextToken();
					if (token.startsWith(LINK_OPEN)) {
						openLink = true;
					}

					if (openLink) {
						result.append(token);
						lineLength += token.length();
						if (token.endsWith(LINK_CLOSE))
							openLink = false;
						else {
							result.append(' ');
							lineLength++;
						}
					} else {
						// testing creole token
						for (String creoleToken : TOKEN) {
							int creoleCount = countFindings(token, creoleToken);
							if (creoleCount > 0) {
								creoleLength += creoleCount * creoleToken.length();
								if (creoleCount % 2 != 0) {
									// creole token is open
									if (formats.contains(creoleToken)) {
										toRemove.add(creoleToken);
									} else {
										toAdd.add(creoleToken);
									}
								}
							}
						}

						if ((lineLength + token.length() - creoleLength) < wrapLength) {
							result.append(token).append(' ');
							lineLength += token.length() + 1;
							formats.addAll(toAdd);
							toAdd.clear();
							formats.removeAll(toRemove);
							toRemove.clear();
						} else if (!result.isEmpty()) { // only finish if the line is not empty
							trimEnd(result);
							for (int i = formats.size(); i > 0; i--)
								result.append(formats.get(i - 1));
							result.append(newLine);
							formats.forEach(f -> result.append(f));
							formats.addAll(toAdd);
							toAdd.clear();
							formats.removeAll(toRemove);
							toRemove.clear();
							result.append(token).append(" ");
							creoleLength = 0;
							for (String format : formats)
								creoleLength += format.length();
							lineLength = token.length() + creoleLength + 1;
						}
					}
				}
			}
			nextLine = true;
		}
		trimEnd(result);
		return result.toString();
	}

	/**
	 * trims spaces of a StringBuffer at the end.
	 * 
	 * @param bufferToTrim
	 */
	private static void trimEnd(StringBuffer bufferToTrim) {
		while (bufferToTrim.charAt(bufferToTrim.length() - 1) == ' ')
			bufferToTrim.setLength(bufferToTrim.length() - 1);
	}

	/**
	 * If the greatest word is bigger than the planned wrapLength, the wrapLength
	 * should be set to this size.
	 * 
	 * @param content     content with the words in it. Will be separated with the
	 *                    regular expression for {@code \s}.
	 * @param wrapLength  actual planned wrapping length.
	 * @param maxWordSize maximum expected word size (performance optimization). 50
	 *                    characters is more or less the biggest word in German.
	 *                    Everything above that size doesn't make any sense to check
	 *                    for longer words.
	 * @return new wrap length
	 */
	private static int getMaxWordLength(String content, int wrapLength, int maxWordSize) {
		if (wrapLength <= maxWordSize) {
			int maxLength = Arrays.stream(content.split("\\s")).max(Comparator.comparingInt(String::length)).orElse("")
					.length();
			wrapLength = maxLength > wrapLength ? maxLength : wrapLength;
		}
		return wrapLength;
	}

	/**
	 * count the matches of a substring in a string in a efficient way.
	 * 
	 * @param string    content string.
	 * @param substring substring to search in the string.
	 * @return count of hits
	 */
	private static int countFindings(String string, String substring) {
		if (string == null || string.length() == 0 || substring == null || substring.length() == 0) {
			return 0;
		}

		int count = 0;
		int idx;
		for (int pos = 0; (idx = string.indexOf(substring, pos)) != -1; pos = idx + substring.length()) {
			++count;
		}

		return count;
	}
}
