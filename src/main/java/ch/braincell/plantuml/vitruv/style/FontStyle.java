package ch.braincell.plantuml.vitruv.style;

public record FontStyle(Style style, Color color) implements Style {
	
	public FontStyle(Style style, Color color) {
		this.style = style == null ? Style.NORMAL : style;
		this.color = color == null || color.equals(Color.BLACK) ? Color.BLACK : color;
	}

	@Override
	public String toPlantCSS() {
		String result = "";
		result += color == Color.BLACK ? "" : "  FontColor " + color.toString() + "\n";
		result += style == Style.NORMAL ? "" : "  FontStyle " + style.toPlantCSS() + "\n";
		return result;
	}
	
	public enum Style {
		NORMAL(""), BOLD("bold"), ITALIC("italic");
		
		private String plantCSS;

		private Style(String plantCSS) {
			this.plantCSS = plantCSS;
		}
		
		String toPlantCSS() {
			return plantCSS;
		}
	}
}
