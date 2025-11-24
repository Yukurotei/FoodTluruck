package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import it.thesquad.foodtruck.Main;
import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Food;
import it.thesquad.foodtruck.dish.Foods;
import it.thesquad.foodtruck.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

        return new Order(new Food(mains.get((int)Math.floor(Math.random()*mains.size())),randomSize()), new Food(sides.get((int)Math.floor(Math.random()*sides.size())),randomSize()), new Food(desserts.get((int)Math.floor(Math.random()*desserts.size())),randomSize()),
            Drinks.BEER);
    }


    public static it.thesquad.foodtruck.util.Sizes randomSize() {
        int pick = (int)Math.floor(Math.random()*3);
        return switch (pick) {
            case 0 -> it.thesquad.foodtruck.util.Sizes.LARGE;
            case 1 -> it.thesquad.foodtruck.util.Sizes.MEDIUM;
            case 2 -> it.thesquad.foodtruck.util.Sizes.SMALL;
            default -> it.thesquad.foodtruck.util.Sizes.MEDIUM;
        };
    }
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

    public static Texture resizeTo(Texture sourceTexture, double percentage) {
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

    public static double distanceOfSprites(Sprite sprite1, Sprite sprite2) {
        return Math.sqrt(Math.pow(sprite1.getX() - sprite2.getX(), 2) + Math.pow(sprite1.getY() - sprite2.getY(), 2));
    }

    public static List<Sprite> getSpritesSortedByDistance(List<Sprite> sprites, Player player) {
        List<Sprite> sortedSprites = new ArrayList<>(sprites);
        sortedSprites.remove(player);
        sortedSprites.sort(Comparator.comparing(sprite -> distanceOfSprites(player, sprite)));
        return sortedSprites;
    }
}