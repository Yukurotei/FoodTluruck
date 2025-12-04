package it.thesquad.foodtruck.customers;

import java.util.ArrayList;

import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.logic.AnimatedSprite;
import it.thesquad.foodtruck.logic.AnimationManager;
import it.thesquad.foodtruck.logic.CutsceneEvent;

public class CustomerQueue {
    private ArrayList<Customer> array = new ArrayList<Customer>();

    /**
     * @param customer the customer object
     */
    public void add(Customer customer) {
        array.add(customer);
    }

    public void shift() {
        array.remove(0);
        Main.cutsceneManager.addEvent(new CutsceneEvent(Main.timePassed + 3f, () -> {
            Main.animationManager.animateMove(new AnimatedSprite(getElm(0).getSprite()), getElm(0).getSprite().getX(), getElm(0).getSprite().getY() + 600, 6f, AnimationManager.Easing.LINEAR);
        }));
    }

    /**
     *
     * @param index the index
     * @return the customer object at the index
     */
    public Customer getElm(int index) {
        return array.get(index);
    }

    public ArrayList<Customer> getArrayList() {
        return array;
    }
}
