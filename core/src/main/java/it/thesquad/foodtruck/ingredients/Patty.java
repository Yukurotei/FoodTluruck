package it.thesquad.foodtruck.ingredients;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import it.thesquad.foodtruck.logic.Sprite;
import it.thesquad.foodtruck.player.Player;

public class Patty implements Ingredient {

    private Sprite patty;
    private int cookedPercentage;
    private int accuracy;

    private final Color rawPattyColor = new Color(219/255f, 108/255f, 141/255f, 1f); // Raw patty color (219, 108, 141)

    public Patty(Sprite patty) {
        this.patty = patty;
        this.cookedPercentage = 0;
    }

    public Patty(Sprite patty, int cookedPercentage) {
        this.patty = patty;
        this.cookedPercentage = cookedPercentage;
    }

    /**
     * Calculates the overlay color for the patty based on its cooked percentage.
     * Transitions from rawPattyColor to white (cooked), then to black (burnt).
     *
     * @return The calculated tint color.
     */
    private Color getOverlayColor() {
        Color tint;
        float currentCookedPercentage = this.cookedPercentage; // Use the field

        if (currentCookedPercentage < 50f) {
            float progress = currentCookedPercentage / 50f;
            tint = new Color(rawPattyColor).lerp(Color.WHITE, progress);
        } else {
            float progress = Math.min((currentCookedPercentage - 50f) / 50f, 1.0f);
            tint = new Color(Color.WHITE).lerp(Color.BLACK, progress);
        }
        return tint;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (patty != null) {
            patty.setX(Player.getInstance().getX() + (Player.getInstance().getTexture().getWidth() / 2f) - (patty.getTexture().getWidth() / 2f));
            patty.setY(Player.getInstance().getY() + (Player.getInstance().getTexture().getHeight() / 2f) - (patty.getTexture().getHeight() / 2f) - 67);

            batch.setColor(getOverlayColor());
            patty.render(batch);
            batch.setColor(Color.WHITE);
        }
    }

    public Sprite getSprite() {
        return patty;
    }

    public void setPatty(Sprite patty) {
        this.patty = patty;
    }

    public int getCookedPercentage() {
        return cookedPercentage;
    }

    public void setCookedPercentage(int cookedPercentage) {
        if (cookedPercentage < 0) {
            cookedPercentage = 0;
        }
        if (cookedPercentage > 100) {
            cookedPercentage = 100;
        }
        this.cookedPercentage = cookedPercentage;
    }

    
    /**
     * @author MR CLANKER GEMINI TY DADDY
     * @return
     */
    public int calcAccuracy() {     
                                                                                                                                   
        // Return 0 if the input is outside the 0-100 range                                                                                                                
        if (cookedPercentage < 0 || cookedPercentage > 100) {                                                                                                                                            
            return 0;                                                                                                                                                      
        }                                                                                                                                                                  
                                                                                                                                                                           
        // First part of the blend: from 0 up to 58                                                                                                                        
        if (cookedPercentage <= 58) {                                                                                                                                                     
            // This is the formula for the line from (0,0) to (58,5)                                                                                                       
            return (int) (5.0f / 58.0f) * cookedPercentage;                                                                                                                                     
        }                                                                                                                                                                  
        // Second part of the blend: from 58 down to 100                                                                                                                   
        else {                                                                                                                                                             
            // This is the formula for the line from (58,5) to (100,0)                                                                                                     
            return (int) (5.0f - (5.0f / 42.0f) * (cookedPercentage - 58.0f));                                                                                                                    
        }                                                                                                                                                                  
   }             

    @Override
    public int getAccuracy() {
        accuracy = calcAccuracy();
        return accuracy;
    }
}
