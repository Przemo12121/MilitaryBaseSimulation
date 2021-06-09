package MilitaryBaseSimulation.GUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import MilitaryBaseSimulation.Map.IMap;
import MilitaryBaseSimulation.Map.Map;
import MilitaryBaseSimulation.MapUnits.Unit.IUnit;



public class GUI extends Frame implements IGUI{
	private TextField baseHPField;	
	private TextField durationField;
	private TextField enemyField;
	private TextField disguisedEnemyField;
	private TextField scoutNumberField;
	private ArrayList<TextField[]> scoutFields;
	private TextField gunnerNumberField;
	private ArrayList<TextField> gunnerFields;
	private ActionListener scoutButtonOnClick;
	private Button scoutButton;
	private Button set;
	private ActionListener setButtonOnClick;
	private IMap map;
		
		public GUI (IMap mapToDraw, boolean shouldBeVisible) {

			this.map = mapToDraw;
			
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			


	         
	         
	         
	         
			//texts
			Label duration = new Label("Duration of the simulation:");
			Label baseHP = new Label("Initial base health points:");
			Label disguisedEnemy = new Label("Disguised enemy units generating period:");
			Label enemy = new Label("Enemy units generating period:");
			Label scoutNumber = new Label("Declare scouts:");			
			Label gunnerNumber = new Label("Declare gunners:");	
			
			//textfields
			this.scoutNumberField = new TextField("5", 8);
			this.baseHPField = new TextField("1000000", 8);
			this.durationField = new TextField("1000000", 8);
			this.enemyField = new TextField("10", 8);
			this.disguisedEnemyField = new TextField("10", 8);
			this.set = new Button("Start");
			this.set.setEnabled(false);

			this.gunnerNumberField = new TextField("5", 8);
			this.scoutButton = new Button("Next");
			
			
			
			
			//setting location
			duration.setLocation(200, 150);
			this.set.setSize(100, 20);
			
			

			addWindowListener(new MyWindowListener());
			
			
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;

			
			
			//adding first column of labels
			add(duration, c);
			c.gridy = 1;
			add(baseHP, c);
			c.gridy = 2;
			add(enemy, c);
			c.gridy = 3;
			add(disguisedEnemy, c);
			c.gridy = 4;
			add(scoutNumber, c);
			c.gridy = 5;
			add(gunnerNumber, c);
			c.gridy = 6;
			add(set, c);
			
			
			//adding first column of fields
			c.gridx = 1;
			c.gridy = 0;
			add(durationField, c);
			c.gridy = 1;
			add(baseHPField, c);
			c.gridy = 2;
			add(enemyField, c);
			c.gridy = 3;
			add(disguisedEnemyField, c);
			c.gridy = 4;
			add(scoutNumberField, c);
			c.gridy = 5;
			add(gunnerNumberField, c);
			c.gridy = 6;
        	add(scoutButton, c);
			


			
			this.scoutFields = new ArrayList<TextField[]>();
		    this.gunnerFields = new ArrayList<TextField>();

			this.scoutButtonOnClick = new ActionListener(){  
		        
				public void actionPerformed(ActionEvent e){  
		        	
		        	int numberOfScouts= Integer.parseInt(scoutNumberField.getText());
		        	c.weightx = 1.0;
					c.gridx = 3;
		        	for(int i = 0; i < numberOfScouts; i++){
					    	
							scoutFields.add(new TextField[4]);
							

							//labels
					    	Label scoutSpeed = new Label("Movement speed:");
					    	Label scoutEffectiveness = new Label("Effectiveness:");
					    	Label scoutTrust = new Label("Trust level:");	
					    	Label scoutVisionRange = new Label("Vision range:");
					    	
					    	//fields
					        scoutFields.get(i)[0] = new TextField("0", 8);	
					        scoutFields.get(i)[1] = new TextField("0", 8);
					        scoutFields.get(i)[2] = new TextField("0", 8);		
					        scoutFields.get(i)[3] = new TextField("0", 8);
					        

							c.gridy = 0;
							
					        //first column of labels
					        add(scoutSpeed, c);
							c.gridy = 1;
					    	add(scoutTrust, c);
							c.gridy = 2;
					    	add(scoutEffectiveness, c);
							c.gridy = 3;
					    	add(scoutVisionRange, c);

					    	
							c.gridx++;
							c.gridy = 0;
							
					        //first column of fields
					        add(scoutFields.get(i)[0], c);
							c.gridy = 1;
					        add(scoutFields.get(i)[1], c);
							c.gridy = 2;
					        add(scoutFields.get(i)[2], c);	
							c.gridy = 3;
					        add(scoutFields.get(i)[3], c);
							c.gridx++;



					}
					
		        	
		        	
		        	
		        	int numberOfGunners= Integer.parseInt(gunnerNumberField.getText());
					c.gridx = 3;
					c.gridy = 5;
					
					for(int i = 0; i < numberOfGunners; i++){
						
					    	gunnerFields.add(new TextField("0", 8));
					    	Label gunnerAccuracy = new Label("Accuracy:");
					    	
							
			

							
					    	add(gunnerAccuracy, c);
					    	c.gridx++;
							add(gunnerFields.get(i), c);
							c.gridx++;

					}
					


					

	                revalidate();
	                repaint();
	                
	                
		            }  
		        };  
			
		    this.scoutButton.addActionListener(this.scoutButtonOnClick);
		    



		     
			
		    
		    
		    this.setButtonOnClick = new ActionListener(){
		    		
		    	public void actionPerformed(ActionEvent e){  
		    		MilitaryBaseSimulation.MilitaryBaseSimulation.buildSimulation();
		    		MilitaryBaseSimulation.MilitaryBaseSimulation.run();
		    		}  
		    };
		    this.set.addActionListener(this.setButtonOnClick);
		    
		    

			setSize(1920, 1080);
			setVisible(shouldBeVisible);
			
		}
		
		
		public int getBaseHP() {
			return Integer.parseInt(baseHPField.getText());
		}
		
		public int getIterations() {
			return Integer.parseInt(durationField.getText());
		}
		
		public int getEnemy() {
			return Integer.parseInt(enemyField.getText());
		}
		
		public int getDisguisedEnemy() {
			return Integer.parseInt(disguisedEnemyField.getText());
		}
		
		public ArrayList<int[]> getScout() {
			ArrayList<int[]> scoutList = new ArrayList<int[]>();
			int numberOfScouts= Integer.parseInt(scoutNumberField.getText());
			for(int i = 0; i < numberOfScouts; i++){
			    scoutList.add(new int[4]);
			    	for(int j=0; j<4; j++) {
			    		scoutList.get(i)[j] = Integer.parseInt(scoutFields.get(i)[j].getText());
			    	}
			}
			return scoutList;
		}
		
		
		public List<Integer> getGunner() {
			List<Integer> gunnerList = new ArrayList<Integer>();
			int numberOfGunners= Integer.parseInt(gunnerNumberField.getText());
			for(int i = 0; i < numberOfGunners; i++){
				gunnerList.add(Integer.parseInt(gunnerFields.get(i).getText()));
			}
			return gunnerList;
		}
		
		
		public void setBaseHP(int HP) {
			baseHPField.setText(String.valueOf(HP));
		}
		
		public void setIterations(int iterations) {
			durationField.setText(String.valueOf(iterations));
		}
		
		public void setEnemy(int enemy) {
			enemyField.setText(String.valueOf(enemy));
		}
		
		public void setDisguisedEnemy(int disguisedEnemy) {
			disguisedEnemyField.setText(String.valueOf(disguisedEnemy));
		}
		
		public void setNumberOfScouts(int scouts) {
			scoutNumberField.setText(String.valueOf(scouts));
		}
		
		public void setNumberOfGunners(int gunners) {
			gunnerNumberField.setText(String.valueOf(gunners));
		}
		
		public void setParameters() {
			this.scoutButtonOnClick.actionPerformed(new ActionEvent(scoutButton, ActionEvent.ACTION_PERFORMED, "test"));
		}
		
		
		
		public void setScout(int scoutID, int movement, int effectiveness, int trust, int vision) {
			scoutFields.get(scoutID)[0].setText(String.valueOf(movement));
			scoutFields.get(scoutID)[1].setText(String.valueOf(effectiveness));
			scoutFields.get(scoutID)[2].setText(String.valueOf(trust));
			scoutFields.get(scoutID)[3].setText(String.valueOf(vision));
		}
		
		
		public void setGunner(int gunnerID, int accuracy) {
			gunnerFields.get(gunnerID).setText(String.valueOf(accuracy));
		}

		
		

		   private class MyWindowListener implements WindowListener {

		      @Override
		      public void windowClosing(WindowEvent evt) {
		         System.exit(0);  
		      }


		      @Override public void windowOpened(WindowEvent evt) { }
		      @Override public void windowClosed(WindowEvent evt) { }
		      @Override public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
		      @Override public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
		      @Override public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
		      @Override public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
		   }	

		   public void drawMap() {
			   this.paint(getGraphics());
		   }
		   
		   @Override
		   public void paint(Graphics g) {

			   	int dimensions[] = this.map.getUpperBoundaries();
			   	IUnit [] [] units = this.map.getMap();
			   		if(units != null) {
			   			for(int i=0; i<dimensions[0]; i++) {
					   		for(int j=0; j<dimensions[1]; j++) {
					   			if(units[i][j]!=null) {
					   				switch(units[i][j].getUnitChar()) {
					   					case 'S':
					   						g.setColor(Color.RED);
					   						break;
					   					case 'E':
					   						g.setColor(Color.CYAN);
					   						break;
					   					case 'D':
					   						g.setColor(Color.YELLOW);
					   						break;
					   					case 'N':
					   						g.setColor(Color.BLUE);
					   						break;
					   					default:
					   						g.setColor(Color.white);
					   			
					   				}
					   			}
					   			else {
					   				g.setColor(Color.GREEN);
					   			}
				   				g.fillRect(i*10+350, j*10+200, 10, 10);
					   		}
					   	}	
			   		}
		   }
		   
		   
}