package restaurantrush.model;

public class PricingService {
	private boolean happyHourActive = false;
	
	public void setHappyHourActive(boolean active) {
		happyHourActive = active;
	}
	
	public int calculateFinalPrice(Customer c, Food f) {
		double price = f.getPrice() * (100-c.applyDiscount())/100.0;
		if (happyHourActive) {
			price = 0.8 * price;
		}
		return (int) Math.round(price);
	}


}
