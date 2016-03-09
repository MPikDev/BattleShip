
public class Board {
	private static Board instance = null;
	private Ship[][] boardLeftComputer = new Ship[10][10];
	private Ship[][] boardRightComputer = new Ship[10][10];
	private Ship[][] boardLeftPlayer = new Ship[10][10];
	private Ship[][] boardRightPlayer = new Ship[10][10];
	private boolean carrierPlaced = false;
	private boolean destroyerPlaced = false;
	private boolean battleshipPlaced = false;
	private boolean submarinePlaced = false;
	private boolean ptboatPlaced = false;
	private boolean computercarrierPlaced = false;
	private boolean computerdestroyerPlaced = false;
	private boolean computerbattleshipPlaced = false;
	private boolean computersubmarinePlaced = false;
	private boolean computerptboatPlaced = false;
	
	private Board() {
		initializeBoards();
	}
	
	public static Board getInstance() {
		if(instance == null) {
			instance = new Board();
		}
		return instance;
	}
	
	private void initializeBoards() {
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				boardLeftComputer[i][j] = new Ship();
			}
		}
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				boardLeftPlayer[i][j] = new Ship();
			}
		}
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				boardRightComputer[i][j] = new Ship();
			}
		}
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				boardRightPlayer[i][j] = new Ship();
			}
		}
	}
	
	public boolean checkWin() {
		boolean playerWin = true;
		boolean computerWin = true;
		for(int i = 0; i < 10; i++) {
			if(computerWin == false) {
				break;
			}
			for(int j = 0; j < 10; j++) {
				if(computerWin == false) {
					break;
				}
				if((boardLeftPlayer[i][j].isShip() == true) && (boardLeftPlayer[i][j].isHit() == false)) {
					computerWin = false;
				}
			}
		}
		for(int i = 0; i < 10; i++) {
			if(playerWin == false) {
				break;
			}
			for(int j = 0; j < 10; j++) {
				if(playerWin == false) {
					break;
				}
				if((boardLeftComputer[i][j].isShip() == true) && (boardLeftComputer[i][j].isHit() == false)) {
					playerWin = false;
					break;
				}
			}
		}
		if(computerWin == true || playerWin == true) {
			return true;
		}
		return false;
	}
	
	public boolean checkPlayerMoveValid(int x, int y) {
		Display disp = Display.getInstance();

		if(boardLeftComputer[x][y].isHit() == false) {
			return true;
		}
		else {
			disp.writeToConsole("The specified move was not valid. This position has already been hit!");
			return false;
		}
	}
	
	public boolean checkComputerMoveValid(int x, int y) {
		if(boardLeftPlayer[x][y].isHit() == false) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean movePlayer(int x, int y) {
		Game_Controller gc = Game_Controller.getInstance();
		Display disp = Display.getInstance();

		if(boardLeftComputer[x][y].isShip() == false) {
			SoundEffect se = new SoundEffect("Splash.wav");
			disp.writeToConsole("You missed!");
		}
		else {
			if(gc.getDifficulty() == 0) {
				SoundEffect se = new SoundEffect("Explosion.wav");
				disp.writeToConsole("You hit a " + boardLeftComputer[x][y].getName() + "!");
			}
			else if(gc.getDifficulty() == 1) {
				SoundEffect se = new SoundEffect("Explosion.wav");
				disp.writeToConsole("You hit a ship!");
			}
		}
		boardLeftComputer[x][y].hitSpace();
		boardRightPlayer[x][y].hitSpace();
		if(checkShipDestroyed(boardLeftComputer[x][y].getName())) {
			disp.writeToConsole("You destroyed a " + boardLeftComputer[x][y].getName() + "!");
		} 
		return true;
	}
	
	public boolean moveComputer(int x,int y) {
		Game_Controller gc = Game_Controller.getInstance();
		Display disp = Display.getInstance();

		if(boardLeftPlayer[x][y].isShip() == false) {
			SoundEffect se = new SoundEffect("Splash.wav");
			disp.writeToConsole("The computer missed!");
		}
		else {
			if(gc.getDifficulty() == 0) {
				disp.writeToConsole("The computer hit your " + boardLeftPlayer[x][y].getName() + "!");
				SoundEffect se = new SoundEffect("Explosion.wav");
			}
			else if(gc.getDifficulty() == 1) {
				disp.writeToConsole("The computer hit your ship!");
				SoundEffect se = new SoundEffect("Explosion.wav");
			}
		}
		boardRightComputer[x][y].hitSpace();
		boardLeftPlayer[x][y].hitSpace();
		return true;
	}
	
	public boolean placeShip(int x, int y, int orientation, String name, int size) {
		Display disp = Display.getInstance();

		if(name.equalsIgnoreCase("")) {
			disp.writeToConsole("You must select a ship first!");
			return false;
		}
		if(alreadyPlaced(name)) {
			return true;
		}
		if(!checkValidShipPlacement(x, y, orientation, size)) {
			disp.writeToConsole("Ship placement was not valid.");
			return false;
		}
		if(orientation == 0) {
			for(int i = 0; i < size; i++) {
				boardLeftPlayer[x+i][y] = new Ship(name, i, 0);
				shipPlaced(name);
			}
			return true;
		}
		if(orientation == 1) {
			for(int i = 0; i < size; i++) {
				boardLeftPlayer[x][y-i] = new Ship(name, i, 1);
				shipPlaced(name);
			}
			return true;
		}
		if(orientation == 2) {
			for(int i = 0; i < size; i++) {
				boardLeftPlayer[x-i][y] = new Ship(name, i, 2);
				shipPlaced(name);
			}
			return true;
		}
		if(orientation == 3) {
			for(int i = 0; i < size; i++) {
				boardLeftPlayer[x][y+i] = new Ship(name, i, 3);
				shipPlaced(name);
			}
			return true;
		}
		disp.writeToConsole("Something happened that wasn't supposed to happen.");
		disp.writeToConsole("The details are: x = " + x + ", y = " + y + ", orientation = " + orientation + ", name = " + name + ", and size = " + size + ",");
		return false;
	}
	
	public boolean computerPlaceShip(int x, int y, int orientation, String name, int size) {
		if(name == null) {
			return false;
		}
		if(computerAlreadyPlaced(name)) {
			return true;
		}
		if(!computerCheckValidShipPlacement(x, y, orientation, size)) {
			return false;
		}
		if(orientation == 0) {
			//Ship is being placed facing to the East
			for(int i = 0; i < size; i++) {
				boardLeftComputer[x+i][y] = new Ship(name, i, 0);
				computerShipPlaced(name);
			}
			return true;
		}
		if(orientation == 1) {
			//Ship is being placed facing to the North
			for(int i = 0; i < size; i++) {
				boardLeftComputer[x][y-i] = new Ship(name, i, 1);
				computerShipPlaced(name);
			}
			return true;
		}
		if(orientation == 2) {
			//Ship is being placed facing to the West
			for(int i = 0; i < size; i++) {
				boardLeftComputer[x-i][y] = new Ship(name, i, 2);
				computerShipPlaced(name);
			}
			return true;
		}
		if(orientation == 3) {
			//Ship is being placed facing to the South
			for(int i = 0; i < size; i++) {
				boardLeftComputer[x][y+i] = new Ship(name, i, 3);
				computerShipPlaced(name);
			}
			return true;
		}
		return false;
	}
	
	public int getNumPlayerShipsAlive() {	
		int tempNum = 0;
		if(isPlayerShipAlive("Carrier")) {
			tempNum++;
		}
		if(isPlayerShipAlive("Battleship")) {
			tempNum++;
		}
		if(isPlayerShipAlive("Destroyer")) {
			tempNum++;
		}
		if(isPlayerShipAlive("Submarine")) {
			tempNum++;
		}
		if(isPlayerShipAlive("pt boat")) {
			tempNum++;
		}
		return tempNum;
	}
	
	public int getNumComputerShipsAlive() {		
		int tempNum = 0;
		if(isComputerShipAlive("Carrier")) {
			tempNum++;
		}
		if(isComputerShipAlive("Battleship")) {
			tempNum++;
		}
		if(isComputerShipAlive("Destroyer")) {
			tempNum++;
		}
		if(isComputerShipAlive("Submarine")) {
			tempNum++;
		}
		if(isComputerShipAlive("pt boat")) {
			tempNum++;
		}
		return tempNum;
	}
	
	private boolean checkValidShipPlacement(int x, int y, int orientation, int size) {
		Display disp = Display.getInstance();

		boolean shipConflict = false;
		boolean shipFits = true;
		//Check to see if the ship actually fits inside the array
		if(orientation == 0) {
			if(x+size > 10) {
				shipFits = false;
			}
		}
		if(orientation == 1) {
			if(y-size < -1) {
				shipFits = false;
			}
		}
		if(orientation == 2) {
			if(x-size < -1) {
				shipFits = false;
			}
		}
		if(orientation == 3) {
			if(y+size > 10) {
				shipFits = false;
			}
		}
		if(shipFits == false) {
			disp.writeToConsole("The ship cannot be placed here. It does not fit in the grid.");
			return false;
		}
		//Check to see if the wanted ship placement will overlap any other ships
		if(orientation == 0) {
			for(int i = 0; i < size; i++) {
				if(boardLeftPlayer[x+i][y].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 1) {
			for(int i = 0; i < size; i++) {
				if(boardLeftPlayer[x][y-i].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 2) {
			for(int i = 0; i < size; i++) {
				if(boardLeftPlayer[x-i][y].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 3) {
			for(int i = 0; i < size; i++) {
				if(boardLeftPlayer[x][y+i].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(shipConflict == true) {
			disp.writeToConsole("The ship is overlapping another ship.");
		}
		
		if((shipConflict == false) && (shipFits == true)) {
			return true;
		}
		
		return false;
	}
	
	private boolean computerCheckValidShipPlacement(int x, int y, int orientation, int size) {
		boolean shipConflict = false;
		boolean shipFits = true;
		//Check to see if the ship actually fits inside the array
		if(orientation == 0) {
			if(x+size > 10) {
				shipFits = false;
			}
		}
		if(orientation == 1) {
			if(y-size < -1) {
				shipFits = false;
			}
		}
		if(orientation == 2) {
			if(x-size < -1) {
				shipFits = false;
			}
		}
		if(orientation == 3) {
			if(y+size > 10) {
				shipFits = false;
			}
		}
		if(shipFits == false) {
			return false;
		}
		//Check to see if the wanted ship placement will overlap any other ships
		if(orientation == 0) {
			for(int i = 0; i < size; i++) {
				if(boardLeftComputer[x+i][y].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 1) {
			for(int i = 0; i < size; i++) {
				if(boardLeftComputer[x][y-i].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 2) {
			for(int i = 0; i < size; i++) {
				if(boardLeftComputer[x-i][y].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(orientation == 3) {
			for(int i = 0; i < size; i++) {
				if(boardLeftComputer[x][y+i].isShip()) {
					shipConflict = true;
				}
			}
		}
		if(shipConflict == true) {
		}
		
		if((shipConflict == false) && (shipFits == true)) {
			return true;
		}
		
		return false;
	}
	
	public boolean shipPlaced(String s) {
		if(s.equals("Carrier")) {
			carrierPlaced = true;
		}
		if(s.equals("Battleship")) {
			battleshipPlaced = true;
		}
		if(s.equals("Destroyer")) {
			destroyerPlaced = true;
		}
		if(s.equals("Submarine")) {
			submarinePlaced = true;
		}
		if(s.equals("PT boat")) {
			ptboatPlaced = true;
		}
		return true;
	}
	
	public boolean computerShipPlaced(String s) {
		if(s.equals("Carrier")) {
			computercarrierPlaced = true;
		}
		if(s.equals("Battleship")) {
			computerbattleshipPlaced = true;
		}
		if(s.equals("Destroyer")) {
			computerdestroyerPlaced = true;
		}
		if(s.equals("Submarine")) {
			computersubmarinePlaced = true;
		}
		if(s.equals("PT boat")) {
			computerptboatPlaced = true;
		}
		return true;		
	}
	
	public boolean alreadyPlaced(String s) {
		if(s.equals("Carrier")) {
			return carrierPlaced;
		}
		if(s.equals("Battleship")) {
			return battleshipPlaced;
		}
		if(s.equals("Destroyer")) {
			return destroyerPlaced;
		}
		if(s.equals("Submarine")) {
			return submarinePlaced;
		}
		if(s.equals("PT boat")) {
			return ptboatPlaced;
		}
		return true;
	}
	
	public boolean computerAlreadyPlaced(String s) {
		if(s.equals("Carrier")) {
			return computercarrierPlaced;
		}
		if(s.equals("Battleship")) {
			return computerbattleshipPlaced;
		}
		if(s.equals("Destroyer")) {
			return computerdestroyerPlaced;
		}
		if(s.equals("Submarine")) {
			return computersubmarinePlaced;
		}
		if(s.equals("PT boat")) {
			return computerptboatPlaced;
		}
		return true;
	}
	
	public String getBoardLeftColor(int x, int y) {
		if(boardLeftPlayer[x][y].isShip()) {
			//The position is a ship
			if(boardLeftPlayer[x][y].getMark() == "X") {
				return "red";
				//Ship here was hit
			}
			else {
				return "grey";
				//Ship here was not hit. Display the ship
			}
		}
		if(boardLeftPlayer[x][y].getMark() == "O") {
			return "white";
			//Position was hit, but it was a miss
		}
		else {
			return "blue";
			//This position was not hit.
		}
	}
	
	public String getBoardRightColor(int x, int y) {
		if(boardLeftComputer[x][y].isShip()) {
			//The position is a ship
			if(boardLeftComputer[x][y].getMark() == "X") {
				return "red";
				//Ship here was hit
			}
			else {
				return "blue";
				//Ship here was not hit, But do not display the ship because that would be cheating.
			}
		}
		else if(boardLeftComputer[x][y].getMark() == "O") {
			return "white";
			//Position was hit, but it was a miss
		}
		else {
			return "blue";
			//This position was not hit.
		}
	}
	
	public boolean checkShipDestroyed(String shipName) {
		for(int i = 0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				if((boardLeftComputer[i][j].getName().equalsIgnoreCase(shipName)) && (boardLeftComputer[i][j].isHit() == false)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkPlayerShipDestroyed(String shipName) {
		for(int i = 0; i < 10; i++) {
			for(int j=0; j < 10; j++) {
				if((boardLeftPlayer[i][j].getName().equalsIgnoreCase(shipName)) && (boardLeftPlayer[i][j].isHit() == false)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean resetBoard() {
		initializeBoards();
		carrierPlaced = false;
		destroyerPlaced = false;
		battleshipPlaced = false;
		submarinePlaced = false;
		ptboatPlaced = false;
		computercarrierPlaced = false;
		computerdestroyerPlaced = false;
		computerbattleshipPlaced = false;
		computersubmarinePlaced = false;
		computerptboatPlaced = false;
		StrategyHard sh = StrategyHard.getInstance();
		sh.resetValues();
		return true;
	}
	
	public String getShipNameLeft(int x, int y) {
		return boardLeftPlayer[x][y].getName();
	}
	
	public String getShipNameRight(int x, int y) {
		return boardLeftComputer[x][y].getName();
	}
	
	public boolean getShipisHitLeft(int x, int y) {
		return boardLeftPlayer[x][y].isShip();
	}
	
	public int getShipPositionLeft(int x, int y) {
		return boardLeftPlayer[x][y].getPosition();
	}
	
	public int getShipPositionRight(int x, int y) {
		return boardLeftComputer[x][y].getPosition();
	}
	
	public int getShipOrientationLeft(int x, int y) {
		return boardLeftPlayer[x][y].getOrientation();
	}
	
	public int getShipOrientationRight(int x, int y) {
		return boardLeftComputer[x][y].getOrientation();
	}
	
	public boolean isPlayerShipAlive(String s) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if((boardLeftPlayer[i][j].getName().equalsIgnoreCase(s)) && (boardLeftPlayer[i][j].isHit() == false)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isComputerShipAlive(String s) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if((boardLeftComputer[i][j].getName().equalsIgnoreCase(s)) && (boardLeftComputer[i][j].isHit() == false)) {
					return true;
				}
			}
		}
		return false;
	}
}
