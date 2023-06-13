package lxqtpr.ecommerce.linda.entities.ProductEntity.models

import org.springframework.web.multipart.MultipartFile

data class CreateProductDto(val price: Int, val title: String, val photos: List<MultipartFile>)