package lxqtpr.ecommerce.linda.entities.ProductEntity

import lxqtpr.ecommerce.linda.entities.ProductEntity.models.CreateProductDto
import lxqtpr.ecommerce.linda.entities.ProductEntity.models.ProductEntity
import lxqtpr.ecommerce.linda.entities.ProductEntity.services.ProductServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/products")
class ProductController(
    val productService: ProductServiceImpl
) {

    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun createProduct(createProductDto: CreateProductDto): ResponseEntity<ProductEntity> =
        ResponseEntity.ok().body(productService.createProduct(createProductDto))

}