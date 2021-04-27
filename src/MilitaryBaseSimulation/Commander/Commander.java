package MilitaryBaseSimulation.Commander;
import MilitaryBaseSimulation.IMilitary.*;
import MilitaryBaseSimulation.TargetUnit.*;
import MilitaryBaseSimulation.Gunner.*;
import MilitaryBaseSimulation.Scout.*;

public class Commander implements ISender, IReceiver{
	public void send(String report, ITargetUnit unit, IReceiver receiver) {}
	public void receive(String report, ITargetUnit unit) {}
}