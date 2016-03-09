import java.util.concurrent.TimeUnit;

public class Game_Controller {
	private static Game_Controller instance = null;
	//BoardLeftPlayer playerLeftBoard = BoardLeftPlayer.getInstance();
	//BoardRightPlayer playerRightBoard = BoardRightPlayer.getInstance();
	//BoardLeftComputer computerLeftBoard = BoardLeftComputer.getInstance();
	//BoardRightComputer computerRightBoard = BoardRightComputer.getInstance();
	private int difficulty = 0;
	private int gameMode = 0;
	private int gameState = 0;
	private int currentPlayer = 0;
	private int fleetMove;
	private int numPlayerShips;
	private int numComputerShips;
	private int shipPlacementStep = 0;
	private int position_x, position_y;
	private int shipSize;
	private String shipName;
	private Board brd = Board.getInstance();
	
	Game_Controller() {
	}
	
	public static Game_Controller getInstance() {
		if(instance == null) {
			instance = new Game_Controller();
		}
		return instance;
	}
	
	public boolean buttonLeftPressed(int x, int y) {
		Display disp = Display.getInstance();
		if(gameState != 0) {
			return false;
		}
		shipPlacementStep++;
		if(shipPlacementStep == 1) {
			position_x = x;
			position_y = y;
		}
		if(shipPlacementStep == 2) {
			shipPlacementStep = 0;
			int orientation = determineOrientation(position_x, position_y, x, y);
			if(brd.placeShip(position_x, position_y, orientation, shipName, shipSize)) {
				//System.out.println("Ship placement was successful.");
				disp.writeToConsole("The ship placement was successful.");
				return true;
			}
			//System.out.println("Ship placement was unsuccessful.");	
			disp.writeToConsole("The ship placement was unsuccessful.");
			return false;
		}
		return true;
	}
	
	public boolean activeShipButtonPressed(String n, int s) {
		setShipName(n);
		setShipSize(s);
		return true;
	}
	
	public boolean randomButtonPressed() {
		PlayerHuman pm = new PlayerHuman();
		if(gameState == 0) {
			pm.randomShipPlacement();
		}
		if(gameState == 1) {
			//generate random position for attack move.
			return pm.randomMove();
		}
		return false;
	}

	public boolean buttonRightPressed(int x, int y) {
		Display disp = Display.getInstance();
		if(gameMode == 0) {
			//game mode is in classic mode
			if(gameState == 0) {
				//Game is in the ship placement mode
			}
			else if(gameState == 1 && currentPlayer == 0) {
				//It is the player's turn
				boolean valid = playerAttackMove(x,y);
				disp.updateButtons();
				if(checkWin()) {
					gameState = 2;
					//System.out.println("The player wins!!");
					disp.writeToConsole("The player wins!!");
					return true;
				}
				changeCurrentPlayer();
				if(valid) {
					computerAttackMove();
					System.out.println("");
					if(checkWin()) {
						gameState = 2;
						//System.out.println("The computer wins!!");
						disp.writeToConsole("The computer wins!!");
						return true;
					}
				}
				changeCurrentPlayer();
				System.out.println("");
			}
			else if(gameState == 1 && currentPlayer == 1) {
				//It is the computer turn
				//System.out.println("It is the computer's turn. Please wait your turn.");
				disp.writeToConsole("It is the computer's turn. Please wait your turn.");
			}
			else if(gameState == 2) {
				//someone has won
			}
		}
		else if(gameMode == 1 && fleetMove <= getNumPlayerShipsAlive()) {
			//game mode is in fleet mode
			if(gameState == 0) {
				//Game is in the ship placement mode
				//Do nothing
			}
			if(gameState == 1 && currentPlayer == 0) {
				//It is the player's turn
				boolean valid = playerAttackMove(x,y);
				disp.updateButtons();
				if(checkWin()) {
					gameState = 2;
					//System.out.println("The player wins!!");
					disp.writeToConsole("The player wins!!");
					return true;
				}
				if(valid) {
					fleetMove++; 
				}
			}
			if(gameState == 1 && currentPlayer == 1) {
				//It is the computer turn
				//System.out.println("It is the computer's turn. Please wait your turn.");
				disp.writeToConsole("It is the computer's turn. Please wait your turn!!");
			}
			if(fleetMove >= getNumPlayerShipsAlive()) {
				//System.out.println("");
				disp.writeToConsole("");
				changeCurrentPlayer();
				for(int i = 0; i < getNumComputerShipsAlive(); i++) {
					computerAttackMove();
					if(checkWin()) {
						//System.out.println("The computer wins!!");
						disp.writeToConsole("The computer wins!!");
						gameState = 2;
						return true;
					}
				}
				//System.out.println("");
				disp.writeToConsole("");
				changeCurrentPlayer();
				fleetMove = 0;
			}
			if(gameState == 2) {
				//someone has won.
			}
			
		}
	return true;
	}
	
	public boolean playerReadyTrigger() {
		PlayerComputer pc = new PlayerComputer();
		pc.randomShipPlacement();
		if(gameState == 0) {
			gameState = 1;
			return true;
		}
		return false;
	}
	
	public boolean resetButtonPressed() {
		gameState = 0;
		currentPlayer = 0;
		fleetMove = 0;
		return brd.resetBoard();
	}
	
	public boolean playerAttackMove(int x, int y) {
		Display disp = Display.getInstance();
		PlayerHuman pm = new PlayerHuman();
		boolean valid = false;
		if(pm.move(x,y)) {
			valid = true;
		}
		else {
			//System.out.println("That is not a valid move. Try again!");
			disp.writeToConsole("That is not a valid move. Try again!");
		}
		return valid;
	}
	
	public boolean computerAttackMove() {
		/*
		try {
		    Thread.sleep(250);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		*/
		PlayerComputer pc = new PlayerComputer();
		pc.move(-1,-1);
		return true;
	}
	
	private boolean checkWin() {
		return brd.checkWin();
	}
	
	private int determineOrientation(int x1, int y1, int x2, int y2) {
		int orientation = -1;
		if(((x1-x2) == -1) && ((y1-y2) == 0)) {
			//Ship is facing to the East
			orientation = 0;
		}
		if(((x1-x2) == 0) && ((y1-y2) == 1)) {
			//Ship is facing to the North
			orientation = 1;
		}
		if(((x1-x2) == 1) && ((y1-y2) == 0)) {
			//Ship is facing to the West
			orientation = 2;
		}
		if(((x1-x2) == 0) && ((y1-y2) == -1)) {
			//Ship is facing to the South
			orientation = 3;
		}
		return orientation;
	}
	
	public int getNumPlayerShipsAlive() {
		return brd.getNumPlayerShipsAlive();
	}
	
	public int getNumComputerShipsAlive() {
		return brd.getNumComputerShipsAlive();
	}
	
	public String getColorBoardLeft(int x, int y) {
		return brd.getBoardLeftColor(x, y);
	}
	
	public String getColorBoardRight(int x, int y) {
		return brd.getBoardRightColor(x, y);
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public boolean setGameMode(int i) {
		gameMode = i;
		return true;
	}
	
	public void setDifficulty(int d) {
		difficulty = d;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(int c) {
		currentPlayer = c;
	}
	
	private void changeCurrentPlayer() {
		if(currentPlayer == 0) 
			currentPlayer = 1;
		else
			currentPlayer = 0;
	}
	
	private void setShipSize(int s) {
		shipSize = s;
	}

	private void setShipName(String shipName) {
		this.shipName = shipName;
	}
}
