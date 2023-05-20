package lxqtpr.ecommerce.linda.models.UserEntity

import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Int> {
    fun findByEmail(email: String): UserEntity?
}