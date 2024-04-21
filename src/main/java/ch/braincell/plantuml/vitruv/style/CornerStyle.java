package ch.braincell.plantuml.vitruv.style;

public record CornerStyle(Style style, int size) implements Style {
	
	public CornerStyle(Style style, int size) {
		this.style = style == null ? Style.NORMAL : style;
		this.size = size;
	}

	@Override
	public String toPlantCSS() {
		String result ="";
		result += style == Style.NORMAL ? "" : "  " + style.toPlantCSS() + " " + size + "\n";
		
		return result;
	}

	public enum Style {
		NORMAL(""), ROUNDED("RoundCorner"), DIAGONAL("DiagonalCorner");

		private String plantCSS;

		private Style(String plantCSS) {
			this.plantCSS = plantCSS;
		}

		String toPlantCSS() {
			return plantCSS;
		}
	}
}
