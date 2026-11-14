package restaurantrush.model;

public class RegularCustomer extends Customer{
	
	public RegularCustomer(String name) {
		super(name);
	}
	
	public int getSatisfactionLossPerTurn() {
		return 8;
	}
	
	public int applyDiscount() {
		return 0;

	}
}
