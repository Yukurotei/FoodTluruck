package it.thesquad.foodtruck.dish;

import it.thesquad.foodtruck.util.Sizes;


public class Food {
    private Foods food;
    private Sizes size;

    public Food(Foods food, Sizes size) {
        this.food = food;
        this.size = size;
    }

    public Foods getFood() {
        return food;
    }

    public Sizes getSize() {
        return size;
    }
}
