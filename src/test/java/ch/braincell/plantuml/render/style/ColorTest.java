package ch.braincell.plantuml.render.style;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ch.braincell.plantuml.vitruv.style.Color;

class ColorTest {

	@DisplayName("Constructor with separate R, G and B Value")
	@Test
	void testColor() {
		Color testColor = new Color(1, 1, 1);
		assertTrue(testColor.rgb() == 0x010101);
	}

	@DisplayName("convert CSS color to color")
	@Test
	void testFromCSS() {
		Color testColor = Color.fromCSS("#FFCC00");
		assertTrue(testColor.rgb() == 0xffcc00);
	}

	@DisplayName("toString produces CSS color format")
	@Test
	void testToString() {
		Color testColor = new Color(0xffcc00);
		assertTrue(testColor.toString().equals("#ffcc00"));
	}

}
