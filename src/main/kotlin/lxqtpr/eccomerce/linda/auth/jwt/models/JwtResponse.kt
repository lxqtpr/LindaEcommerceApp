package lxqtpr.eccomerce.linda.auth.jwt.models

data class JwtResponse (
    val accessToken: String,
    val refreshToken : String,
){
    val type: String = "Bearer"
}