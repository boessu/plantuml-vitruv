package ch.braincell.plantuml.vitruv;

import ch.braincell.plantuml.vitruv.style.Color;

/**
 * Enum representing the different connection types in the Edgy Enterprise Design Foundation. These types
 * indicate the style of a connection when represented in PlantUML.
 * 
 * @author boessu
 */
public enum EdgyConnectionType implements ConnectionType {
    /**
     * the unidirectional link relationship corresponds to a not further specified association relation.
     */
    LINK("-?-"),
    /**
     * a notion of flow between two elements.
     */
    FLOW("-?->"),
    /**
     * any hierarchical representation of the same element is a tree relationship, which corresponds to an aggregation
     * relationship.
     */
    TREE("-?-*");

    // Fields representing the beginning and ending syntax for PlantUML
    private final String beginPlant;
    private final String endPlant;

    /**
     * Constructor for the connection type enum. It splits the PlantUML syntax into beginning and ending parts.
     *
     * @param plant The complete PlantUML syntax for the connection type.
     */
    EdgyConnectionType(String plant) {
        int split = plant.indexOf('?');
        this.beginPlant = " " + plant.substring(0, split);
        this.endPlant = plant.substring(split + 1) + " ";
    }

    /**
     * Retrieves the enum constant that matches the given name. Defaults to SERVING if the name is null or no match is
     * found.
     *
     * @param name The name of the enum constant to retrieve.
     * @return The matching enum constant or SERVING as the default.
     */
    public static ConnectionType getEnum(String name) {
        if (name == null)
            return LINK;
        for (EdgyConnectionType value : EdgyConnectionType.values()) {
            if (name.toUpperCase().equals(value.name())) {
                return value;
            }
        }

        return LINK;
    }

    @Override
    public String getPlant(Color color, boolean bold) {
        final Color resultColor = color == null ? Color.BLACK : color;
        String result = "";
        if (bold) {
            result = "[thickness=3," + resultColor + "]";
        } else if (!(resultColor == Color.BLACK)) {
            result = "[" + resultColor + "]";
        }
        return beginPlant + result + endPlant;
    }
}
