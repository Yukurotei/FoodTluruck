package it.thesquad.foodtruck.customers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.CompleteOrder;
import it.thesquad.foodtruck.logic.AnimatedSprite;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.logic.Utils;
import it.thesquad.foodtruck.reputation.Reputation;
import it.thesquad.foodtruck.reputation.Review;

public class Customer {
    Order order;
    CompleteOrder completeOrder;
    Review review;
    Sprite sprite;
    AnimatedSprite animatedSprite;
    boolean showClank;
    String clankerRev;

    /**
     * Creates a new customer with a random order
     */
    public Customer() {
        this.order = Utils.randomOrder();
        this.sprite = new Sprite(Utils.randomCustomerTexture(), 0, 0, true);
        this.completeOrder = new CompleteOrder(null, null, null, null);
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
        return "I want " + getOrder().toString();
    }

    public void clankerReview(BitmapFont font, SpriteBatch batch) {
        if (animatedSprite == null) return;
        if (clankerRev != null) {
            font.draw(batch, clankerRev, animatedSprite.getX()+65, animatedSprite.getY()+35);
        } else {
            clankerRev = Utils.wrapText(Utils.getReview(getOrderMsg(), completeOrder.getMainEntree().getAccuracy()), 10);
            font.draw(batch, clankerRev, animatedSprite.getX()+65, animatedSprite.getY()+35);
        }
    }

    public void showClank() {
        showClank = true;
    }

    public boolean shouldShowClank() {
        return showClank;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public void drawOrderMsg(BitmapFont font, SpriteBatch batch) {
        if (animatedSprite == null) return;
        font.draw(batch, getOrderMsg(), animatedSprite.getX()+65, animatedSprite.getY()+35);
    }
}
