package org.prography.search.service.model

import org.prography.search.model.keyword.Keyword
import org.prography.search.model.photo.Photo
import org.prography.search.model.reveiw.Review

data class PlaceDetail(
    val id: Long,
    val kakaoPlaceUri: String,
    val naverPlaceUri: String,
    val name: String,
    val addressName: String,
    val roadAddressName: String,
    val photos: List<Photo>,
    val keywords: List<Keyword>,
    val reviewSummary: String,
    val kakaoReviewCount: Int,
    val kakaoReviewAvgScore: Double,
    val kakaoReviews: List<Review>,
    val kakaoVoteRate: Int,
    val naverReviewCount: Int,
    val naverReviewAvgScore: Double,
    val naverReviews: List<Review>,
    val naverVoteRate: Int
)
