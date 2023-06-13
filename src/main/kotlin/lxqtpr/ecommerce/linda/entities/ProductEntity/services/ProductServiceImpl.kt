package lxqtpr.ecommerce.linda.entities.ProductEntity.services

import lxqtpr.ecommerce.linda.entities.FileService.service.FileService
import lxqtpr.ecommerce.linda.entities.ProductEntity.ProductRepository
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.CreateProductDto
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ProductServiceImpl(
    val productRepository: ProductRepository,
    val fileService: FileService
) : ProductService {
    override fun createProduct(createProductDto: CreateProductDto): ProductEntity =
        productRepository.save(toEntity(createProductDto))


    private fun toEntity(createProductDto: CreateProductDto): ProductEntity {
        return ProductEntity(
            id = 0,
            title = createProductDto.title,
            price = createProductDto.price,
            reviews = mutableListOf(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            photos = fileService.upload(createProductDto.photos)
        )
    }
}