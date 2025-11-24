package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grill extends Appliance {

    /**
     * 
     * @param texture the texture of the grill
     * @param x the x-coordinate of the grill
     * @param y the y-coordinate of the grill
     * @param w the width of the grill
     * @param h the height of the grill
     */
    public Grill(Texture texture, int x, int y, int w, int h) {
        super(texture, x, y, w, h);
    }

    /** 
     * 
     * @param batch the SpriteBatch used for rendering
     */
    @Override
    public void display(SpriteBatch batch) {

    }
}
