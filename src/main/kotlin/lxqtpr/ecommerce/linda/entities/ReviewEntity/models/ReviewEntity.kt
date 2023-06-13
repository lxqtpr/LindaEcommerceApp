package lxqtpr.ecommerce.linda.entities.ReviewEntity.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lxqtpr.ecommerce.linda.entities.FileService.FileEntity
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity
import lxqtpr.ecommerce.linda.entities.UserEntity.models.UserEntity
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "reviews")
class ReviewEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "author_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val author: UserEntity,

    @OneToMany
    @JoinColumn(name = "review_id")
    val photos: List<FileEntity>,

    @ManyToOne
    @JsonIgnore
    val product: ProductEntity,

    val text: String,
    val isEdited: Boolean,
    val rating: Int,
)