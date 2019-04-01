package tc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import code.OceanMap;

public class OceanMapTest {

	@Test
	void testOceanMap() {
		//tests to see if its the same grid 
		code.OceanMap method = OceanMap.getGrid();
		code.OceanMap method2 = OceanMap.getGrid();
		assertEquals(method, method2);
		assertTrue(method == method2);
	}
	
	@Test
	void testDimensions() {
		//tests to see if the grid contains the same dimensions
		code.OceanMap method = OceanMap.getGrid();
		code.OceanMap method2 = OceanMap.getGrid();
		assertEquals(method.getDimensions(), method2.getDimensions());
		assertTrue(method.getDimensions() == method2.getDimensions());
	}
	
	@Test
	void testPlacedShips() {
		//tests to see if the ship placed in one instance is the same one in another
		code.OceanMap method = OceanMap.getGrid();
		code.OceanMap method2 = OceanMap.getGrid();
		assertEquals(method.getShipLocation(), method2.getShipLocation());
		assertTrue(method.getShipLocation() == method2.getShipLocation());
	}

	@Test
	void testMap() {
		//tests to make sure only one map is created and when called if its the same map
		code.OceanMap method = OceanMap.getGrid();
		code.OceanMap method2 = OceanMap.getGrid();
		assertEquals(method.getMap(), method2.getMap());
		assertTrue(method.getMap() == method2.getMap());
	}
	
	@Test
	void testplacedTreasure() {
		//tests to see if its the same grid 
		code.OceanMap method = OceanMap.getGrid();
		code.OceanMap method2 = OceanMap.getGrid();
		assertEquals(method.getTreasureLocation(), method2.getTreasureLocation());
		assertTrue(method.getTreasureLocation() == method2.getTreasureLocation());
	}

}
