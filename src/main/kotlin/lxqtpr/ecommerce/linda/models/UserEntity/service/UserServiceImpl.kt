package lxqtpr.ecommerce.linda.models.UserEntity.service

import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEnum
import lxqtpr.ecommerce.linda.models.UserEntity.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
) : UserService {
    override fun getUserRoles(userId: Int): List<RoleEnum> {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException()
        return user.roles.map { it.role }
    }

}