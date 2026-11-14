package restaurantrush.model;

public class SeatingStaff {
	public void seat(Customer c, Table t) {
		if(t.isOccupied()) {
			System.out.println("Table is already taken!!");
		}
		else {
			t.occupy(c);
			c.seatAt(t);
			
		}
	}

}
