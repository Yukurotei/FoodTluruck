package it.thesquad.foodtruck.customers;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    Review review;
    /**
     * 
     * Creates a new customer with a random order
     */
    Customer() {
        this.order = Utils.randomOrder();
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
}
