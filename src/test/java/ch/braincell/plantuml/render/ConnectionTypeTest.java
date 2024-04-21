package ch.braincell.plantuml.render;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.braincell.plantuml.vitruv.ArchimateConnectionType;
import ch.braincell.plantuml.vitruv.ConnectionType;
import ch.braincell.plantuml.vitruv.style.Color;

class ConnectionTypeTest {

	@Test
	void testGetEnum() {
		ConnectionType type = ArchimateConnectionType.getEnum("SomeWeirdShouldBeDefault");
		assertTrue(type == ArchimateConnectionType.SERVING, "Enum Value of an unknown name was " + type);
		type = ArchimateConnectionType.getEnum(null);
		assertTrue(type == ArchimateConnectionType.SERVING, "Enum Value of an null value was " + type);
		type = ArchimateConnectionType.getEnum("Triggering");
		assertTrue(type == ArchimateConnectionType.TRIGGERING, type.toString());
	}

	@Test
	void testGetPlant() {
		assertTrue(ArchimateConnectionType.SERVING.getPlant(null, false).equals(" --> "),
				ArchimateConnectionType.SERVING.getPlant(null, false));
		assertTrue(ArchimateConnectionType.SERVING.getPlant(Color.BLACK, false).equals(" --> "),
				ArchimateConnectionType.SERVING.getPlant(Color.BLACK, false));
		assertTrue(ArchimateConnectionType.SERVING.getPlant(Color.RED, false).equals(" -[#ff0000]-> "),
				ArchimateConnectionType.SERVING.getPlant(Color.RED, false));
		assertTrue(ArchimateConnectionType.SERVING.getPlant(null, true).equals(" -[thickness=3,#000000]-> "),
				ArchimateConnectionType.SERVING.getPlant(null, true));
		assertTrue(ArchimateConnectionType.SERVING.getPlant(Color.BLUE, true).equals(" -[thickness=3,#0000ff]-> "),
				ArchimateConnectionType.SERVING.getPlant(null, true));
	}

}
