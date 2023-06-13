package lxqtpr.ecommerce.linda.entities.ReviewEntity.models

import org.springframework.web.multipart.MultipartFile

class CreateReviewDto(
    val authorId: Int,
    val text: String,
    val rating: Int,
    val photos: List<MultipartFile>,
    val productId: Int
)