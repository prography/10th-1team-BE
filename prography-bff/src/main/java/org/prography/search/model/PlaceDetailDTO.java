package org.prography.search.model;

import java.util.List;
import org.prography.search.model.keyword.Keyword;
import org.prography.search.model.photo.Photo;
import org.prography.search.model.review.Review;

public record PlaceDetailDTO(
    int kakaoId,
    int naverId,
    String name,
    String addressName,
    String roadAddressName,
    List<Photo> photos,
    List<Keyword> keywords,
    String reviewSummary,
    int kakaoReviewCount,
    double kakaoReviewAvgScore,
    List<Review> kakaoReviews,
    int kakaoVoteRate,
    int naverReviewCount,
    double naverReviewAvgScore,
    List<Review> naverReviews,
    int naverVoteRate
) {

}
