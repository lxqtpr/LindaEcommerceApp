package lxqtpr.ecommerce.linda.entities.RoleEntity

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, Int> {
    fun findByRole(role: RoleEnum): RoleEntity
}