package lxqtpr.ecommerce.linda.auth.service

import lxqtpr.ecommerce.linda.auth.jwt.models.JwtCookiePair
import lxqtpr.ecommerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.models.UserEntity.models.ResponseUserEntity

interface AuthService {
    fun register(createUserDto: CreateUserDto) : ResponseUserEntity
    fun login(loginUserDto: CreateUserDto): ResponseUserEntity
    fun logout(refreshToken: String): JwtCookiePair
    fun refreshTokens(refreshToken: String): JwtCookiePair
}