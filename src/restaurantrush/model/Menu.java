package restaurantrush.model;

import java.util.Random;

public class Menu {
    private final Food[] items = {
    		new Salad(),
    		new Burger(),
    		new Pasta(),
    		new ComboMeals(new Salad(), new Burger()),
    		new ComboMeals(new Salad(), new Pasta()),
    		new ComboMeals(new Pasta(), new Burger())
    		
    };
    
    public int getSize() {
    	return items.length;
    }
    
    public Food getItem(int index) {
    	return items[index];
    }
    
    public Food getRandomItem(Random r) {
    	return items[r.nextInt(items.length)];
    }
}

