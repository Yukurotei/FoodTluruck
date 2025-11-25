package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.logic.Sprite;

public class Appliance extends Sprite {

    protected Texture texture;
    protected int w;
    protected int h;

    /**
     *
     * @param texture the texture of the appliance
     * @param x the x-coordinate of the appliance
     * @param y the y-coordinate of the appliance
     * @param w the width of the appliance
     * @param h the height of the appliance
     */
    public Appliance(Texture texture, float x, float y, int w, int h) {
        super(texture, x, y);
        this.w = w;
        this.h = h;
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
     * @return the x-coordinate of the appliance
     */
    public int getW() {
        return w;
    }

    /**
     *
     * @return the y-coordinate of the appliance
     */
    public int getH() {
        return h;
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

    /**
     *
     * @param w the new width of the appliance
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     *
     * @param h the new height of the appliance
     */
    public void setH(int h) {
        this.h = h;
    }
}
