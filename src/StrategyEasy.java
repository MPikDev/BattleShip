import java.util.Random;

public class StrategyEasy extends PlayerComputer implements ComputerStrategy {
	Random rnd = new Random();
	Board brd = Board.getInstance();
	StrategyEasy() {
		//do nothing
	}
	
	public boolean move() {
		int x = rnd.nextInt(10);
		int y = rnd.nextInt(10);
		while(brd.checkComputerMoveValid(x, y) == false) {
			x = rnd.nextInt(10);
			y = rnd.nextInt(10);
		}
		brd.moveComputer(x, y);
		return true;
	}
}
