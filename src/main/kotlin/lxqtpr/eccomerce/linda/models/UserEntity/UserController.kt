package lxqtpr.eccomerce.linda.models.UserEntity


import lxqtpr.eccomerce.linda.models.RoleEntity.RoleEnum
import lxqtpr.eccomerce.linda.models.UserEntity.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserController(private val userService: UserService) {
    @GetMapping("/users/{userId}/role")
    fun getUserRoles(@PathVariable userId: Int) = userService.getUserRoles(userId)
}