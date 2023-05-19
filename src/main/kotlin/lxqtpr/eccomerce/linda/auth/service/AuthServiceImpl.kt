package lxqtpr.eccomerce.linda.auth.service

import at.favre.lib.crypto.bcrypt.BCrypt
import lxqtpr.eccomerce.linda.auth.jwt.JwtRepository
import lxqtpr.eccomerce.linda.auth.jwt.JwtService
import lxqtpr.eccomerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.eccomerce.linda.models.RoleEntity.RoleEntity
import lxqtpr.eccomerce.linda.models.RoleEntity.RoleEnum
import lxqtpr.eccomerce.linda.models.UserEntity.UserRepository
import lxqtpr.eccomerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.eccomerce.linda.models.UserEntity.models.ResponseUserEntity
import lxqtpr.eccomerce.linda.models.UserEntity.models.UserEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import java.time.Duration


@Service
class AuthServiceImpl(@Value("\${security.jwt.accessExpiration}") val accessExpiration: Long,
                      @Value("\${security.jwt.refreshExpiration}") val refreshExpiration: Long,
                      val userRepository: UserRepository,
                      val jwtRepository: JwtRepository,
                      val jwtService: JwtService

) : AuthService {
    override fun register(createUserDto: CreateUserDto): ResponseUserEntity {

        userRepository.findByEmail(createUserDto.email) ?: throw IllegalArgumentException("user already exist")
        val user = userRepository.save(toUserEntity(createUserDto))
        val tokenPair = jwtService.generateTokenPair(user)
        val accessCookie = ResponseCookie.from("accessCookie", tokenPair.accessToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofSeconds(accessExpiration))
            .build()

        val refreshCookie = ResponseCookie.from("refreshToken", tokenPair.refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofSeconds(refreshExpiration))
            .build()
        jwtRepository.setRefreshToken(createUserDto.email, tokenPair.refreshToken)
        return ResponseUserEntity(user, accessCookie, refreshCookie)
    }


    override fun login(createUserDto: CreateUserDto): JwtResponse {
        val user = userRepository.findByEmail(createUserDto.email) ?: throw  NotFoundException()
        if (user.password == createUserDto.password){
            val accessToken: String = jwtService.generateAccessToken(user)
            val refreshToken: String = jwtService.generateRefreshToken(user)
            jwtRepository.setRefreshToken(user.email, refreshToken)
            return JwtResponse(accessToken,refreshToken)
        }
        else{
            throw Exception("auth exception")
        }
    }

    override fun getAccessToken(refreshToken: String): JwtResponse {
        TODO("Not yet implemented")
    }

    override fun refresh(refreshToken: String): JwtResponse {
        TODO("Not yet implemented")
    }
    private fun toUserEntity(createUserDto: CreateUserDto): UserEntity =
        UserEntity(
            id = 0,
            username = createUserDto.username,
            email = createUserDto.email,
            password = BCrypt.withDefaults().hashToString(12, createUserDto.password.toCharArray()),
            roles = mutableListOf(RoleEntity(0,RoleEnum.USER))
        )

}