package org.prography.bff.vote.controller.model.enumeration

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 해당 플랫폼에 투표한 이유
 */
@Schema(description = "플랫폼에 투표한 이유")
enum class Reason {
    /**
     * 리뷰가 많아요
     */
    @Schema(description = "리뷰가 많아요")
    MANY_REVIEWS,

    /**
     * 디테일한 설명이 많아요
     */
    @Schema(description = "디테일한 설명이 많아요")
    DETAILED,

    /**
     * 리뷰가 솔직해요
     */
    @Schema(description = "리뷰가 솔직해요")
    HONEST,

    /**
     *설명이 정확해요
     */
    @Schema(description = "설명이 정확해요")
    ACCURATE,
}
