package MilitaryBaseSimulation.Militaries.Gunner;

import MilitaryBaseSimulation.MilitaryBaseSimulation;
import MilitaryBaseSimulation.Enums.ReportInfo;
import MilitaryBaseSimulation.MapUnits.Unit.subclasses.TargetUnit.interfaces.IDestroyable;

/**
 * 
 * @author Bartosz S?omowicz
 *
 */
public class Gunner implements IGunner {
	private int accuracy;
	
	/**
	 * Constructor.
	 * @param accuracy Value which will decide Gunner's accuracy.
	 */
	public Gunner(int accuracy) {
		this.accuracy = accuracy;
	}
	
	/**
	 * Causes unit to use getDestroyed method, depending on random chance due to Gunner's accuracy.
	 * @param unit Unit which will be attacked by Gunner.
	 */
	private void attack(IDestroyable unit) {
		if(MilitaryBaseSimulation.generateRandomEventHappening(this.accuracy))
			unit.getDestroyed();
	}
	
	/**
	 * Manages receiving report from Commander.
	 * @param report String which contains info about attack.
	 * @param unit Unit which will be attacked.
	 */
	public void receive(ReportInfo report, IDestroyable unit) {
		if(report == ReportInfo.ATTACK)
			this.attack(unit);
	}
	
	/**
	 * Gets the accuracy field.
	 */
	public int getAccuracy(){
		return accuracy;
	}
}
