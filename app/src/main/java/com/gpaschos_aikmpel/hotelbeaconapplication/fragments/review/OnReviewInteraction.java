package com.gpaschos_aikmpel.hotelbeaconapplication.fragments.review;

public interface OnReviewInteraction {
    void writeReview();

    void checkReview(double rating, String comments);

    void completeReview();
}
