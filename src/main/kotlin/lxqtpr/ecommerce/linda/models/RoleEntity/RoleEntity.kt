package lxqtpr.ecommerce.linda.models.RoleEntity

import jakarta.persistence.*

@Entity
@Table(name = "roles")
class RoleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Enumerated(EnumType.STRING)
    val role: RoleEnum,
)