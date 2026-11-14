package restaurantrush.model;

public abstract class Food {
	private final String foodName;
	private final int priceInCents;
	
	protected Food(String name, int priceInCents) {
		this.foodName = name;
		this.priceInCents=priceInCents;
	}
	
	public String getName() {
		return foodName;
	}
	
	public int getPrice() {
		return priceInCents;
	}
	
	public abstract int getPrepUnits();
	

}
