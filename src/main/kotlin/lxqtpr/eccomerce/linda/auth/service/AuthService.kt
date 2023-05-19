package lxqtpr.eccomerce.linda.auth.service

import lxqtpr.eccomerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.eccomerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.eccomerce.linda.models.UserEntity.models.ResponseUserEntity
import lxqtpr.eccomerce.linda.models.UserEntity.models.UserEntity

interface AuthService {
    fun register(createUserDto: CreateUserDto) : ResponseUserEntity
    fun login(authRequest: CreateUserDto): JwtResponse
    fun getAccessToken(refreshToken: String): JwtResponse
    fun refresh(refreshToken: String): JwtResponse
}