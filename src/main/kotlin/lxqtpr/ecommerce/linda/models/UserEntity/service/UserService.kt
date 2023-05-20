package lxqtpr.ecommerce.linda.models.UserEntity.service

import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEnum

interface UserService  {
    fun getUserRoles(userId: Int):List<RoleEnum>
}