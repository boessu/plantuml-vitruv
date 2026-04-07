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
        assertEquals(0xff010101, testColor.argb());
	}

	@DisplayName("convert CSS color to color")
	@Test
	void testFromCSS() {
		Color testColor = Color.fromCSS("#FFCC00");
        assertEquals(0xffffcc00, testColor.argb());
	}

	@DisplayName("toString produces CSS color format")
	@Test
	void testToString() {
		Color testColor = new Color(0xffffcc00);
        assertEquals("#FFCC00", testColor.toString());
	}

}
