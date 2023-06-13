package lxqtpr.ecommerce.linda.entities.UserEntity

import lxqtpr.ecommerce.linda.entities.UserEntity.models.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Int> {
    fun findByEmail(email: String): UserEntity?
}