package restaurantrush.model;

public class ComboMeals extends Food{
	private Food firstOption;
	private Food secondOption;
	
	public ComboMeals(Food firstOption, Food secondOption){
		super(firstOption.getName() + " + " + secondOption.getName() + " Combo!", 
				(int) Math.round((firstOption.getPrice() + secondOption.getPrice()) * 0.9 ));
		this.firstOption = firstOption;
		this.secondOption = secondOption;
	}
	
	public int getPrepUnits() {
		return (firstOption.getPrepUnits() + secondOption.getPrepUnits());
	}
}
