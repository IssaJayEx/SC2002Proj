package restaurantrush.model;

import java.util.Random;

public abstract class Customer {
	public static final int MAXSATISFACTION = 100;
	
	private final String name;
	private Integer satisfaction = 100;
	private Table table;  //null when queuing to enter
	private Order order;  //null until the Waiter takes order
	private Food choice; //null until customer decides
	private Boolean abandon = false;
	
	protected Customer(String name) {
		this.name = name;
	}

	public abstract int getSatisfactionLossPerTurn();
	
	public void loseSatisfaction(int amount) {
		CustomerStatus s = getStatus();
		//only customers still waiting for a table or food lose satisfaction
		if (s != CustomerStatus.QUEUING && s != CustomerStatus.SEATED && s != CustomerStatus.WAITING_FOR_FOOD && s != CustomerStatus.READY_TO_SERVE) {
			return;
		}
		satisfaction = Math.max(0, satisfaction - amount);
		if (satisfaction == 0) {
			abandon = true;
			if (order != null) {
				order.markCancelled(); //chef stops cooking it, can cook others instead
			}
			if (table != null) {
				table.free(); //free table for next customer to sit
			}
		}
	}

	
	public void seatAt(Table t) {
		if (getStatus() != CustomerStatus.QUEUING) {
			System.out.println(this.name + " is not in the queue!!");
		}
		else {
			this.table = t;
		}
	}
	
	public void chooseItem(Menu menu, Random random) {
		this.choice = menu.getRandomItem(random);
	}
	
	public Food getChoice() {
		return choice;
	}
	
	public void placeOrder(Order o) {
		if (getStatus() != CustomerStatus.SEATED) {
			System.out.println(this.name + " is not seated without an order!!");
		}
		else {
			this.order = o;
		}
	}
	
	public boolean isServed() {
		if (order == null) {
			return false;
		}
		OrderStatus s = order.getStatus();
		return s == OrderStatus.SERVED || s == OrderStatus.READY_TO_PAY || s == OrderStatus.PAID;
		// true so long as one got food onwards
	}
	
	public boolean isAbandoned() {
		return abandon;
	}
	
	public boolean isReadyPay() {
		return order != null && order.getStatus() == OrderStatus.READY_TO_PAY;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public Table getTable() {
		return table;
	}
	
	public CustomerStatus getStatus() {
		if (abandon) {
			return CustomerStatus.ABANDONED;
		}
		if (order == null) {
			if (table == null) {
				return CustomerStatus.QUEUING; //no order and table = outside restaurant
			}
			else {
				return CustomerStatus.SEATED; // no order, but has a table
			}
		}
		switch (order.getStatus()) {
			case WAITING:
			case PREPARING:
				return CustomerStatus.WAITING_FOR_FOOD;
			case READY:
				return CustomerStatus.READY_TO_SERVE;
			case SERVED:
				return CustomerStatus.EATING;
			case READY_TO_PAY:
				return CustomerStatus.READY_TO_PAY;
			case PAID:
				return CustomerStatus.PAID;
			case CANCELLED:
				return CustomerStatus.ABANDONED;
			default:
				return null; //maybe can put exception handling here**
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSatisfaction() {
		return this.satisfaction;
	}
	
	public abstract int applyDiscount();

}

