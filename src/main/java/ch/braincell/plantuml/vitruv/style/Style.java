package ch.braincell.plantuml.vitruv.style;

/**
 * This class generates a CSS Stylesheet as it is used in PlantUML header area.
 * It conforms to the new {@code <style/>} tag defined in PlantUML.
 * 
 * @author boessu
 */
public interface Style {
	/**
	 * Generates the PlantUML CSS representation of the element style.
	 * 
	 * @return A string representing the PlantUML CSS properties for the style.
	 */
	public StyleSheet getPlantCSS();

	/**
	 * the area of a stereotype to define the styles for this stereotype.
	 */
	record StyleSheet(String element, String stereotype, String define) {
		/**
		 * gets the stereotype defined by this StyleSheet.
		 * 
		 * @return the stereotype including all defined styles for the stereotype.
		 */
		public String getStereoType() {
			return "  ." + stereotype + " {\n" + define + "  }\n";
		}
	}

	/**
	 * interface to implement the specific entries for the styles.
	 */
	interface SubStyleSteet {
		/**
		 * this method must return the CSS line to add to the CSS style.
		 * 
		 * @param define the style will be appended to the StringBuilder.
		 */
		void appendPlantSubCSS(StringBuilder define);
	}
}
