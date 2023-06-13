package lxqtpr.ecommerce.linda.entities.ReviewEntity

import lxqtpr.ecommerce.linda.entities.ReviewEntity.models.ReviewEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<ReviewEntity, Int>