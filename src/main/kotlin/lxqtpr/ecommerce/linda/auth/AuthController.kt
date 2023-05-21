package lxqtpr.ecommerce.linda.auth

import lxqtpr.ecommerce.linda.auth.service.AuthService
import lxqtpr.ecommerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("/registration")
    fun registration(@RequestBody createUserDto: CreateUserDto): ResponseEntity<UserEntity> {
        val res = authService.register(createUserDto)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, res.cookiePair.accessToken)
            .header(HttpHeaders.SET_COOKIE, res.cookiePair.refreshToken)
            .body(res.userEntity)
    }


    @PostMapping("/login")
    fun login(@RequestBody createUserDto: CreateUserDto): ResponseEntity<UserEntity> {
        val res = authService.login(createUserDto)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, res.cookiePair.accessToken)
            .header(HttpHeaders.SET_COOKIE, res.cookiePair.refreshToken)
            .body(res.userEntity)
    }

    @PostMapping("/logout")
    fun logout(@CookieValue("refreshToken") refreshToken: String): ResponseEntity<String> {
        val res = authService.logout(refreshToken)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, res.refreshToken)
            .header(HttpHeaders.SET_COOKIE, res.accessToken)
            .body("Logout complete")
    }

    @PostMapping("/refreshTokens")
    fun refresh(@CookieValue("refreshToken") refreshToken: String): ResponseEntity<String> {
        val res = authService.refreshTokens(refreshToken)
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, res.refreshToken)
            .header(HttpHeaders.SET_COOKIE, res.accessToken)
            .body("RefreshTokens complete")
    }
}