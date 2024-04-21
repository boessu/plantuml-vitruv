package ch.braincell.plantuml.vitruv;

import java.net.URL;

public record Reference (String name, URL url) implements Comparable<Reference> {
	String getPlant() {
		return "**" + (url == null ? name : "[[" + url.toString() + " " + name + "]]") + "**\n";
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
