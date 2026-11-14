package restaurantrush.model;

public class Cashier {
	public void collectPayment(Order o) {
		o.markPaid();
	}
	}