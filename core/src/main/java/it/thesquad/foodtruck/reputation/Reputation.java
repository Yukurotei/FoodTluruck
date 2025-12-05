package it.thesquad.foodtruck.reputation;

import java.util.ArrayList;

public final class Reputation {
    
    private static double reputationScore = 0;
    private static ArrayList<Review> reviews = new ArrayList<Review>();
    private static int rating = 0;

    private Reputation() {}

    /**
     * 
     * @return the average rating of the food truck
     */
    public static double getReputationScore() {
        return reputationScore;
    }    
    
    /**
     * 
     * @param s the new reputation score
     */
    public static void setReputationScore(double s) {
        reputationScore = s;
    }

    /**
     * 
     * @return the list of reviews given to the food truck
     */
    public static ArrayList<Review> getReviews() {
        return reviews;
    }    
    
    /**
     * 
     * @return the average rating of the food truck
     */
    public static void addReview(Review r) {
        reviews.add(r);
        rating = calcAvgRating();
    }

    /**
     * 
     * @return the average rating of the food truck
     */
    private static int calcAvgRating() {
        int a = 0;
        for (Review review : reviews) a += review.getRating();
        
        return a/reviews.size();
    }
}
