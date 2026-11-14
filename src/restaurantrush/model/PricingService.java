package restaurantrush.model;

public class PricingService {
	public int calculateFinalPrice(Customer c, Food f) {
		return (int) Math.round(f.getPrice() * (100-c.applyDiscount())/100.0);
	}

}
