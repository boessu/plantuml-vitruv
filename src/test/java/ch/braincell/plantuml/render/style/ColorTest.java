package ch.braincell.plantuml.render.style;

import ch.braincell.plantuml.vitruv.style.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorTest {

	@DisplayName("Constructor with separate R, G and B Value")
	@Test
	void testColor() {
		Color testColor = new Color(1, 1, 1);
        assertEquals(0x010101, testColor.rgb());
	}

	@DisplayName("convert CSS color to color")
	@Test
	void testFromCSS() {
		Color testColor = Color.fromCSS("#FFCC00");
        assertEquals(0xffcc00, testColor.rgb());
	}

	@DisplayName("toString produces CSS color format")
	@Test
	void testToString() {
		Color testColor = new Color(0xffcc00);
        assertEquals("#ffcc00", testColor.toString());
	}

}
