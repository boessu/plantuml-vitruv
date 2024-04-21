package ch.braincell.plantuml.vitruv;

import java.net.URL;

public interface LeafType {
	String getPlant(String name, String ID, URL url, String color, boolean bold);
	String getStereotype();
	String getColor();
}
