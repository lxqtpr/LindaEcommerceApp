package lxqtpr.ecommerce.linda.entities.UserEntity.models

import lxqtpr.ecommerce.linda.auth.jwt.models.JwtCookiePair

data class ResponseUserEntity(val userEntity: UserEntity, val cookiePair: JwtCookiePair)