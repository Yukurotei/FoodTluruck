package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.Main;

import java.util.ArrayList;
import java.util.List;

public class Sprite {
    protected Texture texture;
    protected List<Texture> textures;
    protected float x;
    protected float y;
    protected boolean isVisible;
    private float frameTime = 0.1f;
    private float animationTimer = 0f;
    private int currentFrame = 0;

    public Sprite(Texture texture, float x, float y) {
        this.texture = texture;
        this.textures = new ArrayList<>();
        this.x = x;
        this.y = y;
        Main.spriteObjects.add(this);
    }

    public void render(SpriteBatch batch) {
        if (!isVisible) return;
        if (textures != null && !textures.isEmpty()) {
            batch.draw(textures.get(currentFrame), x, y);
        } else if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void update(float delta) {
        if (textures != null && !textures.isEmpty() && frameTime > 0) {
            animationTimer += delta;
            if (animationTimer > frameTime) {
                animationTimer = 0f;
                currentFrame++;
                if (currentFrame >= textures.size()) {
                    currentFrame = 0;
                }
            }
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        if (textures != null) {
            for (Texture t : textures) {
                t.dispose();
            }
        }
    }

    public Texture getSourceTexture() {
        return texture;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.textures = null;
    }

    public void setTextures(List<Texture> textures) {
        this.textures = textures;
        this.texture = null;
        this.currentFrame = 0;
        this.animationTimer = 0;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
