package restaurantrush.model;

import java.util.Random;

public class Menu {
    private final Food[] items = {
    		new Salad(),
    		new Burger(),
    		new Pasta()
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

