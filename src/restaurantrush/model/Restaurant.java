package restaurantrush.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Restaurant {
	private String name;
	private Menu menu;
	private List<Table> tables;
	private Queue<Customer> waitingQueue;
	private List<Order> orders;
	private Waiter waiter;
	private Chef chef;
	private Cashier cashier;
	private int numberOfTables;
	private int nextOrderId = 1;
	
	public Restaurant(String name, int numberOfTables, Waiter waiter, Chef chef,Cashier cashier){
		this.name=name;
		this.menu = new Menu ();
		this.tables = new ArrayList<Table>();
		for (int i = 1; i <= numberOfTables; i++) { // we keep number of tables flexible so that we can modify the difficulty
			tables.add(new Table(i));
		}
		this.waitingQueue = new LinkedList<Customer>();
		this.orders = new ArrayList<Order>();
		this.waiter = waiter;
		this.chef = chef;
		this.cashier = cashier;
		this.numberOfTables=numberOfTables;
		
	}
	
	public String getName() {
		return name;
	}
	public int getNumOfTables() {
		return this.numberOfTables;
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public List<Table> getTables(){
		return tables;
	}
	
	public Queue<Customer> getWaitingQueue(){
		return waitingQueue;
	}
	
	public void addToQueue(Customer c) {
		waitingQueue.add(c);
	}
	
	public Table findFreeTable() { //return from the free table with lowest table number
		for (Table t : tables) {
			if (!t.isOccupied()) {
				return t;
			}
		}
		return null; //no free table
	}

	public Waiter getWaiter() {
		return waiter;
	}
	
	public Chef getChef() {
		return chef;
	}
	
	public Cashier getCashier() {
		return cashier;
	}
	
		
	public void addOrder(Order ordered) {
		orders.add(ordered);
	}
	
	public void removeFromQueue(Customer c) {
		waitingQueue.remove(c); // for customers who abandon while queuing up.
	}
	
	public int generateOrderId() {
		int id = nextOrderId;
		nextOrderId+=1;
		return id;
	}
	
}
