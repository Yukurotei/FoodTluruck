package it.thesquad.foodtruck.dish;

import it.thesquad.foodtruck.util.Sizes;


public class Food {
    private Foods food;
    private Sizes size;

    /**
     * 
     * @param food the food item
     * @param size the size of the food item
     */
    public Food(Foods food, Sizes size) {
        this.food = food;
        this.size = size;
    }

    /**
     * 
     * @return the food item
     */
    public Foods getFood() {
        return food;
    }

    /**
     * 
     * @return the size of the food item
     */
    public Sizes getSize() {
        return size;
    }
}
