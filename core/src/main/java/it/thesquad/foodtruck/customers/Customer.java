package it.thesquad.foodtruck.customers;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.CompleteOrder;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    CompleteOrder completeOrder;
    Review review;

    /**
     * Creates a new customer with a random order
     */
    public Customer() {
        this.order = Utils.randomOrder();
    }

    /**
     * @return the order placed by the customer
     */
    public Order getOrder() {
        return order;
    }

    /**
     * 
     * @return the fufilled order by the customer
     */
    public CompleteOrder getCompleteOrder() {
        return completeOrder;
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
        return "Customer wants " + getOrder().toString();
    }

    public String getClankerReview() {
        return Utils.getReview(getOrderMsg(), completeOrder.getAccuracy());
    }
}
