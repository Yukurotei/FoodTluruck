package it.thesquad.foodtruck.customers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.CompleteOrder;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    CompleteOrder completeOrder;
    Review review;
    Sprite sprite;

    /**
     * Creates a new customer with a random order
     */
    public Customer() {
        this.order = Utils.randomOrder();
        this.sprite = new Sprite(Utils.randomCustomerTexture(), 0, 0, true);
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

    public void setCompleteOrder(CompleteOrder completeOrder) {
        this.completeOrder = completeOrder;
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
        return "I want " + getOrder().toString();
    }

    public String getClankerReview() {
        return Utils.getReview(getOrderMsg(), completeOrder.getAccuracy());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void drawOrderMsg(BitmapFont font, SpriteBatch batch) {
        font.draw(batch, getOrderMsg(), getSprite().getX()+65, getSprite().getY()+55);
    }
}
