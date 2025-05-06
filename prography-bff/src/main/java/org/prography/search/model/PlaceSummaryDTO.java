package org.prography.search.model;

public record PlaceSummaryDTO(
    String id,
    String name,
    String addressName,
    String roadAddressName,
    int kakaoReviews,
    double kakaoScore,
    int naverReviews,
    double naverScore) {

}
