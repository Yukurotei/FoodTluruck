package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Texture;
import it.thesquad.foodtruck.Main;

public class AnimatedSprite extends Sprite {

    private String spriteID;

    /**
     *
     * @param spriteID            the unique identifier for this animated sprite
     * @param texture             the texture of the sprite
     * @param x                   the x-coordinate of the sprite
     * @param y                   the y-coordinate of the sprite
     * @param addToAnimationQueue if true, the sprite will be automatically updated by the AnimationManger
     */
    public AnimatedSprite(String spriteID, Texture texture, float x, float y, boolean addToAnimationQueue) {
        super(texture, x, y, false);
        if (Utils.getSprite(spriteID) != null) return;
        this.spriteID = spriteID;
        if (addToAnimationQueue) {
            Main.animatedSprites.add(this);
        }
    }

    public void setSpriteID(String spriteID) {
        this.spriteID = spriteID;
    }

    public String getSpriteID() {
        return spriteID;
    }
}
