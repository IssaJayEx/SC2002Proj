package restaurantrush.model;

public class Burger extends Food{
	
	public Burger() {
		super("Burger", 1500);
	}
	
	public int getPrepUnits() {
		return 1;
	}

}