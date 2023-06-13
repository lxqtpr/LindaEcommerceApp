package lxqtpr.ecommerce.linda.entities.ProductEntity.services

import lxqtpr.ecommerce.linda.entities.ProductEntity.models.CreateProductDto
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity

interface ProductService {
    fun createProduct(createProductDto: CreateProductDto): ProductEntity
}