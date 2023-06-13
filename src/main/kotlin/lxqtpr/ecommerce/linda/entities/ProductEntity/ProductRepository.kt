package lxqtpr.ecommerce.linda.entities.ProductEntity

import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Int> {
}