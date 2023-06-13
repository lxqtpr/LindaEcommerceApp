package lxqtpr.ecommerce.linda.auth.jwt.models

enum class TokenTypeEnum(val type: String) {
    ACCESS("accessToken"),
    REFRESH("refreshToken")
}