package it.thesquad.foodtruck.reputation;

import java.util.ArrayList;

public final class Reputation {
    
    private static double reputationScore = 0;
    private static ArrayList<Review> reviews = new ArrayList<Review>();
    private static int rating = 0;

    private Reputation() {}

    public static double getReputationScore() {
        return reputationScore;
    }    
    
    public static void setReputationScore(double s) {
        reputationScore = s;
    }

    public static ArrayList<Review> getReviews() {
        return reviews;
    }    
    
    public static void addReview(Review r) {
        reviews.add(r);
        rating = calcAvgRating();
    }

    private static int calcAvgRating() {
        int a = 0;
        for (Review review : reviews) {
            a += review.getRating();
        }
        return a/reviews.size();
    }
}
