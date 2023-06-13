package lxqtpr.ecommerce.linda.entities.UserEntity.service

import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity

interface UserService  {
    fun getUserCart(userId: Int): List<ProductEntity>
    fun addProductToCart(userId: Int, productId: Int): List<ProductEntity>
}