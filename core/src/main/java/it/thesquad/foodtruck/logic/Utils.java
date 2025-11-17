package it.thesquad.foodtruck.logic;

import java.util.Arrays;
import java.util.List;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Foods;

public final class Utils {
    public static Order randomOrder() {
        List<Foods> mains = Arrays.stream(Foods.values())
                          .filter(f -> f.getCategory() == Foods.Category.MAIN)
                          .toList();
        List<Foods> sides = Arrays.stream(Foods.values())
                                .filter(f -> f.getCategory() == Foods.Category.SIDE)
                                .toList();
        List<Foods> desserts = Arrays.stream(Foods.values())
                                .filter(f -> f.getCategory() == Foods.Category.DESSERT)
                                .toList();

        return new Order(mains.get((int)Math.floor(Math.random()*mains.size())), sides.get((int)Math.floor(Math.random()*sides.size())), desserts.get((int)Math.floor(Math.random()*desserts.size())),
            Drinks.BEER);
    }
}
