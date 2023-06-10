package lxqtpr.ecommerce.linda.models.UserEntity.models

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lxqtpr.ecommerce.linda.models.ProductEntity.models.ProductEntity
import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime


@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @NotBlank(message = "username is mandatory")
    @Size(min = 4, max = 24, message = "username must be between 4 and 24 characters")
    val username: String,

    @NotBlank(message = "password is mandatory")
    @Email
    val email: String,

    @NotBlank(message = "password is mandatory")
    @Size(min = 4, max = 24, message = "password must be between 4 and 24 characters")
    val password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")]
    )
    var roles: List<RoleEntity>,


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "cart",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val cart: MutableList<ProductEntity>,

    @Column(updatable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime,

    @UpdateTimestamp
    val updatedAt: LocalDateTime
)