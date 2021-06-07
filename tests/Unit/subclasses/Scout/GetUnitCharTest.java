package Unit.subclasses.Scout;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import MilitaryBaseSimulation.MapUnits.Unit.subclasses.Scout.Scout;

/**
 * 
 * @author Przemys�aw Ma�ecki
 *
 */
class GetUnitCharTest {

	@Test
	void sayS() {
		int[] pos = {0,0};
		Scout scout = new Scout(0,pos,0,0, 0, null);
		
		assertTrue(scout.getUnitChar() == 'S', "Scout should return S char.");
	}

}
