/**
 * 
 */
package Unit.subclasses.TargetUnit.subclasses.NeutralUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import MilitaryBaseSimulation.MapUnits.Unit.subclasses.TargetUnit.subclasses.NeutralUnit.NeutralUnit;

/**
 * @author Przemys�awMa�ecki
 *
 */
class GetUnitCharTest {

	@Test
	void sayN() {
		int[] pos = {0,0};
		NeutralUnit scout = new NeutralUnit(0,pos);
		
		assertTrue(scout.getUnitChar() == 'N', "NeutralUnit should return N char.");
	}

}
