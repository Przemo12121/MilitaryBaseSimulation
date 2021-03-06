package MilitaryBaseSimulation;

import MilitaryBaseSimulation.GUI.*;
import MilitaryBaseSimulation.Map.Map;
import MilitaryBaseSimulation.MapUnits.Unit.*;
import MilitaryBaseSimulation.MapUnits.Unit.subclasses.Scout.*;
import MilitaryBaseSimulation.MapUnits.Unit.subclasses.TargetUnit.subclasses.EnemyUnit.EnemyUnit;
import MilitaryBaseSimulation.MapUnits.Unit.subclasses.TargetUnit.subclasses.EnemyUnit.subclasses.DisguisedEnemyUnit.DisguisedEnemyUnit;
import MilitaryBaseSimulation.MapUnits.Unit.subclasses.TargetUnit.subclasses.NeutralUnit.NeutralUnit;
import MilitaryBaseSimulation.Militaries.Commander.Commander;
import MilitaryBaseSimulation.Militaries.Commander.ICommander;
import MilitaryBaseSimulation.Militaries.Commander.interfaces.IRatable;
import MilitaryBaseSimulation.Militaries.Gunner.*;
import MilitaryBaseSimulation.Militaries.Headquarters.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author Przemys?aw Ma?ecki
 * @author Bartosz S?omowicz
 * @author Mateusz Torski
 *
 */
public class MilitaryBaseSimulation {

	public static void main(String[] args) {
		gui = new GUI(Map.getInstance(), true); //creates new gui window
		
		//below code handles command line arguments
		if(args.length > 0) {
			int[] argsInt = new int[args.length];
			
			//converts args to integers and exits if unsuccessful
			for(int i = 0; i<args.length; i++) {
				try {
					argsInt[i] = Integer.parseInt(args[i]);
				}catch(Exception e) {
					System.out.println("All of input arguments must be integer numbers.");
					System.out.println("Argument no." + i +", " + args[i] + ", was detected not to be integer number.");
					System.out.println("Please try again.");
					System.exit(0);
				}
			}
			
			//fills gui with command liine arguments and returns expected number of arguments
			int expectedArgsLength = fillGui(gui, argsInt);
			
			//checks if user gave enough command line arguments and informs the user.
			if(expectedArgsLength > args.length) {
				System.out.println("Provided input had too few arguments. The required missing input arguments must be provided manually in gui.");
			}
			else {
				gui.pressStart(); //builds and starts the simulation
			}
		}
	}
	//randomness handler
	private static Random random = new Random();
	
	//private objects for the program to access
	private static ICommander commander;
	private static IHeadquarters headquarters;
	private static List<IScout> scouts;
	private static IGUI gui;
	
	//base hit points
	private static int baseHP;
	
	//private fields for simulation control
	private static int enemyFreq;
	private static int disguisedEnemyFreq;
	private static int iterations;
	
	/**
	 * Tries to get data at given position from args.
	 * @param position Position from which it is tried to extract data from.
	 * @param args Array of integers from which the data is tried to be extraced from.
	 * @return Data at given position or 0 if it fails, since empty gui fields have 0 as value by default.
	 */
	private static int tryGetData(int position, int[] args) {
		try {
			return args[position];
		}catch(Exception e) {
			return 0; //empty gui fields have 0 value as default
		}
	}
	
	/**
	 * Fills gui's input text fields with given arguments.
	 * @param gui Gui to fill data with.
	 * @param args Array of arguments.
	 * @return Integer value representing counted necessary input fields.
	 */
	private static int fillGui(IGUI gui, int[] args) {
		int numberOfScouts = args[0]; 			 //gets and fills number of scouts
		gui.setNumberOfScouts(numberOfScouts);
		
		int argsIterator = numberOfScouts*4 + 1; //point to argument representing number of gunners,
												 //each scout requires 4 arguments
		int numberOfGunners = tryGetData(argsIterator, args);	//gets and fills number of gunners
		gui.setNumberOfGunners(numberOfGunners);
		
		argsIterator = 0; 			//point to start, so the scouts can be filled
		if(numberOfGunners > 0 && numberOfScouts > 0) {
			gui.setParameters(); 	//enables scouts and gunners fields to be filled
			
			for(int i = 0; i < numberOfScouts; i++, argsIterator += 4){
				gui.setScout(i,  						//fills all scouts
					tryGetData(1 + argsIterator, args), 
					tryGetData(2 + argsIterator, args), 
					tryGetData(3 + argsIterator, args),
					tryGetData(4 + argsIterator, args)
					);
			}
			
			argsIterator = numberOfScouts*4 + 2; //point to the first gunners parameter
			for(int i = 0; i < numberOfGunners; i++, argsIterator++) {
				gui.setGunner(i, tryGetData(argsIterator, args)); //fill all gunners
			}
			
			//fills the rest of required fields
			gui.setBaseHP(tryGetData(argsIterator, args));
			argsIterator++;
			gui.setEnemy(tryGetData(argsIterator, args));
			argsIterator++;
			gui.setDisguisedEnemy(tryGetData(argsIterator, args));
			argsIterator++;
			gui.setIterations(tryGetData(argsIterator, args));
			argsIterator++;
		}
		return argsIterator;
	}
	
	/**
	 * Starts simulation based on input parameters. Ends after reaching input iterations limit,
	 * or when base hp drops to 0.
	 */
	public static void run() {
		IUnit[][] map = Map.getInstance().getMap();			 //map to be iterated through
		
		List<IUnit> units = Map.getInstance().getAllUnits(); //collection of units required for refresh
		List<Integer> scoutsTrusts = new ArrayList<>(); 	 //collection required for outputing data to user
		
		try {		//creates a file writer and writes names of tracked parameters
			FileWriter writer = new FileWriter("simulationData.csv");	
			writer.write("Iteration;Commander's rating;base hit points;");
			for(int j = 0; j<scouts.size(); j++) writer.write("Scout no." + j + 1 + " trust level;");
			writer.write("Unit count; NeutralUnit count; EnemyUnit count; DisguisedEnemyUnit count\n");
			
			for(int i = 0; i<iterations;i++) {		//runs simulation for given duration
				
				//writes data at the beginning of iteration
				writer.write(i+1 + ";" + commander.getRating() + ";" + baseHP +";");
				for(int j = 0; j<scouts.size(); j++) writer.write(scouts.get(j).getTrustLevel() + ";");
				writer.write(Unit.getCount() + ";" + NeutralUnit.getCount() + ";" + EnemyUnit.getCount() + ";" + DisguisedEnemyUnit.getCount() + "\n");
				
				//draws map in the gui and outputs tracked data
				scoutsTrusts.clear();
				scouts.forEach(scout -> scoutsTrusts.add(scout.getTrustLevel()));
				gui.drawMap(scoutsTrusts, baseHP, i+1, commander.getRating(), 
						Unit.getCount(), NeutralUnit.getCount(), 
						EnemyUnit.getCount(), DisguisedEnemyUnit.getCount());
				
				for(IUnit[] row : map) {	//iterate through the map, this approach
					for(IUnit unit : row) { //is required since collection of units 
						if(unit != null) {  //can be modified at the runtime
							try{
								//try to move, and check for unexpected error
								unit.move();
								
								//chceck if base was not destroyed and finish simulation if is so
								if(baseHP <= 0) {
									System.out.println("Base was destroyed. Simulation ended.");
									writer.write("\nSimulation ended because base hit points were brought down to 0.");
									return;
								}
							}catch(Exception e) {
								System.out.println("Simulation approached unexpected error." + e.getMessage());
								writer.write("\nSimulation ended because of an error.");
								writer.close();
								return;
							}
						}
					}
				}
				// create new unit at the end of iteration
				Map.getInstance().placeUnitOnMap(generateNewUnit(i, Map.getInstance().getRandomStartingPosition()));
				TimeUnit.SECONDS.sleep(1); 	//delays changes so the animation can be seen
				for(IUnit unit : units) unit.refreshMovement(); //refresh movements of each alive unit
			}
			writer.write("\nSimulation ended because it approached iteration limit.");
			writer.close();
		}catch(IOException e){
			System.out.println("Cannot access simulationData.txt, simulation data cannot be saved. " + e.getMessage());
			return;
		}catch(InterruptedException e) {
			System.out.println("Delaying process interrupted. " + e.getMessage());
			return;
		}
	}
	
	/**
	 * Builds simulation parameters and its actors by getting data from gui.
	 */
	public static void buildSimulation() {
		Map.getInstance().initializeMap();
		
		//sets gunners
		List<Integer> gunnersParams = gui.getGunner();
		List<IGunner> gunners = new ArrayList<>();
		for(Integer acc : gunnersParams) {
			gunners.add(new Gunner(acc));
		}
		
		//sets commander
		commander = new Commander(gunners);
		
		//sets scouts
		List<int[]> scoutsParams = gui.getScout();
		scouts = new ArrayList<>();
		IScout scout;
		for(int[] params : scoutsParams) {
			scout = new Scout(params[0], Map.getInstance().getRandomPosition(), params[1], params[2], params[3], commander);
			scouts.add(scout);
			Map.getInstance().placeUnitOnMap((IUnit)scout);
		}
		
		//sets simulation control parameters
		enemyFreq = gui.getEnemy();
		disguisedEnemyFreq = gui.getDisguisedEnemy();
		iterations = gui.getIterations();
		baseHP = gui.getBaseHP();
		
		//sets hq
		headquarters = new Headquarters(commander);
		
		//filling 4% of 2d map with random units		
		for(int i = 0; i < 100; i++) {
			IUnit newUnit = generateNewUnit(i, Map.getInstance().getRandomPosition());
			Map.getInstance().placeUnitOnMap(newUnit);
		}
	}
	
	/**
	 * Damages base.
	 * @param damage Damage inflicted to the base hit points.
	 */
	public static void damageBase(int damage) {
		baseHP -= damage;
	}
	
	/**
	 * Gets the commander.
	 * @return
	 */
	public static IRatable getCommander() {
		return commander;
	}
	
	/**
	 * Gets the headquarters.
	 * @return
	 */
	public static IHeadquarters getHeadquarters() {
		return headquarters;
	}
	
	/**
	 * Generates boolean value representing occurence of
	 * random event happening with probabilty.
	 * @param probabilty The probability of the event.
	 * @return Boolean value representing whether event happened or not.
	 */
	public static boolean generateRandomEventHappening(int probabilty) {
		return random.nextInt(100) < probabilty;
	}
	
	/**
	 * Generates unit of subclass chosen from NeutralUnit, EnemyUnit, DisguisedEnemyUnit
	 * based on iteration, and sets its position to given.
	 * @param iteration Iteration based on which subclass is chosen.
	 * @param position Position of unit to place it on.
	 * @return Generated IUnit.
	 */
	private static IUnit generateNewUnit(int iteration, int[] position) {
		if( iteration%disguisedEnemyFreq == 0) {
			return new DisguisedEnemyUnit(random.nextInt(3)+1, position, random.nextInt(5)+1); 
		}
		else if( iteration%enemyFreq == 0) {
			return new EnemyUnit(random.nextInt(3)+1, position, random.nextInt(5)+1);
		}
		else {
			return new NeutralUnit(random.nextInt(3)+1, position);
		}
	}
}
