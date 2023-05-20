package lxqtpr.ecommerce.linda.auth.jwt.models

import org.springframework.beans.factory.annotation.Value

data class JwtResponse (
    val accessToken: String,
    val refreshToken : String,
){
    @Value("\${security.jwt.type")
    val type: String = ""
}