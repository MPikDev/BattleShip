import java.util.Random;


public class StrategyHard extends PlayerComputer implements ComputerStrategy {
	Random rnd = new Random();
	Board brd = Board.getInstance();
	public int hitFlag = 0; //
	public int firstHitX;
	public int firstHitY;
	public int nextHitX;
	public int nextHitY;	
	public int hitLastDir; // 0 = East, 1 = North, 2 = West, 3 = South
	public int hitInitDir;
	public boolean checkShipD;

	private static StrategyHard instance = null;

	public StrategyHard() {
		//do nothing
	}
	public static StrategyHard getInstance(){
		if(instance == null) {
			instance = new StrategyHard();
		}
		return instance;
	}
	
	public boolean notOutOfBound(int x,int y){
		if (x >= 0 && x <= 9 && y >= 0 && y <= 9){
			return true;
		}
		return false;
	}
	
	public void detOrient(int x, int y) {
		if(notOutOfBound(x+1, y) && (brd.getShipisHitLeft(x+1, y) && brd.getShipNameLeft(x,y).equals(brd.getShipNameLeft(x+1,y)) 
				&& brd.checkComputerMoveValid(x+1, y))){ 
			//Ship is facing to the East
			nextHitX = x+1;
			nextHitY = y;
			hitLastDir = 3;
			hitInitDir = 3;

		}
		else if(notOutOfBound(x, y-1) && (brd.getShipisHitLeft(x, y-1) && brd.getShipNameLeft(x,y).equals(brd.getShipNameLeft(x,y-1))
				&& brd.checkComputerMoveValid(x, y-1))) {
			//Ship is facing to the North
			nextHitX = x;
			nextHitY = y-1;
			hitLastDir = 2;
			hitInitDir = 2;
		}
		else if(notOutOfBound(x-1, y) && (brd.getShipisHitLeft(x-1, y) && brd.getShipNameLeft(x,y).equals(brd.getShipNameLeft(x-1,y))
				&& brd.checkComputerMoveValid(x-1, y))) {
			//Ship is facing to the West
			nextHitX = x-1;
			nextHitY = y;
			hitLastDir = 1;
			hitInitDir = 1;
		}
		else if(notOutOfBound(x, y+1) && (brd.getShipisHitLeft(x, y+1) && brd.getShipNameLeft(x,y).equals(brd.getShipNameLeft(x,y+1))
				&& brd.checkComputerMoveValid(x, y+1))) {
			//Ship is facing to the South
			nextHitX = x;
			nextHitY = y+1;
			hitLastDir = 0;
			hitInitDir = 0;
		
		}else{
			hitLastDir = -1;		
		}
	}
	
	
	public void getFinalDir(){
		if(brd.isPlayerShipAlive(brd.getShipNameLeft(firstHitX,firstHitY)) ){  
			if(hitInitDir == 2){
				nextHitX = firstHitX;
				nextHitY = firstHitY+1;
				hitLastDir = 0;

			}else if(hitInitDir == 3){
				nextHitX = firstHitX-1;
				nextHitY = firstHitY;
				hitLastDir = 1;

			}else if(hitInitDir == 0){
				nextHitX = firstHitX;
				nextHitY = firstHitY-1;
				hitLastDir = 2;

			}else if(hitInitDir == 1){
				nextHitX = firstHitX+1;
				nextHitY = firstHitY;
				hitLastDir = 3;

			}else{
				hitLastDir = -1;
			}
		}
	}
	
	public void hit(){
		hitFlag = 1;
	}

	public boolean checkSquaresAround(int x, int y){
		if((notOutOfBound(x, y+1) && notOutOfBound(x, y-1) && notOutOfBound(x-1, y) && notOutOfBound(x+1, y)) 
				&& (!brd.checkComputerMoveValid(x+1, y) && !brd.checkComputerMoveValid(x-1, y) 
				&& !brd.checkComputerMoveValid(x, y+1) && !brd.checkComputerMoveValid(x, y-1))){
			return true;
		}
		return false;
	}
	
	public void checkResetValues(int x, int y){
		if(brd.checkPlayerShipDestroyed(brd.getShipNameLeft(x, y))){
			hitFlag = 0;
			hitLastDir = -2;
			hitInitDir = -2;
			nextHitX = -1;
			nextHitY = -1;
			firstHitX = -1;
			firstHitY = -1;
		}
	}
	
	public void resetValues() {
		hitFlag = 0;
		hitLastDir = -2;
		hitInitDir = -2;
		nextHitX = -1;
		nextHitY = -1;
		firstHitX = -1;
		firstHitY = -1;
	}
	
	
	public boolean move() {
		int x = rnd.nextInt(10);
		int y = rnd.nextInt(10);
		while((brd.checkComputerMoveValid(x, y) == false) && ((checkSquaresAround(x,y) == false))) {
			x = rnd.nextInt(10);
			y = rnd.nextInt(10);
		}
		if((brd.getShipisHitLeft(x, y) && (hitFlag == 0) )){
			firstHitX = x;
			firstHitY = y;
			detOrient(x,y);
			hit();

		}else if((hitFlag == 1) && ((hitLastDir >= 0 && hitLastDir <= 3))){
			x = nextHitX;
			y = nextHitY;
			detOrient(x,y);

		}else if((hitFlag == 1) && (hitLastDir == -1)){
			getFinalDir();
			x = nextHitX;
			y = nextHitY;
			detOrient(x,y);

		}else if(hitLastDir == -1){
			checkResetValues(x, y);
		}

		if(notOutOfBound(x, y) && (brd.checkComputerMoveValid(x, y) && brd.isPlayerShipAlive(brd.getShipNameLeft(x, y)))){
			brd.moveComputer(x,y);
			checkResetValues(x, y);

		}else{
			x = rnd.nextInt(10);
			y = rnd.nextInt(10);
			while((brd.checkComputerMoveValid(x, y) == false) && ((checkSquaresAround(x,y) == false))) {
				x = rnd.nextInt(10);
				y = rnd.nextInt(10);
			}
			brd.moveComputer(x,y);
			checkResetValues(x, y);

		}
		return true;
	}
}

