package lxqtpr.ecommerce.linda.entities.FileService

import jakarta.persistence.*


@Entity
@Table(name = "files")
class FileEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val filePath: String,
    val fileType: String,
)