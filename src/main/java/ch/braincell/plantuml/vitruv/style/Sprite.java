package ch.braincell.plantuml.vitruv.style;

/**
 * Represents a sprite definition for PlantUML diagrams. A sprite is a small
 * graphical element that can be embedded within diagram elements to enhance
 * their visual representation.
 * 
 * @author boessu
 */
public record Sprite(String name, String svg, double scale) {
    
    /**
     * Constructs a new {@code Sprite} with the specified name, SVG content, and
     * default scale of 1.0.
     * 
     * @param name The name of the sprite, used for identification in PlantUML.
     * @param svg  The SVG content representing the sprite image.
     */
    public Sprite(String name, String svg) {
        this(name, svg, 1.0);
    }

    /**
     * Constructs a new {@code Sprite} with the specified name, SVG content, and
     * scale factor.
     * 
     * @param name  The name of the sprite, used for identification in PlantUML.
     * @param svg   The SVG content representing the sprite image.
     * @param scale The scaling factor to apply to the sprite when rendered.
     */
    public Sprite(String name, String svg, double scale) {
        validateSvg(svg);
        this.name = name;
        this.scale = scale;
        this.svg = svg;
    }

    /**
     * Validates that the provided SVG string is valid.
     * 
     * @param svg The SVG string to validate.
     * @return The validated SVG string.
     * @throws RuntimeException if the SVG string is null or doesn't start with "&lt;svg".
     */
    private static String validateSvg(String svg) {
        if (svg == null) {
            throw new RuntimeException("SVG string cannot be null");
        }
        if (!svg.startsWith("<svg")) {
            throw new RuntimeException("SVG string must begin with <svg");
        }
        return svg;
    }

    /**
     * Generates the PlantUML sprite definition command for this sprite.
     * 
     * @return The PlantUML command string to define this sprite in a diagram.
     */
    public String getPlantSprite() {
        return "sprite $" + name + " " + svg;
    }

    /**
     * Generates the PlantUML stereotype syntax for using this sprite.
     * 
     * @return The PlantUML stereotype string that references this sprite.
     */
    public String getPlantStereotype() {
        StringBuilder result = new StringBuilder();
        result.append(" <<$");
        getPlantReference(result);
        result.append(">>");

        return result.toString();
    }

    /**
     * Generates the PlantUML inline syntax for using this sprite.
     * 
     * @return The PlantUML inline string that references this sprite.
     */
    public String getPlantInline() {
        StringBuilder result = new StringBuilder();
        result.append(" <$");
        getPlantReference(result);
        result.append(">");

        return result.toString();
    }

    /**
     * Appends the PlantUML reference for this sprite to the provided StringBuilder.
     * 
     * @param result The StringBuilder to append the reference to.
     */
    private void getPlantReference(StringBuilder result) {
        result.append(name);
        if (scale != 1.0) {
            result.append('{').append("scale=" + scale).append('}');
        }
    }
}
