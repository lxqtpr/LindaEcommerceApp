package lxqtpr.eccomerce.linda.models.RoleEntity

import jakarta.persistence.*
import lxqtpr.eccomerce.linda.models.UserEntity.models.UserEntity

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Enumerated(EnumType.STRING)
    val role: RoleEnum,

    @ManyToMany(mappedBy = "roles")
    var users: List<UserEntity> = mutableListOf()
)