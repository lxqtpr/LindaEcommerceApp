package lxqtpr.ecommerce.linda.entities.ProductEntity.models

import jakarta.persistence.*
import lxqtpr.ecommerce.linda.entities.FileService.FileEntity
import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.ReviewEntity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    val title: String,
    val price: Int,

    @OneToMany
    @JoinColumn(name = "product_id")
    val photos: List<FileEntity>,

    @OneToMany(mappedBy = "product")
    val reviews: MutableList<ReviewEntity>,

    @Column(updatable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime,

    @UpdateTimestamp
    val updatedAt: LocalDateTime
)