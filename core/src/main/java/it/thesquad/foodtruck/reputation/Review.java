package it.thesquad.foodtruck.reputation;

public class Review {
    int rating;
    String reviewText;
    /**
     * 
     * @param rating the rating given by the customer in this review
     * @param reviewText the text of the review given by the customer
     */
    public Review(int rating, String reviewText) {
        this.rating = rating;
        this.reviewText = reviewText;

    }


    /**
     * 
     * @return the rating given by the customer in this review
     */
    public int getRating() {
        return rating;
    }

    /**
     * 
     * @return the text of the review given by the customer
     */

    public String getReviewText() {
        return reviewText;
    }
}
