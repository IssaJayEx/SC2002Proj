package restaurantrush.model;

public class Pasta extends Food{
	
	public Pasta() {
		super("Pasta", 1800);
	}
	
	public int getPrepUnits() {
		return 2;
	}

}