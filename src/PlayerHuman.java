import java.util.Random;
public class PlayerHuman extends Player {
	Board brd = Board.getInstance();
	Random rnd = new Random();
	
	PlayerHuman() {
		//do nothing
	}
	
	public boolean move(int x, int y) {
		if(brd.checkPlayerMoveValid(x, y)) {
			//continue with move
			brd.movePlayer(x,y);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean randomMove() {
		Game_Controller gc = Game_Controller.getInstance();
		int x = rnd.nextInt(10);
		int y = rnd.nextInt(10);
		while(brd.checkPlayerMoveValid(x, y) == false) {
			x = rnd.nextInt(10);
			y = rnd.nextInt(10);
		}
		gc.buttonRightPressed(x, y);
		return true;
	}
	
	public boolean randomShipPlacement() {
		String[] names = {"Carrier" , "Battleship", "Destroyer", "Submarine", "PT boat"}; //new String[5];
		int[] sizes = {5, 4, 3, 3, 2};
		for(int i = 0; i < 5; i++) {
			int x = rnd.nextInt(10);
			int y = rnd.nextInt(10);
			int orientation = rnd.nextInt(4);
			if(!brd.alreadyPlaced(names[i])) {
				while(!brd.placeShip(x, y, orientation, names[i], sizes[i])) {
					x = rnd.nextInt(10);
					y = rnd.nextInt(10);
					orientation = rnd.nextInt(3);
				}				
			}
		}
		return true;
	}
}
