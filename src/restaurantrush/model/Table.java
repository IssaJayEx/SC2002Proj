package restaurantrush.model;

public class Table {
	private final int id;
	private boolean occupied;
	private Customer curCustomer; //reset to null when table is free
	
	public Table(int id) {
		this.id = id;
	}
	
	public void occupy(Customer c) {
		if (occupied) {
			System.out.println("Table " + id + " is already taken!!");
		}
		else {
			curCustomer = c;
			occupied = true;
		}
	}
	
	public void free() {
		curCustomer = null;
		occupied = false;
	}
	
	public boolean isOccupied() {
		return occupied;
	}
	
	public Customer getCustomer() {
		return curCustomer;
	}
}
