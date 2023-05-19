package lxqtpr.eccomerce.linda.models.UserEntity.service

import lxqtpr.eccomerce.linda.models.RoleEntity.RoleEnum

interface UserService  {
    fun getUserRoles(userId: Int):List<RoleEnum>
}