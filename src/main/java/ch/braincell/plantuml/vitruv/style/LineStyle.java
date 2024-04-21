package ch.braincell.plantuml.vitruv.style;

public record LineStyle(Style style, Color color, int thickness) implements Style {
	
	public LineStyle(Style style, Color color, int thickness) {
		this.style = style == null ? Style.NORMAL : style;
		this.color = color == null || color.equals(Color.BLACK) ? Color.BLACK : color;
		this.thickness = thickness;
	}

	@Override
	public String toPlantCSS() {
		String result = "";
		result += style == Style.NORMAL ? "" : "  BorderStyle " + style.toPlantCSS() + "\n";
		result += thickness <= 1 ? "" : "  BorderThickness " + thickness + "\n";
		result += color == Color.BLACK ? "" : "  BorderColor " + color + "\n";
		
		return result;
	}
	
	public enum Style {
		NORMAL(""), DASHED("dashed"), DOTTED("dotted");
		
		private String plantCSS;

		private Style(String plantCSS) {
			this.plantCSS = plantCSS;
		}
		
		String toPlantCSS() {
			return plantCSS;
		}
	}
}