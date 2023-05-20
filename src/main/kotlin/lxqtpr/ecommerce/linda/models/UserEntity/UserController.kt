package lxqtpr.ecommerce.linda.models.UserEntity


import lxqtpr.ecommerce.linda.models.UserEntity.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class UserController(private val userService: UserService) {
    @GetMapping("/users/{userId}/role")
    fun getUserRoles(@PathVariable userId: Int) = userService.getUserRoles(userId)
}