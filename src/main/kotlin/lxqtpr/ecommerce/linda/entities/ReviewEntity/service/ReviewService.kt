package lxqtpr.ecommerce.linda.entities.ReviewEntity.service

import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.CreateReviewDto
import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.ReviewEntity

interface ReviewService {
    fun createReview(createReviewDto: CreateReviewDto): ReviewEntity
    fun editReview(review: ReviewEntity): ReviewEntity
    fun deleteReview(review: ReviewEntity)
}