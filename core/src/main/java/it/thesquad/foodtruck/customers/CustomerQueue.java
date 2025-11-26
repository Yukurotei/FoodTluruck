package it.thesquad.foodtruck.customers;

import java.util.ArrayList;

public class CustomerQueue {
    private ArrayList<Customer> array = new ArrayList<Customer>();
    
    public CustomerQueue() {

    }

    /**
     * 
     * @param customer the customer object
     */
    public void add(Customer customer) {
        array.add(customer);
    }

    public void pop() {
        array.remove(array.size());
    }

    /**
     * 
     * @param index the index
     * @return the customer object at the index
     */
    public Customer getElm(int index) {
        return array.get(index);
    }
}
