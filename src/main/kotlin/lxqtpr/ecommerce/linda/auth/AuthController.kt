package lxqtpr.ecommerce.linda.auth

import lxqtpr.ecommerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.ecommerce.linda.auth.service.AuthService
import lxqtpr.ecommerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/registration")
    fun registration(@RequestBody createUserDto: CreateUserDto): ResponseEntity<UserEntity> = run {
        val res = authService.register(createUserDto)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, res.accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, res.refreshCookie.toString())
            .body(res.userEntity)
    }


    @PostMapping("/login")
    fun login(@RequestBody createUserDto: CreateUserDto): JwtResponse = authService.login(createUserDto)
}