package restaurantrush.model;

public class Order {
	private int id;
	private final Customer customer;
	private final Food item;
	private final int priceInCents;
	private OrderStatus status;
	private int preparationUnitsRemaining;
	
	public Order(int id, Customer customer, Food item, int priceInCents) {
		this.id = id;
		this.customer = customer;
		this.item = item;
		this.status = OrderStatus.WAITING;
		this.preparationUnitsRemaining = item.getPrepUnits();
		this.priceInCents = priceInCents;
	}
	
	
	public int getPriceInCents() {
		return priceInCents;
	}
	public OrderStatus getStatus() {
		return status;
	}
	
	public void markServed() {
		if (status !=OrderStatus.READY) {
			System.out.println("Order "+ id + " is not ready!!");
		}
		else {
			status = OrderStatus.SERVED;
		}
	}
	
	public void markReady() {
		if (status!=OrderStatus.WAITING && status != OrderStatus.PREPARING) {
			System.out.println("Order "+ id + " is not being prepared!!");
		}
		else {
			status = OrderStatus.READY;
		}
	}
	
	public void markReadyPay() {
		if (status!= OrderStatus.SERVED) {
			System.out.println("Order "+ id + " has not been served!!");
		}
		else {
			status = OrderStatus.READY_TO_PAY;
		}
	}
	
	public void prepareOneUnit() {
	    if (status != OrderStatus.WAITING && status != OrderStatus.PREPARING) {
	        System.out.println("Order " + id + " is not being prepared!!");
	    }
	    else {
	        preparationUnitsRemaining--;
	        status = OrderStatus.PREPARING;
	        if (preparationUnitsRemaining == 0) {
	            markReady();
	        }
	    }
	}
	
	public void markPaid() {
		if (status != OrderStatus.READY_TO_PAY) {
			System.out.println("Order "+ id + " is not ready to pay!!");
		}
		else {
			status = OrderStatus.PAID;
		}
	}
	
	public void markCancelled() {
		if (status == OrderStatus.SERVED || status == OrderStatus.READY_TO_PAY || status == OrderStatus.PAID) { 
			System.out.println("Order " + id + " is already served, don't cancel it!!!");
		}
		else {
			status = OrderStatus.CANCELLED;
		}
	}
	
}

