package restaurantrush.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class AppClass {
	public static final int MAX_TURNS=18;
	public static final int STARTING_TABLES=2;
	private CLI cli;
	private Restaurant restaurant;
	private Random random = new Random(676767);
	private int totalSatisfactionOfPaidCustomer = 0;
	private int paidCustomerCount = 0;
	private List<Customer> eatingAtTurnStart = new ArrayList<Customer>(); //because a customer can only finish eating a turn after he or she was served
	private Queue<Customer> readyToPayLine = new LinkedList<Customer>(); //like a normal queue at a restaurant
	private int currentTurn;
	private int revenueInCents;
	private int customerCount = 0;
	private PricingService pricingService = new PricingService();
	
	//stage 2 additions
	private int vipArrival;
	private int criticArrival;
	
	
	public AppClass(CLI cli) {
		this.cli = cli;
	}
	
	public void initialiseGame(String restaurantName) { 
	    Waiter waiter = new Waiter();
	    Chef chef = new Chef();
	    Cashier cashier = new Cashier();
		restaurant = new Restaurant (restaurantName, STARTING_TABLES, waiter, chef, cashier);
		currentTurn = 0;
		revenueInCents = 0;
		
		// stage 2 additions
		vipArrival=random.nextInt(6)+1; 
		do {
			criticArrival = random.nextInt(6)+1;
		} while (criticArrival == vipArrival);
	}
	
	
	public void runGame() {
		initialiseGame(cli.askRestaurantName());
		cli.showMessage("Restaurant " + restaurant.getName() + " has been successfully created!");
		cli.stageIntro();
		
		for (int turn = 1; turn <= MAX_TURNS; turn++) {
			runTurn(turn);
		}
		
		cli.showMessage("\n ===== FINAL RESULTS! =====");
		cli.showMessage(restaurant.getName() + " had a total of " + paidCustomerCount + " paid customers (Min to win = 4)");
		cli.showMessage(restaurant.getName() + " managed to make a total revenue of $" + String.format("%.2f", revenueInCents / 100.0) + " (Min to win =$45.00)");
		cli.showMessage("The average satisfaction of " + restaurant.getName() + " is "  + getAverageSatisfactionOfPaidCustomer() + " (Min to win = 60)");
        if (evalResult() == Result.VICTORY) {
        	cli.showMessage("You ran a SUCCESSFUL restaurant!! Ready for the next gamemode?");
        }
        else {
        	cli.showMessage("Unfortunately, there's still some work to do.. but let's try again!");
        }
        
	
	}
	
	public void runTurn(int turn) {
		currentTurn = turn;
		handleArrivals();
		recordEatingCustomers();
		cli.displayGameState(this);
		resolveServing();
		EatingAndPayment();
		applySatisfactionLoss();
		cli.showMessage("----- End Of Turn " + turn + " - Revenue: $" + String.format("%.2f", revenueInCents/100.0) + ", Paid Customers: " + paidCustomerCount + " -----");
	}
	public void handleArrivals() {
		if ((currentTurn - 1) % 3 == 0) {
            customerCount+=1;
            Customer customer;
            
        // stage 2 additions
            if (customerCount == vipArrival) {
            	customer = new VIPCustomer("Customer " + customerCount + " (VIP!!)");
            }
            else if (customerCount == criticArrival) {
            	customer = new CriticCustomer("Customer " + customerCount + " (Critic!!)");
            }
            else {
            	customer = new RegularCustomer("Customer " + customerCount);
            }
        restaurant.addToQueue(customer);
        cli.showMessage("A new customer wants to eat at " + restaurant.getName() + "!!");
		}
		
	}
		
            
	
	private void recordEatingCustomers() {
		eatingAtTurnStart.clear();
		for (Table table: restaurant.getTables()) {
			if (table.isOccupied() && table.getCustomer().getStatus() == CustomerStatus.EATING) {
				eatingAtTurnStart.add(table.getCustomer());
			}
		}
	}
	
	public void resolveServing() {
		boolean doneAction = false;
		while (!doneAction) {
			doneAction = doWaiterAction(cli.askWaiterAction());
		}
		doneAction = false;
		while (!doneAction) {
			doneAction = doChefAction(cli.askChefAction());
		}
	}
	
	private boolean doWaiterAction(WaiterAction action) {
		switch(action) {
			case WAIT:
				return true;
			case SEAT:
				return seatCustomer();
			case TAKE_ORDER:
				return takeOrder();
			case SERVE:
				return serveOrder();
			default:
				return false;
		}
	}
	
	private boolean seatCustomer() {
		Table table = restaurant.findFreeTable();
		Customer customer = restaurant.getWaitingQueue().peek();
		if (table == null || customer == null) {
			cli.showMessage("No free tables or waiting customers!!");
			return false;
			}
		restaurant.getWaiter().seat(customer,table);
		restaurant.removeFromQueue(customer);
		cli.showMessage(customer.getName() + " is seated at Table " + (restaurant.getTables().indexOf(table)+1));
		return true;
		}
	
	public boolean takeOrder() {
		Customer customer = askSeatedCustomer();
		if (customer == null) {
			return false;
		}
		if (customer.getStatus() != CustomerStatus.SEATED) {
			cli.showMessage("Customer cannot place an order!");
			return false;
		}
		customer.chooseItem(restaurant.getMenu(),random);
		Food food = customer.getChoice();
		int price = pricingService.calculateFinalPrice(customer, food);
		Order order = new Order(restaurant.generateOrderId(), customer, food, price);
		customer.placeOrder(order);
		restaurant.addOrder(order);
		cli.showMessage(customer.getName() + " ordered " + customer.getChoice().getName() + "!");
		return true;
	}
	
	private boolean serveOrder() {
		Customer customer = askSeatedCustomer();
		if (customer == null) {
			return false;
		}
		Order order  = customer.getOrder();
		if (order == null || order.getStatus() != OrderStatus.READY) {
			cli.showMessage("Order is not ready!!");
			return false;
		}
		restaurant.getWaiter().serve(order);
		
		cli.showMessage(customer.getName() + " has been served!");
		
		// stage 2 addition
				String reaction = customer.onServed();
				if (reaction != null) {
					cli.showMessage(reaction);
				}
		
		return true;
	}
	
	private boolean doChefAction(ChefAction action) {
		switch (action) {
			case WAIT:
				return true;
			case PREPARE:
				Customer customer = askSeatedCustomer();
				if (customer == null) {
					return false;
				}
				Order order = customer.getOrder();
				if (order == null || (order.getStatus() != OrderStatus.WAITING && order.getStatus() != OrderStatus.PREPARING)) {
					cli.showMessage("No order available to prepare!");
					return false;
				}
				restaurant.getChef().advance(order);
				cli.showMessage("Your chef cooked one unit of " + customer.getName() + "'s order. Status: " + order.getStatus());
				return true;
			default:
				return false;
		}
			
		}
	private Customer askSeatedCustomer() {
		int tableNumber = cli.askTableNumber();
		if (tableNumber< 1 || tableNumber> restaurant.getTables().size()) {
			cli.showMessage("Invalid table");
			return null;
		}
		Table table = restaurant.getTables().get(tableNumber - 1);
		if (!table.isOccupied()) {
			cli.showMessage("Table is currently empty!!");
			return null;
		}
		return table.getCustomer();
	}
	
	public void EatingAndPayment() {
		Customer payer = readyToPayLine.poll();
		if (payer != null) {
			int bill = payer.getOrder().getPriceInCents();
			restaurant.getCashier().collectPayment(payer.getOrder());
			payer.getTable().free();
			revenueInCents += bill;
			totalSatisfactionOfPaidCustomer += payer.getSatisfaction();
			paidCustomerCount+=1;
			cli.showMessage(payer.getName() + " paid $" + String.format("%.2f", bill/ 100.0) + " and left!");
		}
		for (Customer c : eatingAtTurnStart) {
			c.getOrder().markReadyPay();
			readyToPayLine.add(c);
			cli.showMessage(c.getName() + " has finished eating and is now ready to pay");
			
		}
	}
	public void applySatisfactionLoss() {
		List<Customer> leavers = new ArrayList<Customer>();
		
		for (Customer c : restaurant.getWaitingQueue()) {
			c.loseSatisfaction(c.getSatisfactionLossPerTurn());
			if (c.isAbandoned()) {
				leavers.add(c);
			}
		}
		for (Customer c:leavers) {
			restaurant.removeFromQueue(c);
		}
		for (Table table: restaurant.getTables()) {
			if (table.isOccupied()) {
				Customer c = table.getCustomer();
				c.loseSatisfaction(c.getSatisfactionLossPerTurn());
				if (c.isAbandoned()) {
					leavers.add(c);
					
				}
			}
		}
		for (Customer c :leavers) {
			cli.showMessage(c.getName() + " has had enough and left " + restaurant.getName() + "!!");
		}
	}
	
	public Result evalResult() {
		if (paidCustomerCount >= 4 && revenueInCents >= 4500 && getAverageSatisfactionOfPaidCustomer()>=60) {
			return Result.VICTORY;
		}
		else {
			return Result.DEFEAT;
		}
	}
	
	public Restaurant getRestaurant() {
        return restaurant;
    }
		    
	public int getCurrentTurn() {
        return currentTurn;
    }
	
	public int getRevenueInCents() {
        return revenueInCents;
	}
	public int getPaidCustomerCount() {
		return paidCustomerCount;
	}
	
	public int getAverageSatisfactionOfPaidCustomer() {
		if (paidCustomerCount == 0) {
			return 0;
		}
		else {
			return totalSatisfactionOfPaidCustomer/paidCustomerCount;
			
		}
	}
	

}

	




