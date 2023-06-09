package lxqtpr.ecommerce.linda.auth.service

import lxqtpr.ecommerce.linda.auth.jwt.JwtRepository
import lxqtpr.ecommerce.linda.auth.jwt.JwtService
import lxqtpr.ecommerce.linda.auth.jwt.models.JwtCookiePair
import lxqtpr.ecommerce.linda.auth.jwt.models.TokenTypeEnum
import lxqtpr.ecommerce.linda.entities.RoleEntity.RoleEnum
import lxqtpr.ecommerce.linda.entities.RoleEntity.RoleRepository
import lxqtpr.ecommerce.linda.entities.UserEntity.UserRepository
import lxqtpr.ecommerce.linda.entities.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.entities.UserEntity.models.ResponseUserEntity
import lxqtpr.ecommerce.linda.entities.UserEntity.models.UserEntity
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthServiceImpl(
    val authenticationManager: AuthenticationManager,
    val bCrypt: BCryptPasswordEncoder,
    val userRepository: UserRepository,
    val roleRepository: RoleRepository,
    val jwtRepository: JwtRepository,
    val jwtService: JwtService

) : AuthService {
    override fun register(createUserDto: CreateUserDto): ResponseUserEntity {
        userRepository.findByEmail(createUserDto.email)?.let { throw Exception("User already exist") }
        val user = userRepository.save(toUserEntity(createUserDto))
        val tokenPair = jwtService.generateTokenPair(user)
        val cookiePair = jwtService.generateCookiePair(tokenPair)
        jwtRepository.setRefreshToken(user.email, tokenPair.refreshToken)
        return ResponseUserEntity(user, cookiePair)
    }


    override fun login(loginUserDto: CreateUserDto): ResponseUserEntity {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginUserDto.email,
                loginUserDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user = userRepository.findByEmail(loginUserDto.email) ?: throw NotFoundException()
        if (bCrypt.matches(loginUserDto.password, user.password)) {
            val tokenPair = jwtService.generateTokenPair(user)
            val cookiePair = jwtService.generateCookiePair(tokenPair)
            jwtRepository.setRefreshToken(user.email, tokenPair.refreshToken)
            return ResponseUserEntity(user, cookiePair)
        } else {
            throw Exception("Password doesn`t match")
        }
    }

    override fun logout(refreshToken: String): JwtCookiePair {
        jwtRepository.removeRefreshToken(jwtService.getUserEmailFromClaims(refreshToken, TokenTypeEnum.REFRESH))
        return jwtService.getLogoutCookie()
    }

    override fun refreshTokens(refreshToken: String): JwtCookiePair {
        val isRefreshTokenValid = jwtService.validateRefreshToken(refreshToken)
        if (isRefreshTokenValid) {
            userRepository.findByEmail(jwtService.getUserEmailFromClaims(refreshToken, TokenTypeEnum.REFRESH))?.let {
                val tokenPair = jwtService.generateTokenPair(it)
                jwtRepository.setRefreshToken(it.email, tokenPair.refreshToken)
                return jwtService.generateCookiePair(tokenPair)
            }
        }
        throw Exception("Token does`t valid")

    }

    private fun toUserEntity(createUserDto: CreateUserDto): UserEntity =
        UserEntity(
            id = 0,
            username = createUserDto.username,
            email = createUserDto.email,
            password = bCrypt.encode(createUserDto.password),
            roles = mutableListOf(roleRepository.findByRole(RoleEnum.USER)),
            createdAt = LocalDateTime.now(),
            cart = mutableListOf(),
            updatedAt = LocalDateTime.now()
        )
}