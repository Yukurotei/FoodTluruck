package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Patty implements Ingredient {

    private Sprite patty;
    private int cookedPercentage;

    public Patty(Sprite patty) {
        this.patty = patty;
        this.cookedPercentage = 0;
    }

    public Patty(Sprite patty, int cookedPercentage) {
        this.patty = patty;
        this.cookedPercentage = cookedPercentage;
    }

    @Override
    public void draw(SpriteBatch batch) {
        patty.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (patty.getTexture().getWidth() / 2f));
        patty.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (patty.getTexture().getHeight() / 2f) - 67);

        if (patty != null) {
            batch.draw(patty.getTexture(), patty.getX(), patty.getY());
        }
    }

    public Sprite getPatty() {
        return patty;
    }

    public void setPatty(Sprite patty) {
        this.patty = patty;
    }

    public int getCookedPercentage() {
        return cookedPercentage;
    }

    public void setCookedPercentage(int cookedPercentage) {
        if (cookedPercentage < 0) {
            cookedPercentage = 0;
        }
        if (cookedPercentage > 100) {
            cookedPercentage = 100;
        }
        this.cookedPercentage = cookedPercentage;
    }
}
