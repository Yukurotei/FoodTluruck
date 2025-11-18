package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.Main;

public class Sprite {
    protected Texture texture;
    protected float x;
    protected float y;

    public Sprite(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        Main.spriteObjects.add(this);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void update(float delta) {

    }

    public void dispose() {
        texture.dispose();
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
