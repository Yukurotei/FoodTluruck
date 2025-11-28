package it.thesquad.foodtruck.logic;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import it.thesquad.foodtruck.Order;
import it.thesquad.foodtruck.Sizes;
import it.thesquad.foodtruck.dish.Drinks;
import it.thesquad.foodtruck.dish.Food;
import it.thesquad.foodtruck.dish.Foods;
import it.thesquad.foodtruck.player.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public final class Utils {
    /**
     *
     * @return a random order consisting of a main entree, side dish, dessert, and drink
     */
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

    /**
     *
     * @return a random size (LARGE, MEDIUM, SMALL)
     */
    public static Sizes randomSize() {
        int pick = (int)Math.floor(Math.random()*3);
        return switch (pick) {
            case 0 -> Sizes.LARGE;
            case 1 -> Sizes.MEDIUM;
            case 2 -> Sizes.SMALL;
            default -> Sizes.MEDIUM;
        };
    }

    /**
     *
     * @param sourceTexture the original texture to be resized
     * @param newWidth the desired width of the resized texture
     * @param newHeight the desired height of the resized texture
     * @return a new Texture object resized to the specified dimensions
     */
    public static Texture resizeTo(Texture sourceTexture, int newWidth, int newHeight) {
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }

        com.badlogic.gdx.graphics.TextureData sourceData = sourceTexture.getTextureData();
        Pixmap sourcePixmap;

        boolean isFile = sourceData instanceof com.badlogic.gdx.graphics.glutils.FileTextureData;
        if (isFile) {
            sourcePixmap = new Pixmap(((com.badlogic.gdx.graphics.glutils.FileTextureData) sourceData).getFileHandle());
        } else {
            if (!sourceData.isPrepared()) {
                sourceData.prepare();
            }
            sourcePixmap = sourceData.consumePixmap();
        }

        Pixmap resizedPixmap = new Pixmap(newWidth, newHeight, sourcePixmap.getFormat());
        resizedPixmap.setFilter(Pixmap.Filter.NearestNeighbour); //Much faster scaling method than BiLinear

        resizedPixmap.drawPixmap(sourcePixmap,
                0, 0, sourcePixmap.getWidth(), sourcePixmap.getHeight(), // Source rectangle
                0, 0, newWidth, newHeight    // Destination rectangle
        );

        Texture resizedTexture = new Texture(resizedPixmap);

        //Dispose pixmaps
        sourcePixmap.dispose();
        resizedPixmap.dispose();

        return resizedTexture;
    }

    /**
     *
     * @param sourceTexture the original texture to be resized
     * @param percentage the percentage to scale the texture by (e.g., 50 for 50%)
     * @return a new Texture object resized by the specified percentage
     */
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

    /**
     *
     * @param sprite1 the first sprite
     * @param sprite2 the second sprite
     * @return the Euclidean distance between the two sprites
     */
    public static double distanceOfSprites(Sprite sprite1, Sprite sprite2) {
        double dx = (sprite1.getX() + sprite1.getTexture().getWidth() / 2.0) - (sprite2.getX() + sprite2.getTexture().getWidth() / 2.0);
        double dy = (sprite1.getY() + sprite1.getTexture().getHeight() / 2.0) - (sprite2.getY() + sprite2.getTexture().getHeight() / 2.0);
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     *
     * @param sprites the list of sprites to be sorted
     * @param player the player sprite to measure distance from
     * @return a list of sprites sorted by their distance from the player
     */
    public static List<Sprite> getSpritesSortedByDistance(Queue<Sprite> sprites, Player player) {
        List<Sprite> sortedSprites = new ArrayList<>(sprites);
        sortedSprites.remove(player);
        sortedSprites.sort(Comparator.comparing(sprite -> distanceOfSprites(player, sprite)));
        return sortedSprites;
    }

    /**
     * Rotates a texture by a given angle (multiples of 90 degrees).
     * For angles other than 0, 90, 180, 270, the original texture is returned.
     *
     * @param sourceTexture The original texture to rotate.
     * @param degrees The rotation angle in degrees (0, 90, 180, 270).
     * @return A new, rotated Texture.
     */
    public static Texture rotateTextureRightAngles(Texture sourceTexture, float degrees) {
        if (sourceTexture == null) {
            throw new IllegalArgumentException("Source texture cannot be null.");
        }

        int rotationAngle = ((int) degrees % 360 + 360) % 360;
        if (rotationAngle % 90 != 0) {
            return sourceTexture;
        }

        TextureData sourceData = sourceTexture.getTextureData();
        Pixmap sourcePixmap;

        if (!sourceData.isPrepared()) {
            sourceData.prepare();
        }
        sourcePixmap = sourceData.consumePixmap();


        int originalWidth = sourcePixmap.getWidth();
        int originalHeight = sourcePixmap.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // Determine new dimensions for 90/270 degree rotations
        if (rotationAngle == 90 || rotationAngle == 270) {
            newWidth = originalHeight;
            newHeight = originalWidth;
        }

        Pixmap rotatedPixmap = new Pixmap(newWidth, newHeight, sourcePixmap.getFormat());

        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                int color = sourcePixmap.getPixel(x, y);
                int rotatedX = 0;
                int rotatedY = switch (rotationAngle) {
                    case 0 -> {
                        rotatedX = x;
                        yield y;
                    }
                    case 90 -> {
                        rotatedX = y;
                        yield newHeight - 1 - x;
                    }
                    case 180 -> {
                        rotatedX = newWidth - 1 - x;
                        yield newHeight - 1 - y;
                    }
                    case 270 -> {
                        rotatedX = newWidth - 1 - y;
                        yield x;
                    }
                    default -> 0;
                };

                rotatedPixmap.drawPixel(rotatedX, rotatedY, color);
            }
        }

        Texture rotatedTexture = new Texture(rotatedPixmap);

        sourcePixmap.dispose();
        rotatedPixmap.dispose();

        return rotatedTexture;
    }
    


private static final Gson gson = new Gson();

public static String getReview(String prompt) {
    try {
        URL url = new URL("http://localhost:5005/review");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        JsonObject obj = new JsonObject();
        obj.addProperty("prompt", prompt);
        String json = gson.toJson(obj);

        con.getOutputStream().write(json.getBytes());

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(con.getInputStream())
        );

        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null)
            result.append(line);

        return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "AI error";
        }
    }


}
