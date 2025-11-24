package it.thesquad.foodtruck;

import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Food;

public class Order {
    private Food mainEntree;
    private Food sideDish;
    private Food dessertsDish;
    private Drinks drink;

    public Order(Food mainEntree, Food sideDish, Food dessertsDish, Drinks drink) {
        this.mainEntree = mainEntree;
        this.sideDish = sideDish;
        this.dessertsDish = dessertsDish;
        this.drink = drink;
    }

    public Food getMainEntree() {
        return mainEntree;

    }
    public Food getSideDish() {
        return sideDish;
    }
    public Food getDessertsDish() {
        return dessertsDish;
    }
    public Drinks getDrink() {
        return drink;
    }
}
