import java.util.Random;


public class PlayerComputer extends Player {
	Board brd = Board.getInstance();
	Random rnd = new Random();
	
	PlayerComputer() {
		//do nothing
	}
	
	public boolean move(int x, int y) {
		Game_Controller gc = Game_Controller.getInstance();
		if(gc.getDifficulty() == 0) {
			StrategyEasy es = new StrategyEasy();
			es.move();
		}
		if(gc.getDifficulty() == 1) {
			StrategyHard sh = StrategyHard.getInstance();
			sh.move();
		}
		return true;
	}

	public boolean randomShipPlacement() {
		String[] names = {"Carrier" , "Battleship", "Destroyer", "Submarine", "PT boat"}; //new String[5];
		int[] sizes = {5, 4, 3, 3, 2};
		for(int i = 0; i < 5; i++) {
			int x = rnd.nextInt(10);
			int y = rnd.nextInt(10);
			int orientation = rnd.nextInt(3);
			if(!brd.computerAlreadyPlaced(names[i])) {
				while(!brd.computerPlaceShip(x, y, orientation, names[i], sizes[i])) {
					x = rnd.nextInt(10);
					y = rnd.nextInt(10);
					orientation = rnd.nextInt(3);
				}				
			}
		}
		return true;
	}
}
