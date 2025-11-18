package it.thesquad.foodtruck.appliances;

import com.badlogic.gdx.graphics.Texture;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.logic.Sprite;

public class Appliance extends Sprite {

    protected Texture texture;
    protected int w;
    protected int h;

    public Appliance(Texture texture, float x, float y, int w, int h) {
        super(texture, x, y);
        this.w = w;
        this.h = h;
        Main.spriteObjects.add(this);
    }

    /*
    public void render(SpriteBatch batch) {

    }
     */

    /*
    public void update() {

    }
     */

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }
}
