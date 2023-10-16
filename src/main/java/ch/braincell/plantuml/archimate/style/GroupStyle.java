package ch.braincell.plantuml.archimate.style;

public record GroupStyle(String stereotype, CornerStyle cornerStyle, LineStyle lineStyle, FontStyle fontStyle, Color backgroundColor, boolean shadow) {
	public String toPlantCSS() {
		String result = "skinparam rectangle<<" + stereotype + ">> {\n";
		result += "  Shadowing " + shadow + "\n";
		result += backgroundColor == null ? "" : "  BackgroundColor " + backgroundColor + "\n";
		result += fontStyle == null ? "" : fontStyle.toPlantCSS();
		result += cornerStyle == null ? "" : cornerStyle.toPlantCSS();
		result += lineStyle == null ? "" : lineStyle.toPlantCSS();
		result += "}\n";
		return result;
	}
}
