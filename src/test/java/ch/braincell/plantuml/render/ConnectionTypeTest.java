package ch.braincell.plantuml.render;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.braincell.plantuml.archimate.ConnectionType;
import ch.braincell.plantuml.archimate.style.Color;

class ConnectionTypeTest {

	@Test
	void testGetEnum() {
		ConnectionType type = ConnectionType.getEnum("SomeWeirdShouldBeDefault");
		assertTrue(type == ConnectionType.SERVING, "Enum Value of an unknown name was " + type);
		type = ConnectionType.getEnum(null);
		assertTrue(type == ConnectionType.SERVING, "Enum Value of an null value was " + type);
		type = ConnectionType.getEnum("Triggering");
		assertTrue(type == ConnectionType.TRIGGERING, type.toString());
	}

	@Test
	void testGetPlant() {
		assertTrue(ConnectionType.SERVING.getPlant(null, false).equals(" --> "), ConnectionType.SERVING.getPlant(null, false));
		assertTrue(ConnectionType.SERVING.getPlant(Color.BLACK, false).equals(" --> "), ConnectionType.SERVING.getPlant(Color.BLACK, false));
		assertTrue(ConnectionType.SERVING.getPlant(Color.RED, false).equals(" -[#ff0000]-> "),ConnectionType.SERVING.getPlant(Color.RED, false));
		assertTrue(ConnectionType.SERVING.getPlant(null, true).equals(" -[thickness=3,#000000]-> "), ConnectionType.SERVING.getPlant(null, true));
		assertTrue(ConnectionType.SERVING.getPlant(Color.BLUE, true).equals(" -[thickness=3,#0000ff]-> "), ConnectionType.SERVING.getPlant(null, true));
	}

}
