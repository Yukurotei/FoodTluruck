package it.thesquad.foodtruck.ingredients;

import it.thesquad.foodtruck.logic.Sprite;

public class Burger extends Bun
{

    Patty patty;

    public Burger(Sprite bread, Patty patty) {
        super(bread);
        this.patty = patty;
    }

    public Patty getPatty() {
        return patty;
    }
}
