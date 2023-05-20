package lxqtpr.ecommerce.linda.auth.service

import lxqtpr.ecommerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.ecommerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.models.UserEntity.models.ResponseUserEntity

interface AuthService {
    fun register(createUserDto: CreateUserDto) : ResponseUserEntity
    fun login(authRequest: CreateUserDto): JwtResponse
    fun getAccessToken(refreshToken: String): JwtResponse
    fun refresh(refreshToken: String): JwtResponse
}