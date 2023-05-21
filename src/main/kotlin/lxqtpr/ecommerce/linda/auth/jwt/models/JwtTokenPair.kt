package lxqtpr.ecommerce.linda.auth.jwt.models

data class JwtTokenPair(
    val accessToken: String,
    val refreshToken : String,
)