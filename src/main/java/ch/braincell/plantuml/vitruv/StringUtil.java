package ch.braincell.plantuml.vitruv;

import java.util.*;
import java.util.regex.Pattern;

/**
 * The {@code StringUtil} class provides utility methods for string manipulation with a focus on handling special cases
 * such as creole syntax and URLs within strings. It includes methods for replacing characters, checking for whitespace
 * or symbols, wrapping text, and counting occurrences of substrings.
 */
public class StringUtil {

    // for skipping lists (can't be wrapped)
    private final static Pattern SKIPPING_LISTS = Pattern.compile("^\\s*[*#=]+[\\s].*");

    // detect URL's (should not be wrapped)
    private final static String LINK_OPEN = "[[";
    private final static String LINK_CLOSE = "]]";

    /**
     * Replaces single quotes with double quotes. This is needed in PlantUML legends, block comments etc. There is the
     * risk that plantUML will misinterpret it as a comment if it is on the beginning of a line.
     *
     * @param input String in whose content all single quotes should be replaced.
     * @return new string with all single quotes replaced.
     */
    public static String replaceSingleQuotes(String input) {
        if (input == null)
            return null;
        // I don't think that's one of the things I win a flower pot with. But it is by
        // far the fastest implementation I've found.
        char[] inputChars = input.toCharArray();
        for (int i = 0; i < inputChars.length; i++) {
            if (inputChars[i] == '\'') {
                if ( // is the single quote at begin or end of a string
                        i == 0 || i == inputChars.length - 1 ||
                                // or is the single quote after or before a whitespace
                                isWhitespace(inputChars[i - 1]) || isWhitespace(inputChars[i + 1])
                                // or does a symbol character follow for the single quote
                                || isSymbol(inputChars[i + 1])) {
                    inputChars[i] = '"'; // replace it with double quote.
                }
            }
        }

        return new String(inputChars);
    }

    /**
     * Checks the single character for whitespace (the same definition as \s in regular expression)
     *
     * @param c the single character to check
     * @return true if it is a whitespace, false otherwise.
     */
    private static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == 0x0B;
    }

    /**
     * Checks if the character is an open or closing brace, or if it is a punctuation (.,;:?!)
     *
     * @param c the single character to check
     * @return true if it is a symbol character, false otherwise.
     */
    private static boolean isSymbol(char c) {
        return c == '(' || c == '[' || c == '{' || // open braces
                c == '}' || c == ']' || c == ')' || // close braces
                c == '.' || c == ',' || c == ';' || c == ':' || c == '?' || c == '!';
    }

    /**
     * Wraps a String with specific length and some sort of creole sensitivity from plantuml. The wrapping character is
     * a {@code \n} (newline).
     *
     * @param input      input string to word wrap
     * @param wrapLength length to wrap the lines. the longest word will overwrite this value.
     * @return text wrapped with newLine.
     */
    public static String wrap(String input, int wrapLength) {
        return wrap(input, wrapLength, "\n");
    }

    /**
     * Wraps a String with specific length and some sort of creole sensitivity from plantuml. This wrapper can't fix
     * curious scenarios with overlapping creole syntax (plantuml can't handle that too). TODO: Looks rather ugly (but
     * works as expected). Should be better optimized.
     *
     * @param input      input string to word wrap
     * @param wrapLength length to wrap the lines. the longest word will overwrite this value.
     * @param newLine    new line string to use for end of line (e.g. {@code "\n", "</br>"} etc.)
     * @return text wrapped with newLine.
     */
    public static String wrap(String input, int wrapLength, String newLine) {
        if (input == null || input.isEmpty())
            return null;

        StringBuilder result = new StringBuilder(input.length() + 32);

        wrapLength = getMaxWordLength(input, wrapLength, 50);

        String[] splitter = input.split("\\r\\n|\\n\\r|\\r|\\n");

        boolean isFirstLine = true;
        StringTokenizer tokenizer = null;
        CreoleHelper creoleHelper = new CreoleHelper();

        for (String line : splitter) {

            if (!isFirstLine || line.isEmpty()) {
                // for all lines following the first line (or empty lines), trim the end and add
                // a newLine.
                trimEnd(result);
                result.append(newLine);
            } else {
                // if we're on the first line, do nothing.
                isFirstLine = false;
            }

            if (line.length() <= wrapLength || SKIPPING_LISTS.matcher(line).matches()) {
                // if the line is either smaller as the wrapLength anyway or if we shouldn't
                // wrap, append and continue with the next line
                result.append(line);
            } else {
                int lineLength = 0;
                tokenizer = new StringTokenizer(line, " \t\f", false);
                creoleHelper.clearFormat();
                boolean openLink = false;

                while (tokenizer.hasMoreTokens()) {
                    final String token = tokenizer.nextToken();
                    final int tokenLength = token.length();

                    if (token.startsWith(LINK_OPEN)) {
                        // if a token starts with a link, don't wrap it.
                        openLink = true;
                    }

                    if (openLink) {
                        result.append(token);
                        lineLength += tokenLength;
                        if (token.endsWith(LINK_CLOSE)) {
                            openLink = false;
                        } else {
                            result.append(' ');
                            lineLength++;
                        }
                    } else {
                        // parsing creole tokens within the token in the line.
                        creoleHelper.parseCreole(token);

                        // word-wrap if the wrapLength is reached. ignore the tokens in the calculation
                        // of the actual length.
                        if ((lineLength + tokenLength - creoleHelper.getCreoleLength()) <= wrapLength) {
                            // normal behaviour: Add the token and add a space as separator.
                            result.append(token).append(' ');
                            lineLength += tokenLength + 1;

                            creoleHelper.consolidateFormat();
                        } else {
                            trimEnd(result);
                            creoleHelper.switchLine(result, token, newLine);
                            lineLength = tokenLength + creoleHelper.getCreoleLength() + 1;
                        }
                    }
                }
            }
        }
        trimEnd(result);
        return result.toString();
    }

    /**
     * trims spaces of a StringBuffer at the end.
     *
     * @param bufferToTrim changes the length of the StringBuilder instance to strip all spaces at the end of the
     *                     String.
     */
    private static void trimEnd(StringBuilder bufferToTrim) {
        while (!bufferToTrim.isEmpty() && bufferToTrim.charAt(bufferToTrim.length() - 1) == ' ')
            bufferToTrim.setLength(bufferToTrim.length() - 1);
    }

    /**
     * If the greatest word is bigger than the planned wrapLength, the wrapLength should be set to this size.
     *
     * @param content     content with the words in it. Will be separated with the regular expression for {@code \s}.
     * @param wrapLength  actual planned wrapping length.
     * @param maxWordSize maximum expected word size (performance optimization). 50 characters is more or less the
     *                    biggest word in German. Everything above that size doesn't make any sense to check for longer
     *                    words.
     * @return new wrap length
     */
    private static int getMaxWordLength(String content, int wrapLength, int maxWordSize) {
        if (wrapLength <= maxWordSize) {
            int maxLength = Arrays.stream(content.split("\\s")).max(Comparator.comparingInt(String::length)).orElse("")
                    .length();
            wrapLength = Math.max(maxLength, wrapLength);
        }
        return wrapLength;
    }

    /**
     * count the matches of a substring in a string in an efficient way.
     *
     * @param string    content string.
     * @param substring substring to search in the string.
     * @return count of hits
     */
    private static int countFindings(String string, String substring) {
        if (string == null || string.isEmpty() || substring == null || substring.isEmpty()) {
            return 0;
        }

        int count = 0;
        int idx;
        for (int pos = 0; (idx = string.indexOf(substring, pos)) != -1; pos = idx + substring.length()) {
            ++count;
        }

        return count;
    }

    /**
     * The {@code CreoleHelper} class is used for managing Creole markup tokens within a string. It provides methods to
     * parse Creole tokens, switch lines while maintaining formatting, and consolidate formatting changes.
     */
    static class CreoleHelper {

        /**
         * An array of Creole markup tokens.
         */
        private final static String[] CREOLETOKEN = {"**", "//", "\"\"", "--", "__", "~~"};
        /**
         * A list of current format tokens.
         */
        private final List<String> formats = new ArrayList<>(CREOLETOKEN.length);
        /**
         * A list of format tokens to be removed.
         */
        private final List<String> toRemove = new ArrayList<>(CREOLETOKEN.length);
        /**
         * A list of format tokens to be added.
         */
        private final List<String> toAdd = new ArrayList<>(CREOLETOKEN.length);
        /**
         * The total length of the Creole markup within the string.
         */
        private int creoleLength = 0;

        /**
         * Clears the formatting information.
         */
        public void clearFormat() {
            creoleLength = 0;
            formats.clear();
            toRemove.clear();
            toAdd.clear();
        }

        /**
         * Parses the given token to find and process Creole markup.
         *
         * @param token The string token to parse.
         */
        public void parseCreole(String token) {
            for (String creoleToken : CREOLETOKEN) {
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
        }

        /**
         * Switches the line and updates the formatting based on the current state.
         *
         * @param result  The {@code StringBuilder} to append the result to.
         * @param token   The string token to append after switching the line.
         * @param newLine The new line character or sequence to insert.
         */
        public void switchLine(StringBuilder result, final String token, final String newLine) {
            for (int i = formats.size(); i > 0; i--)
                result.append(formats.get(i - 1));
            result.append(newLine);
            formats.forEach(result::append);
            consolidateFormat();
            result.append(token).append(" ");
            creoleLength = 0;
            for (String format : formats)
                creoleLength += format.length();
        }

        /**
         * Consolidates the formatting changes by adding or removing format tokens.
         */
        public void consolidateFormat() {
            formats.addAll(toAdd);
            toAdd.clear();
            formats.removeAll(toRemove);
            toRemove.clear();
        }

        /**
         * Gets the total length of the Creole markup.
         *
         * @return The length of the Creole markup.
         */
        public int getCreoleLength() {
            return creoleLength;
        }
    }
}
