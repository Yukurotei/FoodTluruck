package it.thesquad.foodtruck;

import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Foods;

public class Order {
    private Foods mainEntree;
    private Foods sideDish;
    private Foods dessertsDish;
    private Drinks drink;

    Order(Foods mainEntree, Foods sideDish, Foods dessertsDish, Drinks drink) {
        this.mainEntree = mainEntree;
        this.sideDish = sideDish;
        this.dessertsDish = dessertsDish;
        this.drink = drink;
    }

    public Foods getMainEntree() {
        return mainEntree;

    }
    public Foods getSideDish() {
        return sideDish;
    }
    public Foods getDessertsDish() {
        return dessertsDish;
    }
    public Drinks getDrink() {
        return drink;
    }

}
