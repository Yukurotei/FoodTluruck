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
        array.add(new Customer());
        getElm(0).getSprite().setX(400 - (getElm(0).getSprite().getWidth() / 2));
        getElm(0).getSprite().setY(-150);
        AnimatedSprite customerAnimated = new AnimatedSprite("customer", getElm(0).getSprite().getTexture(), getElm(0).getSprite().getX(), getElm(0).getSprite().getY(), true);
        customerAnimated.setAlpha(1f);
        getElm(0).setAnimatedSprite(customerAnimated);
        Main.cutsceneManager.addEvent(new CutsceneEvent(Main.timePassed + 3f, () -> {
            Main.animationManager.animateMove(customerAnimated, getElm(0).getSprite().getX(), getElm(0).getSprite().getY() + 300, 3f, AnimationManager.Easing.EASE_IN_OUT_QUART);
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
