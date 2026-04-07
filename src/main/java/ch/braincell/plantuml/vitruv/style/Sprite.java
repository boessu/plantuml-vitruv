package ch.braincell.plantuml.vitruv.style;

public record Sprite(String name, String svg, double scale) {
	public Sprite(String name, String svg) {
		this(name, svg, 1.0);
	}

	public String getPlantSprite() {
		return "sprite $" + name + " " + svg;
	}

	public String getPlantStereotype() {
		StringBuilder result = new StringBuilder();
		result.append(" <<$");
		getPlantReference(result);
		result.append(">>");

		return result.toString();
	}
	public String getPlantInline() {
		StringBuilder result = new StringBuilder();
		result.append(" <$");
		getPlantReference(result);
		result.append(">");

		return result.toString();
	}
	
	private void getPlantReference(StringBuilder result) {
		result.append(name);
		if (scale != 1.0) {
			result.append('{').append("scale=" + scale).append('}');
		}
	}

}
