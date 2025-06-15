package org.prography.bff.voting.controller.model.enumeration

/**
 * 해당 플랫폼에 투표한 이유
 */
enum class VoteReason {
    /**
     * 리뷰가 많아요
     */
    MANY_REVIEWS,

    /**
     * 디테일한 설명이 많아요
     */
    DETAILED,

    /**
     * 리뷰가 솔직해요
     */
    HONEST,

    /**
     *설명이 정확해요
     */
    ACCURATE,
}
