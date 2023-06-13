package lxqtpr.ecommerce.linda.entities.UserEntity.service

import lxqtpr.ecommerce.linda.entities.ProductEntity.ProductRepository
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity
import lxqtpr.ecommerce.linda.entities.UserEntity.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val productRepository: ProductRepository
) : UserService {
    override fun getUserCart(userId: Int): List<ProductEntity> =
        userRepository.findByIdOrNull(userId)?.cart ?: throw NotFoundException()

    override fun addProductToCart(userId: Int, productId: Int): List<ProductEntity> {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()
        var product = productRepository.findByIdOrNull(productId) ?: throw NotFoundException()
        user.cart.add(product)
        userRepository.save(user)
        return user.cart
    }


}