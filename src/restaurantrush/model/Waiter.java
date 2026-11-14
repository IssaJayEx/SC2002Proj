package restaurantrush.model;

public class Waiter extends SeatingStaff{
	public void serve(Order ordered) {
		ordered.markServed();
	}

}
