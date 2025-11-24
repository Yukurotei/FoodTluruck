package it.thesquad.foodtruck.reputation;

public class Review {
    int rating;
    String reviewText;
    Review(int rating, String reviewText) {
        this.rating = rating;
        this.reviewText = reviewText;

    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }
}
