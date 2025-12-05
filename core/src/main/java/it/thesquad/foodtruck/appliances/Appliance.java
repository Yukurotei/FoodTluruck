package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.logic.Sprite;

public class Appliance extends Sprite {

    protected Texture texture;

    /**
     *
     * @param texture the texture of the appliance
     * @param x the x-coordinate of the appliance
     * @param y the y-coordinate of the appliance
     */
    public Appliance(Texture texture, float x, float y) {
        super(texture, x, y, true);
        Main.spriteObjects.add(this);
    }

    public void init() {

    }

    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    public void display(SpriteBatch batch) {

    }

    public void end() {

    }

    /**
     *
     * @param x the new x-coordinate of the appliance
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y the new y-coordinate of the appliance
     */
    public void setY(int y) {
        this.y = y;
    }
}
