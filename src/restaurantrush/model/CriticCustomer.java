package restaurantrush.model;

public class CriticCustomer extends Customer{
	
	public CriticCustomer(String name) {
		super(name);
	}
	
	public int getSatisfactionLossPerTurn() {
		return 12;
	}
	
	public int applyDiscount() {
		return 0;

	}
	
	public String onServed() {
		if (getSatisfaction()>= 70) {
			gainSatisfaction(10);
			return getName() + " loved the quick service, and wrote a beautiful review!! (+10 satisfaction)";
		}
		else {
			reduceSatisfaction(10);
			return getName() + " did not appreciate the wait, and wrote a hurtful comment.. (-10 satisfaction)";
			}
		}
	}	
