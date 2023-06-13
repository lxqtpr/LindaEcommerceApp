package lxqtpr.ecommerce.linda.entities.UserEntity


import lxqtpr.ecommerce.linda.entities.UserEntity.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserController(private val userService: UserService) {
    @GetMapping("/users/{userId}/cart")
    fun getUserCart(@PathVariable userId: Int) = userService.getUserCart(userId)

    @PostMapping("/users/{userId}/cart/{productId}")
    fun addProductToUserCart(@PathVariable userId: Int, @PathVariable productId: Int) =
        userService.addProductToCart(userId, productId)
}