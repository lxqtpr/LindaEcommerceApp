package lxqtpr.ecommerce.linda.models.UserEntity.models

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEntity


@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @NotBlank(message = "username is mandatory")
    @Size(min = 4, max = 24, message = "username must be between 4 and 24 characters")
    val username: String,

    @Email
    @NotBlank(message = "password is mandatory")
    val email: String,

    @NotBlank(message = "password is mandatory")
    @Size(min = 4, max = 24, message = "password must be between 4 and 24 characters")
    val password: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = [JoinColumn(name = "users_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")])
    var roles: List<RoleEntity> = mutableListOf()
)