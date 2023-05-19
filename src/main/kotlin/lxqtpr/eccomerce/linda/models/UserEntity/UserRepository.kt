package lxqtpr.eccomerce.linda.models.UserEntity

import lxqtpr.eccomerce.linda.models.UserEntity.models.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Int> {
    fun findByEmail(email: String): UserEntity?
}