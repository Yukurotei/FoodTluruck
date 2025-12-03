package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import it.thesquad.foodtruck.Main;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    protected Texture texture;
    protected List<Texture> textures;
    protected float x;
    protected float y;
    protected float alpha = 1f;
    protected float scaleX = 1f;
    protected float scaleY = 1f;
    protected Rectangle hitbox;
    protected boolean isVisible;
    private float frameTime = 0.1f;
    private float animationTimer = 0f;
    private int currentFrame = 0;

    /**
     *
     * @param texture the texture of the sprite
     * @param x the x-coordinate of the sprite
     * @param y the y-coordinate of the sprite
     */
    public Sprite(Texture texture, float x, float y, boolean addToQueue) {
        this.texture = texture;
        this.textures = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.isVisible = true;
        this.hitbox = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
        if (addToQueue) Main.spriteObjects.add(this);
    }


    /**
     *
     * @param batch the SpriteBatch used for rendering
     */
    public void render(SpriteBatch batch) {
        if (!isVisible) return;

        Texture currentTexture = (textures != null && !textures.isEmpty()) ? textures.get(currentFrame) : texture;
        if (currentTexture == null) return;

        Color originalColor = batch.getColor();
        batch.setColor(originalColor.r, originalColor.g, originalColor.b, alpha);

        float width = currentTexture.getWidth();
        float height = currentTexture.getHeight();
        float originX = width / 2f;
        float originY = height / 2f;

        batch.draw(currentTexture,
                x, y,
                originX, originY,
                width, height,
                scaleX, scaleY,
                0,
                0, 0, (int) width, (int) height,
                false, false);

        batch.setColor(originalColor);
    }

    /**
     *
     * @param delta the time elapsed since the last update
     */
    public void update(float delta) {
        if (textures != null && !textures.isEmpty() && frameTime > 0) {
            animationTimer += delta;
            if (animationTimer > frameTime) {
                animationTimer = 0f;
                currentFrame++;

                if (currentFrame >= textures.size()) currentFrame = 0;
            }
        }
    }

    public void dispose() {
        if (texture != null) texture.dispose();

        if (textures != null) for (Texture texture : textures) texture.dispose();

        Main.spriteObjects.remove(this);
    }

    /**
     *
     * @return the source texture of the sprite
     */
    public Texture getSourceTexture() {
        return texture;
    }

    /**
     *
     * @return the list of textures for animated sprites
     */
    public List<Texture> getTextures() {
        return textures;
    }

    public Texture getTexture() {
        if (texture == null && textures.isEmpty()) return null;
        if (textures.isEmpty()) return texture;
        
        return textures.get(0);
    }

    /**
     *
     * @return the x-coordinate of the sprite
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @return the y-coordinate of the sprite
     */
    public float getY() {
        return y;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    /**
     *
     * @return whether the sprite is visible
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     *
     * @param x the new x-coordinate of the sprite
     */
    public void setX(float x) {
        this.x = x;
        hitbox.setX(x);
    }

    /**
     *
     * @param y the new y-coordinate of the sprite
     */
    public void setY(float y) {
        this.y = y;
        hitbox.setY(y);
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    /**
     *
     * @param texture the new texture of the sprite
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
        this.textures = null;
        hitbox.setWidth(texture.getWidth());
        hitbox.setHeight(texture.getHeight());
    }

    /**
     *
     * @param textures the new list of textures for animated sprites
     */
    public void setTextures(List<Texture> textures) {
        this.textures = textures;
        this.texture = null;
        this.currentFrame = 0;
        this.animationTimer = 0;
        if (textures != null && !textures.isEmpty()) {
            Texture firstFrame = textures.get(0);
            hitbox.setWidth(firstFrame.getWidth());
            hitbox.setHeight(firstFrame.getHeight());
        }
    }

    /**
     *
     * @param frameTime the time each frame is displayed in animated sprites
     */
    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }

    /**
     *
     * @param visible whether the sprite should be visible
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     *
     * @return the hitbox of the sprite
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     *
     * @param other the other sprite to check collision with
     * @return true if this sprite collides with the other sprite, false otherwise
     */
    public boolean collidesWith(Sprite other) {
        return this.hitbox.overlaps(other.getHitbox());
    }


    public float getWidth() {
        return this.texture.getWidth();
    }


    public float getHeight() {
        return this.texture.getHeight();
    }

    public float centerAxis(float objectAxis, float parentAxis) {
        return objectAxis - (parentAxis / 2F);
    }
}
