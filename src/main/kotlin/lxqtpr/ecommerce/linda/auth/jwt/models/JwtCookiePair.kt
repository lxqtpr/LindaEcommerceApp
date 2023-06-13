package lxqtpr.ecommerce.linda.auth.jwt.models

data class JwtCookiePair(
    val accessToken: String,
    val refreshToken: String,
)