package it.thesquad.foodtruck.customers;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    Review review;
    Customer(Order order) {
        this.order = order;   
    }

    public Order getOrder() {
        return order;
    }

    public void setReview(Review review) {
        this.review = review;
        Reputation.addReview(review);
    }

    public Review getReview() {
        return review;
    }
}
