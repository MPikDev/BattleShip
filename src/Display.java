import javax.sound.sampled.*;

import java.io.File;
import java.net.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;


public class Display extends JFrame
{
	private static Display instance = null;
	private Game_Controller gc = Game_Controller.getInstance();
	
	private ImagePanel contentPane;
  	private final Button[][] location;
  	private final Button[][] rightgrid;
  	private final Label[][] rightLabels;
  	private final Label[][] locationLabels;

  	private JLabel statusBar;
    private JMenuBar bar;
  	private JMenu gameMenu, playerMode, PVC;
  	private JMenuItem PVP, newFleetGame, newClassicGame, playerSubEZ, playerSubHRD;
  	private String path;
  	public JTextArea textArea;
  public	ImagePanel panel;
  
  	private Display () {  		

	    this.location = new Button[11][11];
	    this.locationLabels = new Label[11][11];
	    this.rightgrid = new Button[11][11];
	    this.rightLabels = new Label[11][11];
		path = new File(".").getAbsolutePath();
		//System.out.println("The system path is: " + path);
		//fOut = new FileOutputStream(path + "/data/" + symbol+".csv");
	    setJMenuBar(bar = new JMenuBar());
	    setResizable(false);
	    setTitle("BattleShip");
	    setDefaultCloseOperation(3); 
	    this.contentPane = new ImagePanel(path+"/images/dpn1.jpg",580,1100,1200,1200);
	    this.contentPane.setLayout(new BorderLayout(2, 2));
	    setContentPane(this.contentPane);
	    this.statusBar = new JLabel(new ImageIcon(path+"/images/logo.jpg"));
	    statusBar.setHorizontalAlignment(SwingConstants.CENTER);
	    this.statusBar.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.DARK_GRAY));
	    this.contentPane.add(this.statusBar, "North");
	    
	    ImagePanel entLabel = new ImagePanel("");

	    //new jpanel
	    ImagePanel ships = new ImagePanel("");


	    //Carrier
	    ImageIcon shipImage0 = new ImageIcon(path + "/images/originals/carrier.gif");
	    JButton ship0 = new JButton("Carrier (5)", shipImage0);
	    ship0.setActionCommand("Carrier");
	    ship0.addActionListener(this.buttonListener);
	    ship0.setVerticalTextPosition(SwingConstants.BOTTOM);
	    ship0.setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    //Battleship Icon
	    ImageIcon shipImage1 = new ImageIcon(path + "/images/originals/battleship.gif");
	    JButton ship1 = new JButton("Battleship (4)", shipImage1);
	    ship1.setActionCommand("Battleship");
	    ship1.addActionListener(this.buttonListener);
	    ship1.setVerticalTextPosition(SwingConstants.BOTTOM);
	    ship1.setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    ImageIcon shipImage2 = new ImageIcon(path + "/images/originals/destroyer.gif");
	    JButton ship2 = new JButton("Destroyer (3)", shipImage2);
	    ship2.setActionCommand("Destroyer");
	    ship2.addActionListener(this.buttonListener);
	    ship2.setVerticalTextPosition(SwingConstants.BOTTOM);
	    ship2.setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    ImageIcon shipImage3 = new ImageIcon(path + "/images/originals/submarine.gif");
	    JButton ship3 = new JButton("Submarine (3)", shipImage3);
	    ship3.setActionCommand("Submarine");
	    ship3.addActionListener(this.buttonListener);
	    ship3.setVerticalTextPosition(SwingConstants.BOTTOM);
	    ship3.setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    ImageIcon shipImage4 = new ImageIcon(path + "/images/originals/patrol.gif");
	    JButton ship4 = new JButton("PT boat (2)", shipImage4);
	    ship4.setActionCommand("PT boat");
	    ship4.addActionListener(this.buttonListener);
	    ship4.setVerticalTextPosition(SwingConstants.BOTTOM);
	    ship4.setHorizontalTextPosition(SwingConstants.CENTER);
	    
	    
	    //throws all the buttons into a vertical box layout
	    ships.setLayout(new GridLayout(5,1));
	    
	    //add the buttons to the box layout
	    ships.add(ship0);
	    ships.add(ship1);
	    ships.add(ship2);
	    ships.add(ship3);
	    ships.add(ship4);
	    ships.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.DARK_GRAY));

	    this.contentPane.add(ships, "West");
	    GridBagLayout gbl_entLabel = new GridBagLayout();
	    gbl_entLabel.columnWidths = new int[]{119, 0};
	    gbl_entLabel.rowHeights = new int[]{29, 29, 29, 0};
	    gbl_entLabel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
	    gbl_entLabel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
	    entLabel.setLayout(gbl_entLabel);
	    JButton buttonRandom = new JButton("Random");
	    buttonRandom.addActionListener(this.buttonListener);
	    //Bottom buttons for starting game and resetting
	    JButton buttonPlay = new JButton("Ready? Play!");
	    buttonPlay.addActionListener(this.buttonListener);
	    
	    JButton buttonReset = new JButton("Reset");
	    buttonReset.addActionListener(this.buttonListener);
	    
	        GridBagConstraints gbc_buttonReset = new GridBagConstraints();
	        gbc_buttonReset.anchor = GridBagConstraints.NORTH;
	        gbc_buttonReset.insets = new Insets(0, 0, 5, 0);
	        gbc_buttonReset.gridx = 0;
	        gbc_buttonReset.gridy = 0;
	        
	    
	    GridBagConstraints gbc_buttonPlay = new GridBagConstraints();
	    gbc_buttonPlay.anchor = GridBagConstraints.NORTHWEST;
	    gbc_buttonPlay.insets = new Insets(0, 0, 5, 0);
	    gbc_buttonPlay.gridx = 0;
	    gbc_buttonPlay.gridy = 1;
	    
	    GridBagConstraints gbc_buttonRandom = new GridBagConstraints();
	    gbc_buttonRandom.anchor = GridBagConstraints.NORTH;
	    gbc_buttonRandom.gridx = 0;
	    gbc_buttonRandom.gridy = 2;
//this is the treminal and play button set up	    
	    panel = new ImagePanel(new ImageIcon(path+"/images/dp2.gif").getImage());
	    panel.setBackground(Color.darkGray);
	    panel.setSize(200,200);
	    panel.setLayout(new BorderLayout(0, 0));
	    ImagePanel gameb = new ImagePanel(new ImageIcon(path+"/images/dn2.gif").getImage());
	    panel.add(gameb, "North");
	    gameb.add(buttonReset, gbc_buttonReset);
	    gameb.add(buttonPlay, gbc_buttonPlay);
	    gameb.add(buttonRandom,gbc_buttonRandom);
	    gameb.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.DARK_GRAY));

	    getContentPane().add(panel,BorderLayout.SOUTH);
	    
	    String text = "Welcome to battleship\n";
	    Font f = new Font(Font.MONOSPACED, Font.PLAIN, 13);
	    textArea = new JTextArea(text, 22, 90);
	    textArea.setEditable(false);
	    textArea.setFont(f);
	    textArea.setCaretPosition(textArea.getDocument().getLength());
	    JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    //scrollPane.isWheelScrollingEnabled();
	    scrollPane.setBorder(BorderFactory.createTitledBorder(null ,"Battle Report", TitledBorder.LEFT, TitledBorder.TOP));

	    scrollPane.setPreferredSize(new Dimension(80, 180));
	    //textArea.setLineWrap(true);
	    //scrollPane.setSize(80, 8);
	    panel.add(scrollPane);
//this is the end of the teminal and buttion lay out
	    //// Menu Bar -- 
	    gameMenu = new JMenu("Game Mode");
	    //add classic mode to menu
	    newClassicGame = new JMenuItem("Classic Mode");
	    newClassicGame.addActionListener(this.buttonListener);
		gameMenu.add(newClassicGame);
		//add fleet mode to menu list
		newFleetGame = new JMenuItem("Fleet Mode");
		newFleetGame.addActionListener(this.buttonListener);
		gameMenu.add(newFleetGame);
		
		
		playerMode = new JMenu("Player Mode");
		PVC  = new JMenu("Player vs. computer!");
		PVC.addActionListener(this.buttonListener);
		playerMode.add(PVC);
		
		//PVP  = new JMenuItem("Player vs. player!");
		//PVP.addActionListener(this.buttonListener);
		
		
		playerSubEZ = new JMenuItem("Easy");
		playerSubEZ.addActionListener(this.buttonListener);
		PVC.add(playerSubEZ);
		
		playerSubHRD = new JMenuItem("Hard");
		playerSubHRD.addActionListener(this.buttonListener);
		PVC.add(playerSubHRD);
		
		playerMode.add(PVC);
		//playerMode.add(PVP);
		
		//add menu to the menu bar GUI
		bar.add(gameMenu);
		bar.add(playerMode);
	    makeBoard();
  }


  	public static Display getInstance() {
  		if(instance == null) {
  			instance = new Display();
  		}
  		return instance;
  	}
  	
  	public void updateButtons() {
  		
  		//System.out.println("buttons updated");
  		for(int i = 1; i < 11; i++) {
  			for(int j = 1; j < 11; j++) {
  				
  				String temp = gc.getColorBoardLeft(i-1, j-1);
  				if(temp.equalsIgnoreCase("blue")) {
  					rightgrid[i][j].setIcon(new ImageIcon(path + "/images/water/water.png"));
  				}
  				/*
  				else if(temp.equalsIgnoreCase("grey")) {
  					rightgrid[i][j].setIcon(new ImageIcon("C:/Users/chris_000/workspace/Battleship/src/grey.png"));
  				}
  				*/
  				else if(temp.equalsIgnoreCase("grey")) {
  					applyShipIconLeft(i,j);
  				}
  				else if(temp.equalsIgnoreCase("red")) {
  					rightgrid[i][j].setIcon(new ImageIcon(path + "/images/marks/hit.gif"));
  				}
  				else if(temp.equalsIgnoreCase("white")) {
  					rightgrid[i][j].setIcon(new ImageIcon(path + "/images/marks/miss.gif"));
  				}
  				
  			}
  		}
  		for(int i = 1; i < 11; i++) {
  			for(int j = 1; j < 11; j++) {
  				String temp = gc.getColorBoardRight(i-1, j-1);
  				if(temp.equalsIgnoreCase("blue")) {
  					location[i][j].setIcon(new ImageIcon(path + "/images/water/water.png"));
  				}
  				/*
  				else if(temp.equalsIgnoreCase("grey")) {
  					location[i][j].setIcon(new ImageIcon("C:/Users/chris_000/workspace/Battleship/src/grey.png"));
  				}
  				*/
  				else if(temp.equalsIgnoreCase("grey")) {
  					applyShipIconRight(i,j);
  				}
  				else if(temp.equalsIgnoreCase("red")) {
  					location[i][j].setIcon(new ImageIcon(path + "/images/marks/hit.gif"));
  				}
  				else if(temp.equalsIgnoreCase("white")) {
  					location[i][j].setIcon(new ImageIcon(path + "/images/marks/miss.gif"));
  				}			  
  			}
  			
  		}
  		
  	}
  	
  	public boolean applyShipIconLeft(int x, int y) {

  		Board brd = Board.getInstance();
		String name = brd.getShipNameLeft(x-1,y-1);
		int shipPosition = brd.getShipPositionLeft(x-1, y-1);
		int shipOrientation = brd.getShipOrientationLeft(x-1, y-1);
		String orientation = "";
		if(shipOrientation == 0) {
			orientation = "South/";
		}
		else if(shipOrientation == 1) {
			orientation = "West/";
		}
		else if(shipOrientation == 2) {
			orientation = "North/";
		}
		else if(shipOrientation == 3) {
			orientation = "East/";
		}
		if(name.equalsIgnoreCase("Carrier")) {
			if(shipPosition == 0) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier5.gif"));
			}
			if(shipPosition == 1) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier4.gif"));
			}
			if(shipPosition == 2) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier3.gif"));
			}
			if(shipPosition == 3) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier2.gif"));
			}
			if(shipPosition == 4) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier1.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Battleship")) {
			if(shipPosition == 0) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship4.gif"));
			}
			if(shipPosition == 1) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship3.gif"));
			}
			if(shipPosition == 2) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship2.gif"));
			}
			if(shipPosition == 3) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship1.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Destroyer")) {
			if(shipPosition == 0) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer3.gif"));
			}
			if(shipPosition == 1) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer2.gif"));
			}
			if(shipPosition == 2) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer1.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Submarine")) {
			if(shipPosition == 0) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine3.gif"));
			}
			if(shipPosition == 1) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine2.gif"));
			}
			if(shipPosition == 2) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine1.gif"));
			}
		}
		else if(name.equalsIgnoreCase("PT boat")) {
			if(shipPosition == 0) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/patrol/" + orientation + "patrol2.gif"));
			}
			if(shipPosition == 1) {
				rightgrid[x][y].setIcon(new ImageIcon(path + "/images/patrol/" + orientation + "patrol1.gif"));
			}
		}
		return true;
  	}
  	
  	public boolean applyShipIconRight(int x, int y) {

  		Board brd = Board.getInstance();
		String name = brd.getShipNameRight(x-1,y-1);
		int shipPosition = brd.getShipPositionRight(x-1, y-1);
		int shipOrientation = brd.getShipOrientationRight(x-1, y-1);
		String orientation = ""; 
		if(shipOrientation == 0) {
			orientation = "East/";
		}
		else if(shipOrientation == 1) {
			orientation = "North/";
		}
		else if(shipOrientation == 2) {
			orientation = "West/";
		}
		else if(shipOrientation == 3) {
			orientation = "South/";
		}
		if(name.equalsIgnoreCase("Carrier")) {
			if(shipPosition == 0) {
				location[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier1.gif"));
			}
			if(shipPosition == 1) {
				location[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier2.gif"));
			}
			if(shipPosition == 2) {
				location[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier3.gif"));
			}
			if(shipPosition == 3) {
				location[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier4.gif"));
			}
			if(shipPosition == 4) {
				location[x][y].setIcon(new ImageIcon(path + "/images/carrier/" + orientation + "carrier5.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Battleship")) {
			if(shipPosition == 0) {
				location[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship1.gif"));
			}
			if(shipPosition == 1) {
				location[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship2.gif"));
			}
			if(shipPosition == 2) {
				location[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship3.gif"));
			}
			if(shipPosition == 3) {
				location[x][y].setIcon(new ImageIcon(path + "/images/battleship/" + orientation + "battleship4.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Destroyer")) {
			if(shipPosition == 0) {
				location[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer1.gif"));
			}
			if(shipPosition == 1) {
				location[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer2.gif"));
			}
			if(shipPosition == 2) {
				location[x][y].setIcon(new ImageIcon(path + "/images/destroyer/" + orientation + "destroyer3.gif"));
			}
		}
		else if(name.equalsIgnoreCase("Submarine")) {
			if(shipPosition == 0) {
				location[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine1.gif"));
			}
			if(shipPosition == 1) {
				location[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine2.gif"));
			}
			if(shipPosition == 2) {
				location[x][y].setIcon(new ImageIcon(path + "/images/submarine/" + orientation + "submarine3.gif"));
			}
		}
		else if(name.equalsIgnoreCase("PT boat")) {
			if(shipPosition == 0) {
				location[x][y].setIcon(new ImageIcon(path + "/images/patrol/" + orientation + "patrol1.gif"));
			}
			if(shipPosition == 1) {
				location[x][y].setIcon(new ImageIcon(path + "/images/patrol/" + orientation + "patrol2.gif"));
			}
		}
		return true;
  	}
  
    public URL ship0Image() {
    	URL image = SoundEffect.class.getResource("carrier.gif");
    	System.out.println(image);
    	return image;
    }
    public URL ship1Image() {
    	URL image = SoundEffect.class.getResource("battleship.gif");
    	return image;
    }
    public URL ship2Image() {
    	URL image = SoundEffect.class.getResource("seawolf.gif");
    	return image;
    }
    public URL ship3Image() {
    	URL image = SoundEffect.class.getResource("submarine.gif");
    	return image;
    }
    public URL ship4Image() {
    	URL image = SoundEffect.class.getResource("patrol.gif");
    	return image;
    }
    public URL waterImage() {
    	URL image = SoundEffect.class.getResource("water.png");
    	return image;
    }
    
    public void makeBoard() {
    	ImagePanel centerPane = new ImagePanel(new ImageIcon(path+"/images/back.jpg").getImage());
    	centerPane.setBackground(Color.black);
    	centerPane.setBorder(null);
    	this.contentPane.add(centerPane, BorderLayout.CENTER);
    	centerPane.setLayout(new GridLayout(0, 2, 22, 0));
    	ImagePanel boarddisplay = new ImagePanel(path+"/images/black.jpg", 1000, 1200, 100, 120);
    	boarddisplay.setBackground(Color.black);
    	boarddisplay.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
    	boarddisplay.setLayout(new GridLayout(11, 11, 2, 2));
    	centerPane.add(boarddisplay);
    	//layoutContainer(centerPane);
    	for (int row = 0; row < 11; row++) {
    		for (int col = 0; col < 11; col++) {
    			if ( row == 0 && col == 0){
						this.rightLabels[row][col] = new Label();
						this.rightLabels[row][col].setBackground(Color.WHITE);
        			boarddisplay.add(this.rightLabels[row][col]);
    			}
        		else if((row == 0)){
    				if(col > 0){
    					this.rightLabels[row][col] = new Label(""+col);
						this.rightLabels[row][col].setBackground(Color.WHITE);
            			boarddisplay.add(this.rightLabels[row][col]);
    				}
    			}
    			else if((col == 0)){
    				if(row > 0){
    					this.rightLabels[row][col] = new Label(""+(char)(64+row));
						this.rightLabels[row][col].setBackground(Color.WHITE);
            			boarddisplay.add(this.rightLabels[row][col]);
    					}
    			}
    			else{
    				this.rightgrid[row][col] = new Button(row-1, col-1);
    				this.rightgrid[row][col].setActionCommand("left" + " " + (row-1) + " " + (col-1));
        			this.rightgrid[row][col].addActionListener(this.buttonListener);
        			boarddisplay.add(this.rightgrid[row][col]);
    			}
    		}
    	}
    	
    	ImagePanel boardGame = new ImagePanel(path+"/images/black.jpg", 1000, 1200, 100, 120);
    	
	    boardGame.setBackground(Color.BLACK);
	    boardGame.setBorder(new MatteBorder(2, 2, 2, 2, Color.black));
	    boardGame.setLayout(new GridLayout(11, 11, 2, 2));
	    centerPane.add(boardGame);
	    for (int row = 0; row < 11; row++) {
	    	for (int col = 0; col < 11; col++) {
	    		if ( row == 0 && col == 0){
					this.locationLabels[row][col] = new Label();
					this.locationLabels[row][col].setBackground(Color.WHITE);
        			boardGame.add(this.locationLabels[row][col]);
    			}
        		else if((row == 0)){
    				if(col > 0){
    					this.locationLabels[row][col] = new Label(""+col);
						this.locationLabels[row][col].setBackground(Color.WHITE);
    					boardGame.add(this.locationLabels[row][col]);
    				}
    			}
    			else if((col == 0)){
    				if(row > 0){
    					this.locationLabels[row][col] = new Label(""+(char)(64+row));
						this.locationLabels[row][col].setBackground(Color.WHITE);
    					boardGame.add(this.locationLabels[row][col]);
    					}
    			}
    			else{
    				this.location[row][col] = new Button(row-1, col-1);
    				this.location[row][col].setActionCommand("right" + " " + (row-1) + " " + (col-1));
        			this.location[row][col].addActionListener(this.buttonListener);
        			boardGame.add(this.location[row][col]);
    			}
	    	}
	    }
	    updateButtons();   
    }
    
    public void writeToConsole(String s) {
    	textArea.append(s + "\n");
    }
  
    ActionListener buttonListener = new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		Display disp = Display.getInstance();

    		if(e.getActionCommand().equalsIgnoreCase("Classic Mode")) {
    			disp.writeToConsole("The game has been set to Classic Mode");
    			gc.setGameMode(0);
    			gc.resetButtonPressed();
    			updateButtons();
    		}
	    	else if(e.getActionCommand().equalsIgnoreCase("Fleet Mode")) {
	    		disp.writeToConsole("The game has been set to Fleet Mode");
	    		gc.setGameMode(1);
	    		gc.resetButtonPressed();
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Reset")) {
	    		disp.writeToConsole("Game Reset");
	    		gc.resetButtonPressed();
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Ready? Play!")){
	    		disp.writeToConsole("Ready to Play!\n");
	    		gc.playerReadyTrigger();
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Random")) {
	    		gc.randomButtonPressed();
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Carrier")) {
	    		gc.activeShipButtonPressed("Carrier", 5);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Battleship")) {
	    		gc.activeShipButtonPressed("Battleship", 4);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Destroyer")) {
	    		gc.activeShipButtonPressed("Destroyer", 3);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Submarine")) {
	    		gc.activeShipButtonPressed("Submarine", 3);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("PT boat")) {
	    		gc.activeShipButtonPressed("PT boat", 2);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Hard")) {
	    		gc.resetButtonPressed();
	    		gc.setDifficulty(1);
	    		updateButtons();
	    	}
	    	else if (e.getActionCommand().equalsIgnoreCase("Easy")) {
	    		gc.resetButtonPressed();
	    		gc.setDifficulty(0);
	    		updateButtons();
	    	}
	    	else {
	    		String[] s = e.getActionCommand().split(" ");
	    		String name = s[0];
	    		int row = Integer.valueOf(s[1]).intValue();
	    		int col = Integer.valueOf(s[2]).intValue();
	    		//Display.this.location[row][col].setIcon(new ImageIcon(humImage()));
	    		if(name.equals("left")) {
	    			gc.buttonLeftPressed(row, col);
	    		}
	    		if(name.equals("right")) {
	    			gc.buttonRightPressed(row, col);
	    		}
	    		updateButtons();
	    	}
    	}
    };
}