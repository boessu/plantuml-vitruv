package ch.braincell.plantuml.vitruv;

import java.net.URL;

/**
 * Represents a reference with a name and a URL.
 * This record is comparable based on its string representation.
 * 
 * @author boessu
 */
public record Reference (String name, URL url) implements Comparable<Reference> {

    /**
     * Retrieves a formatted plant representation of the reference.
     * If the URL is not provided, only the name is returned.
     * Otherwise, it returns a Markdown link with the name as the link text.
     *
     * @return A string representing the plant formatted reference.
     */
	String getPlant() {
		return "**" + (url == null ? name : "[[" + url + " " + name + "]]") + "**\n";
	}

	@Override
	public String toString() {
		return name + " " + url;
	}
	
	@Override
	public int compareTo(Reference r) {
		return this.toString().compareTo(r.toString());
	}
}
