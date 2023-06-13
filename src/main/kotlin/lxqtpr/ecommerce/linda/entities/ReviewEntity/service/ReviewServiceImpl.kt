package lxqtpr.ecommerce.linda.entities.ReviewEntity.service

import lxqtpr.ecommerce.linda.entities.FileService.service.FileService
import lxqtpr.ecommerce.linda.entities.ProductEntity.ProductRepository
import lxqtpr.ecommerce.linda.entities.ReviewEntity.ReviewRepository
import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.CreateReviewDto
import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.ReviewEntity
import lxqtpr.ecommerce.linda.entities.UserEntity.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReviewServiceImpl(
    val fileService: FileService,
    val userRepository: UserRepository,
    val reviewRepository: ReviewRepository,
    val productRepository: ProductRepository
) : ReviewService {

    override fun createReview(createReviewDto: CreateReviewDto): ReviewEntity =
        reviewRepository.save(dtoToEntity(createReviewDto))

    override fun editReview(review: ReviewEntity): ReviewEntity {
        TODO("Not yet implemented")
    }

    override fun deleteReview(review: ReviewEntity) {
        TODO("Not yet implemented")
    }

    private fun dtoToEntity(createReviewDto: CreateReviewDto) =
        ReviewEntity(
            id = 0,
            author = userRepository.findByIdOrNull(createReviewDto.authorId) ?: throw NotFoundException(),
            product = productRepository.findByIdOrNull(createReviewDto.productId) ?: throw NotFoundException(),
            photos = fileService.upload(createReviewDto.photos),
            text = createReviewDto.text,
            rating = createReviewDto.rating,
            isEdited = false
        )
}