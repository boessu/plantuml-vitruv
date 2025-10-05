package ch.braincell.plantuml.render;

import ch.braincell.plantuml.vitruv.ArchimateConnectionType;
import ch.braincell.plantuml.vitruv.ConnectionType;
import ch.braincell.plantuml.vitruv.style.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ConnectionTypeTest {

	@Test
	void testGetEnum() {
		ConnectionType type = ArchimateConnectionType.getEnum("SomeWeirdShouldBeDefault");
        assertSame(type, ArchimateConnectionType.SERVING, "Enum Value of an unknown name was " + type);
		type = ArchimateConnectionType.getEnum(null);
        assertSame(type, ArchimateConnectionType.SERVING, "Enum Value of an null value was " + type);
		type = ArchimateConnectionType.getEnum("Triggering");
        assertSame(type, ArchimateConnectionType.TRIGGERING, type.toString());
	}

	@Test
	void testGetPlant() {
        assertEquals(" --> ", ArchimateConnectionType.SERVING.getPlant(null, false), ArchimateConnectionType.SERVING.getPlant(null, false));
        assertEquals(" --> ", ArchimateConnectionType.SERVING.getPlant(Color.BLACK, false), ArchimateConnectionType.SERVING.getPlant(Color.BLACK, false));
        assertEquals(" -[#ff0000]-> ", ArchimateConnectionType.SERVING.getPlant(Color.RED, false), ArchimateConnectionType.SERVING.getPlant(Color.RED, false));
        assertEquals(" -[thickness=3,#000000]-> ", ArchimateConnectionType.SERVING.getPlant(null, true), ArchimateConnectionType.SERVING.getPlant(null, true));
        assertEquals(" -[thickness=3,#0000ff]-> ", ArchimateConnectionType.SERVING.getPlant(Color.BLUE, true), ArchimateConnectionType.SERVING.getPlant(null, true));
	}

}
