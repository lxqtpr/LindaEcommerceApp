package lxqtpr.eccomerce.linda.models.UserEntity.models

import lxqtpr.eccomerce.linda.auth.jwt.models.JwtResponse
import org.springframework.http.ResponseCookie

data class ResponseUserEntity(val userEntity: UserEntity, val accessCookie: ResponseCookie, val refreshCookie: ResponseCookie)