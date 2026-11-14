package restaurantrush.model;

import java.util.Scanner;

public class CLI {
	private Scanner sc = new Scanner(System.in);
	
	public String askRestaurantName() {
		
		System.out.println("======= Welcome to Restaurant Rush !! =======");
		System.out.println("What would you like to name your restaurant?");
		return sc.nextLine();
	}
	
	public void displayGameState(AppClass game) {
		Restaurant restaurant = game.getRestaurant();
		System.out.println("\n====== Turn " + game.getCurrentTurn() + " =======");
		System.out.println("Revenue: $" + String.format("%.2f" , game.getRevenueInCents() / 100.0));
		System.out.println("Average satisfaction of paid customers: " + game.getAverageSatisfactionOfPaidCustomer());
		
		if (restaurant.getWaitingQueue().isEmpty()) {
			System.out.println("No customer is waiting outside " + restaurant.getName() + "!!");
		}
		else {
			System.out.println("waiting queue:");
			for (Customer customer: restaurant.getWaitingQueue()) {
				System.out.println(customer.getName() + " - Satisfaction: " + customer.getSatisfaction() + ", Status: " + customer.getStatus());
			}
		}
		int tableNo=1;
		for (Table table: restaurant.getTables()) {
			if (table.isOccupied()) {
				Customer customer = table.getCustomer();
				System.out.println("Table " + tableNo + ": " + customer.getName() + "- Satisfaction: " + customer.getSatisfaction() + ", Status: " + customer.getStatus());
			}
			else {
				System.out.println("Table " + tableNo + " is currently available!");
		}
			tableNo+=1;
}
}

	
	public WaiterAction askWaiterAction() {
		while (true) {
			System.out.println("\n===== Waiter Tasks =====");
			System.out.println("1. Seat Customer");
			System.out.println("2. Take Order");
			System.out.println("3. Serve Food");
			System.out.println("4. Wait");
			System.out.println("Choose task: ");
			switch (readChoice()) {
				case 1: 
					return WaiterAction.SEAT;
				case 2: 
					return WaiterAction.TAKE_ORDER;
				case 3:
					return WaiterAction.SERVE;
				case 4:
					return WaiterAction.WAIT;
				default:
					System.out.println("You chose an invalid number!");
			}
			}
		}
	public ChefAction askChefAction() {
		while (true) {
			System.out.println("\n===== Chef Tasks =====");
			System.out.println("1. Prepare Order");
			System.out.println("2. Wait");
			System.out.println("Choose task: ");
			switch (readChoice()) {
			case 1: 
				return ChefAction.PREPARE;
			case 2: 
				return ChefAction.WAIT;
			default:
				System.out.println("You chose an invalid number!");
		}
		}
	}
	public int askTableNumber() {
		System.out.println("Choose table number: ");
		return readChoice();
	}
	
	public int readChoice() {
		while (!sc.hasNextInt()) {
			System.out.println("You gave an invalid input, Enter a number please!");
			sc.next();
		}
		return sc.nextInt();
	}
	
	public void showMessage(String msg) {
		System.out.println(msg);
	}
	
	
	public static void main(String[] args) {
		AppClass controller= new AppClass (new CLI());
		controller.runGame();
	}
	
	// stage 2 addition 
	
	public void stageIntro() {
		System.out.println("\n===== Your Customers =====");
		System.out.println("Regular - lose 8 satisfaction per waiting turn, pays the full price");
		System.out.println("VIP     - more patient, lose 5 satisfaction per waiting turn, gets a 10% discount");
		System.out.println("Critic  - an impatient person, lose 12 satisfaction per waiting turn, pays the full price, and ");
		System.out.println("          be more or less satisfied if served fast enough! (satisfaction above 70 when served) ");
		System.out.println("\nTo win, have at least 4 paid customers, earn above $45 in revenue, and have an average       ");
		System.out.println("satisfaction of 60 or more!");
		
	}
		
}

	

