package restaurantrush.model;

public class VIPCustomer extends Customer{
	
	public VIPCustomer(String name) {
		super(name);
	}
	
	public int getSatisfactionLossPerTurn() {
		return 5;
	}
	
	public int applyDiscount() {
		return 10;

	}
}