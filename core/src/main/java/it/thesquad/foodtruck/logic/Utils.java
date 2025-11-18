package it.thesquad.foodtruck.logic;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Foods;

public final class Utils {
    public static Order randomOrder() {
        List<Foods> mains = Arrays.stream(Foods.values())
                          .filter(f -> f.getCategory() == Foods.Category.MAIN)
                          .toList();
        List<Foods> sides = Arrays.stream(Foods.values())
                                .filter(f -> f.getCategory() == Foods.Category.SIDE)
                                .toList();
        List<Foods> desserts = Arrays.stream(Foods.values())
                                .filter(f -> f.getCategory() == Foods.Category.DESSERT)
                                .toList();

        return new Order(mains.get((int)Math.floor(Math.random()*mains.size())), sides.get((int)Math.floor(Math.random()*sides.size())), desserts.get((int)Math.floor(Math.random()*desserts.size())),
            Drinks.BEER);
    }

    /**
     * Creates a new texture by scaling an existing texture to a specific width and height.
     * <p>
     * This method is CPU-intensive and creates a new Texture object. For frequent or
     * real-time scaling, it is much more efficient to use the
     * {@code SpriteBatch.draw(texture, x, y, width, height)} method during rendering.
     * <p>
     * This method safely disposes of the intermediate Pixmap objects it creates.
     *
     * @param sourceTexture The source texture to resize.
     * @param newWidth      The desired width of the new texture in pixels (must be > 0).
     * @param newHeight     The desired height of the new texture in pixels (must be > 0).
     * @return A new Texture object with the specified dimensions.
     * @throws IllegalArgumentException if newWidth or newHeight are not positive.
     */
    public static Texture resizeTo(Texture sourceTexture, int newWidth, int newHeight) {
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("New dimensions must be positive.");
        }

        if (!sourceTexture.getTextureData().isPrepared()) {
            sourceTexture.getTextureData().prepare();
        }
        Pixmap sourcePixmap = sourceTexture.getTextureData().consumePixmap();

        Pixmap resizedPixmap = new Pixmap(newWidth, newHeight, sourcePixmap.getFormat());

        // Draw the original Pixmap into the new one, scaling it to the new dimensions.
        resizedPixmap.drawPixmap(sourcePixmap,
            0, 0, sourcePixmap.getWidth(), sourcePixmap.getHeight(), // Source rectangle
            0, 0, newWidth, newHeight    // Destination rectangle
        );

        Texture resizedTexture = new Texture(resizedPixmap);

        // Dispose of the pixmaps to prevent memory leaks
        sourcePixmap.dispose();
        resizedPixmap.dispose();

        return resizedTexture;
    }

    /**
     * Creates a new texture by scaling an existing texture by a given percentage.
     * <p>
     * A percentage of 100 results in an identically sized texture. A percentage of 50
     * results in a texture that is half the width and height of the original.
     * <p>
     * This method is a convenience wrapper around {@link #resizeTo(Texture, int, int)}.
     *
     * @param sourceTexture The source texture to scale.
     * @param percentage    The percentage to scale the texture by (e.g., 50.0 for 50%).
     *                      Using a double for the parameter as requested.
     * @return A new, scaled Texture object.
     * @throws IllegalArgumentException if percentage is not positive.
     */
    public static Texture scaleByPercentage(Texture sourceTexture, double percentage) {
        if (percentage <= 0) {
            throw new IllegalArgumentException("Percentage must be positive.");
        }

        float scale = (float) (percentage / 100.0);
        int newWidth = Math.round(sourceTexture.getWidth() * scale);
        int newHeight = Math.round(sourceTexture.getHeight() * scale);

        if (newWidth <= 0) newWidth = 1;
        if (newHeight <= 0) newHeight = 1;

        return resizeTo(sourceTexture, newWidth, newHeight);
    }
}
