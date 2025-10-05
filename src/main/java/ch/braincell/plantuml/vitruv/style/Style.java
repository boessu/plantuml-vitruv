package ch.braincell.plantuml.vitruv.style;

public interface Style {
	/**
	 * Generates the PlantUML CSS representation of the element style.
	 * 
	 * @return A string representing the PlantUML CSS properties for the style.
	 */
	public StyleSheet getPlantCSS();
	
	record StyleSheet(String element, String stereotype, String define) {
		public String getStereoType () {
			return "  ." + stereotype + " {\n" + define + "  }\n";
		}
	}
	
	interface SubStyleSteet {
		void appendPlantSubCSS(StringBuilder define);
	}
}
