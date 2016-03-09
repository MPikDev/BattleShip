
public class Ship {//This is only a section of the whole ship
	private String name = "";
	private int orientation;
	//private int size;
	private boolean isHit;
	private String mark = "-";
	private int position;
	
	
	Ship() {
		//default constructor
	}
	
	Ship(String n, int p, int o) {//used to be Ship(String n, int o, int s) then it was Ship(String n).
		name = n;
		orientation = o;
		//size = s;
		position = p;
		isHit = false;
	}
	
	/* Being handled in the board class
	public boolean placeShip(int start, int orientation) {
		//do things
		return true;
	}*/
	
	public int getNumShipsAlive() {
		return 5;
	}
	
	public boolean isHit() {
		return isHit;
	}
	
	public boolean isShip() {
		if(name.equals("")) {
			return false;
		}
		return true;
	}
	
	public boolean hitSpace() {
		isHit = true;
		if(isShip()) {
			mark = "X";
			isHit = true;
			return true;
		}
		else {
			mark = "O";
			isHit = true;
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getMark() {
		return mark;
	}
	
	public int getPosition() {
		return position;
	}
	
	public int getOrientation() {
		return orientation;
	}
}
