package it.thesquad.foodtruck.customers;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    Review review;
    int id;
    private static int customers = 0;
    /**
     * 
     * Creates a new customer with a random order
     */
    public Customer() {
        this.order = Utils.randomOrder();
        this.id = customers;
        customers++;
    }

    /**
     * 
     * @return the order placed by the customer
     */
    public Order getOrder() {
        return order;
    }

    /**
     * 
     * @param review the review given by the customer
     */
    public void setReview(Review review) {
        this.review = review;
        Reputation.addReview(review);
    }

    /**
     * 
     * @return the review given by the customer
     */
    public Review getReview() {
        return review;
    }

    /**
     * 
     * @return the order message
     */
    public String getOrderMsg() {
        return "Customer"+id+" wants "+getOrder().toString();
    }
}
