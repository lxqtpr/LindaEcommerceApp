package lxqtpr.ecommerce.linda.entities.ReviewEntity

import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.CreateReviewDto
import lxqtpr.ecommerce.linda.entities.ReviewEntity.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reviews")
class ReviewController(
    val reviewService: ReviewService
) {
    @PostMapping("/create")
    fun create(createReviewDto: CreateReviewDto) =
        ResponseEntity.ok().body(reviewService.createReview(createReviewDto))
}