package MilitaryBaseSimulation.MapUnits.Unit;

/**
 * 
 * @author Przemys�aw Ma�ecki
 *
 */
public interface IUnit {
	public void move();
	public int[] getPosition();
	public char getUnitChar();
	public void refreshMovement();
}
