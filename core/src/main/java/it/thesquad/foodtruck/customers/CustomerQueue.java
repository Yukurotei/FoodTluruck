package it.thesquad.foodtruck.customers;

import java.util.ArrayList;

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
