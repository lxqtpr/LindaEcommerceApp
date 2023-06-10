package lxqtpr.ecommerce.linda.models.UserEntity.service

import lxqtpr.ecommerce.linda.models.ProductEntity.models.ProductEntity

interface UserService  {
    fun getUserCart(userId: Int): List<ProductEntity>
    fun addProductToCart(userId: Int, productId: Int): List<ProductEntity>
}